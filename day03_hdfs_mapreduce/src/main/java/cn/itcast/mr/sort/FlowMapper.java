package cn.itcast.mr.sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable,Text,FlowBean,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //接受数据

        String lineData = value.toString();
        //处理数据
        String[] split = lineData.split("\t");
        String iphoneNum = split[0];
        Integer upflow = Integer.parseInt(split[1]);
        Integer downflow = Integer.parseInt(split[2]);
        Integer upCountflow = Integer.parseInt(split[3]);
        Integer downCountFlow = Integer.parseInt(split[4]);
        FlowBean flowBean = new FlowBean(upflow, downflow, upCountflow, downCountFlow);
        //发送数据
        context.write(flowBean,new Text(iphoneNum));



    }
}
