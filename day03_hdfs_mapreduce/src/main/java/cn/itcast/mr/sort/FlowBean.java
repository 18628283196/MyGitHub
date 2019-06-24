package cn.itcast.mr.sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements WritableComparable<FlowBean> {

    private Integer upflow;
    private Integer downflow;
    private Integer upCountFlow;
    private Integer downCountFlow;

    @Override
    public String toString() {
        return upflow + "\t" +
                downflow + "\t" +
                 upCountFlow + "\t" +
                 downCountFlow + "\t" ;
    }

    public Integer getUpflow() {
        return upflow;
    }

    public void setUpflow(Integer upflow) {
        this.upflow = upflow;
    }

    public Integer getDownflow() {
        return downflow;
    }

    public void setDownflow(Integer downflow) {
        this.downflow = downflow;
    }

    public Integer getUpCountFlow() {
        return upCountFlow;
    }

    public void setUpCountFlow(Integer upCountFlow) {
        this.upCountFlow = upCountFlow;
    }

    public Integer getDownCountFlow() {
        return downCountFlow;
    }

    public void setDownCountFlow(Integer downCountFlow) {
        this.downCountFlow = downCountFlow;
    }

    public FlowBean(Integer upflow, Integer downflow, Integer upCountFlow, Integer downCountFlow) {

        this.upflow = upflow;
        this.downflow = downflow;
        this.upCountFlow = upCountFlow;
        this.downCountFlow = downCountFlow;
    }

    public FlowBean() {

    }

    //序列化
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(upflow);
        dataOutput.writeInt(downflow);
        dataOutput.writeInt(upCountFlow);
        dataOutput.writeInt(downCountFlow);

    }
    //反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upflow = dataInput.readInt();
        this.downflow = dataInput.readInt();
        this.upCountFlow = dataInput.readInt();
        this.downCountFlow = dataInput.readInt();
    }

    //比较方法 根据上行流行递减排序
    @Override
    public int compareTo(FlowBean o) {
        int compare = this.upflow.compareTo(o.getUpflow());
        return -compare;
    }
}
