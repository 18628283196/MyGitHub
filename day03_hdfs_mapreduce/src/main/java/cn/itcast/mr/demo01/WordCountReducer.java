package cn.itcast.mr.demo01;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountReducer extends Reducer<Text,LongWritable,Text,LongWritable> {
    private LongWritable countScore = new LongWritable();
    /**
     *每接受一行数据，就执行一次方法
     */
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

        /**
         * 接受数据：
         *K2：hello
         * V2：{1,1}
         *
         * 处理为：
         * K3：hello
         * V3：1+1=2
         */
        //接受数据

        //处理数据
        long count = 0;
        for (LongWritable value : values) {
            count += value.get();
        }
        countScore.set(count);

        //发送数据到下游
        context.write(key,countScore);

    }
}
