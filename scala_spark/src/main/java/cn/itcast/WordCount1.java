package cn.itcast;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class WordCount1 {
    public static void main(String[] args) {
        //构建sparkconf,设置配置信息
        SparkConf sparkConf = new SparkConf().setAppName("wordcount").setMaster("local[2]");
        //构建java版的sparkContext
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        //读取文件
        JavaRDD<String> dataRDD = sc.textFile("F:\\娱乐头条\\input\\wordcount.txt");
        //对你一行单词进行划分
        JavaRDD<String> wordRDD = dataRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
        //对每个单词统计为1
        // Spark为包含键值对类型的RDD提供了一些专有的操作。这些RDD被称为PairRDD。
        // mapToPair函数会对一个RDD中的每个元素调用f函数，其中原来RDD中的每一个元素都是T类型的，
        // 调用f函数后会进行一定的操作把每个元素都转换成一个<K2,V2>类型的对象,其中Tuple2为多元组
        JavaPairRDD<String, Integer> wordAndOneRDD = wordRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });


        //todo:6、相同单词出现的次数累加
        JavaPairRDD<String, Integer> resultJavaPairRDD = wordAndOneRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //todo:7、反转顺序
        JavaPairRDD<Integer, String> reverseJavaPairRDD = resultJavaPairRDD.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tuple) throws Exception {
                return new Tuple2<Integer, String>(tuple._2, tuple._1);
            }
        });

        //todo:8、把每个单词出现的次数作为key，进行排序，并且在通过mapToPair进行反转顺序后输出
        JavaPairRDD<String, Integer> sortJavaPairRDD = reverseJavaPairRDD.sortByKey(false).mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> tuple) throws Exception {

                return new Tuple2<String, Integer>(tuple._2, tuple._1);
                //或者使用tuple.swap() 实现位置互换，生成新的tuple;
            }
        });
        //todo:执行输出
        System.out.println(sortJavaPairRDD.collect());
        //todo:关闭sparkcontext
        sc.stop();
    }
}





