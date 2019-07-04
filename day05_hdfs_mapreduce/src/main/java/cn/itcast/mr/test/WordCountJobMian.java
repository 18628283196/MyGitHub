package cn.itcast.mr.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountJobMian extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {

        Job job = Job.getInstance(super.getConf(), "wordcount");
        job.setJarByClass(WordCountJobMian.class);

        job.setInputFormatClass(TextInputFormat.class);//指定待处理数据的文件格式
        TextInputFormat.addInputPath(job,new Path("F:\\Test\\input"));
        //第二步：自定义map逻辑
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);



        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //第八步：指定输出
        job.setOutputFormatClass(TextOutputFormat.class);
        //指定输出的path一定不能先存在，不然会报错，如果存在可以先删除
        TextOutputFormat.setOutputPath(job, new Path("F:\\Test\\output"));
        //等待执行
        boolean b = job.waitForCompletion(true);
        return b?0:1;
    }





    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new WordCountJobMian(), args);
        System.exit(run);
    }
}
