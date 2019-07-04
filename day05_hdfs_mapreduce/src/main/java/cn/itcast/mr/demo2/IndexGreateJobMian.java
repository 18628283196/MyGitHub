package cn.itcast.mr.demo2;

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

/**
 * 需求：有大量的文本（文档、网页），需要建立搜索索引
 思路分析：
 首选将文档的内容全部读取出来，加上文档的名字作为key，文档的value为1，组织成这样的一种形式的数据
 map端数据输出
 hello-a.txt  1
 hello-a.txt 1
 hello-a.txt 1
 reduce端数据输出
 hello-a.txt 3
 */

public class IndexGreateJobMian extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        //创建媒介
        Job job = Job.getInstance(super.getConf(),"indexgreate");
        job.setJarByClass(IndexGreateJobMian.class);

        //指定输入
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path("F:\\娱乐头条\\input"));

        //指定map逻辑
        job.setMapperClass(IndexGreateMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //分区
        //排序
        //规约
        //分组


        //指定reduce逻辑
        job.setReducerClass(IndexGreateReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //指定输出
        job.setOutputKeyClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,new Path("F:\\娱乐头条\\output_file"));

        boolean b = job.waitForCompletion(true);



        return b?0:1;
    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(),new IndexGreateJobMian(),args);
    }
}
