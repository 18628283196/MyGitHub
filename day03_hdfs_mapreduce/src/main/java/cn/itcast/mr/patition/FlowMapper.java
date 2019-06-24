package cn.itcast.mr.patition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable,Text,Text,NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        /**
         * k1:offset
         * v1:13480253104	3	3	180	180	
         * 
         * 处理为：
         * k1:13480253104	3	3	180	180	
         * k2:null
         */
        //接受数据
        context.write(value,NullWritable.get());



    }
}
