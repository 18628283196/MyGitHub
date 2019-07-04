package cn.itcast.mr.demo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ComonsFriendsStepOneMapper extends Mapper<LongWritable,Text,Text,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         * 接受数据：
         *k1: 行偏移量
         * v1:A:B,C,D,F,E,O
         *
         *经过shuffle的分区后得到：
         *  k1  v1
         *  B   A
         *  B   E
         *  B   F
         *  B   J
         * 再经过shuffle的分组后，合并value得到如下数据：
         *
         * 处理为：
         * k2:B
         * K2:A,E,F,J
         */
        //接受数据
        String lineData = value.toString();
        String[] split = lineData.split(":");
        //获取到用户
        String user = split[0];
        //获取好友列表
        String friends = split[1];
        //获取到每个好友
        String[] friend = friends.split(",");
        for (String s : friend) {
            context.write(new Text(s),new Text(user));
        }
    }
}
