package cn.itcast.mr.demo02;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FlowJobMain extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        //创建媒介
        Job job = Job.getInstance(super.getConf(),"flow");
        job.setJarByClass(FlowJobMain.class);//不写在集群上运行会报错

        //指定输入
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path("F:\\娱乐头条\\input"));

        //map逻辑
        job.setMapperClass(FlowMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //分区
        //排序
        //规约
        //分组

        //定义reduce逻辑
        job.setReducerClass(FlowReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //指定输出
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,new Path("F:\\娱乐头条\\output"));

        //等待执行
        boolean b = job.waitForCompletion(true);

        return b?0:1;

    }

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(),new FlowJobMain(),args);
    }
}
