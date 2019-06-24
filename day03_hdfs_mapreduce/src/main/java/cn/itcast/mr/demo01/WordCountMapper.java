package cn.itcast.mr.demo01;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable,Text,Text,LongWritable>{
    private Text wordTest = new Text();
    private LongWritable score = new LongWritable(1);

    /**
     * map方法：每接受一行数据，就执行一次方法
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         * 接受数据
         * K1：行偏移量
         * V1：hello，word，Hadoop
         *
         *
         * 处理为：
         * K2：hello
         * V2：1
         */

        //接受数据
        //处理数据
        String[] split = value.toString().split(" ");
        for (String word : split) {
            wordTest.set(word);
            //发送到下游
            //context.write(new Text(word),new LongWritable(1));所以得将他们设置为成员变量
            context.write(wordTest,score);
        }





    }
}
