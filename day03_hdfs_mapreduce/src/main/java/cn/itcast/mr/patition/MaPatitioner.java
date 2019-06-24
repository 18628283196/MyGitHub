package cn.itcast.mr.patition;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class MaPatitioner extends Partitioner<Text,NullWritable> {
    @Override
    public int getPartition(Text text, NullWritable nullWritable, int i) {
        /**
         * 数据为
         * k1:13480253104	3	3	180	180
         * k2:null
         *
         * 然后判断分组
         *
         */
        String lineData = text.toString();
        String[] split = lineData.split("\t");
        String phoneNum = split[0];
        if(phoneNum.startsWith("135")){
            return 0;
        }else if (phoneNum.startsWith("136")){
            return 1;
        }else if (phoneNum.startsWith("137")){
            return 2;
        }else if (phoneNum.startsWith("138")){
            return 3;
        }else if (phoneNum.startsWith("139")){
            return 4;
        }else {
            return 5;
        }
    }
}
