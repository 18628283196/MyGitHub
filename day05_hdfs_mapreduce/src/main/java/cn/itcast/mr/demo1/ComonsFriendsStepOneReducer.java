package cn.itcast.mr.demo1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ComonsFriendsStepOneReducer extends Reducer<Text,Text,Text,Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        /**
         * 接受数据：
         * k2:A-F
         * K2:{B,C}
         */
        StringBuffer buffer = new StringBuffer();
        for (Text value : values) {

           buffer.append(value).append("-");
        }
        context.write(key,new Text(buffer.toString()));
    }
}
