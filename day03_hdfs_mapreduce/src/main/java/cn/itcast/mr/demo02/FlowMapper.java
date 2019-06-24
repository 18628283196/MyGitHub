package cn.itcast.mr.demo02;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable,Text,Text,FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //接受数据

        String lineData = value.toString();
        //处理数据
        String[] split = lineData.split("\t");
        String iphoneNum = split[1];
        Integer upflow = Integer.parseInt(split[6]);
        Integer downflow = Integer.parseInt(split[7]);
        Integer upCountflow = Integer.parseInt(split[8]);
        Integer downCountFlow = Integer.parseInt(split[9]);
        FlowBean flowBean = new FlowBean(upflow, downflow, upCountflow, downCountFlow);
        //发送数据
        context.write(new Text(iphoneNum),flowBean);



    }
}
