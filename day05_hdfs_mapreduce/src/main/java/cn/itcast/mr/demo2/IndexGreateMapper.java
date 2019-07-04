package cn.itcast.mr.demo2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class IndexGreateMapper extends Mapper<LongWritable,Text,Text,LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取文件切片
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        //通过文件切片获取文件名
        String name = fileSplit.getPath().getName();

        String line = value.toString();

        String[] split = line.split(",");
        for (String word : split) {
            context.write(new Text(word+"-"+name),new LongWritable(1));
        }
    }
}
