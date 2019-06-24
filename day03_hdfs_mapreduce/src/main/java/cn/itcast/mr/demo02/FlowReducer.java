package cn.itcast.mr.demo02;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class FlowReducer extends Reducer<Text,FlowBean,Text,FlowBean> {
    private FlowBean flowBean = new FlowBean();
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        Integer upFlow = 0;
        Integer downFlow = 0;
        Integer upCountFlow = 0;
        Integer downCountFlow = 0;
        for (FlowBean value : values) {
            upFlow += value.getUpflow();
            downFlow += value.getDownflow();
            upCountFlow += value.getUpCountFlow();
            downCountFlow =+ value.getDownCountFlow();
        }
        flowBean.setUpflow(upFlow);
        flowBean.setDownflow(downFlow);
        flowBean.setUpCountFlow(upCountFlow);
        flowBean.setDownCountFlow(downCountFlow);

        //发送数据
        context.write(key,flowBean);
    }
}
