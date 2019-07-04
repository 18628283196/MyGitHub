package cn.itcast.mr.demo1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

public class ComonsFriendsStepOneMapper extends Mapper<LongWritable,Text,Text,Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         * 接受数据：
         *k1: 行偏移量
         *V1： E-A-J-F-	B
         * K-A-B-E-F-G-H-	C
         *
         *经过shuffle的分区后得到：
         *  k1  v1      K1      V1
         * E,A   B      K,H     C
         * E,J   B      K,B     C
         * E,F   B      K,E     C
         * A,J   B      K,F     C
         * A,F   B         ....
         * J,F   B      A,F     C
         *
         * 再经过shuffle的分组后，合并value得到如下数据：
         *
         * 处理为：
         * k2:A-F
         * K2:{B,C}
         */
        //接受数据
        String lineData = value.toString();
        String[] split = lineData.split("\t");
        String user = split[1];
        String friends = split[0];
        String[] split1 = friends.split("-");
        Arrays.sort(split1);
        for (int i = 0;i<split1.length-1;i++){
            for (int j=i+1;j<split1.length;j++){
                context.write(new Text(split1[i]+"-"+split1[j]),new Text(user));
            }
        }
    }
}
