package cn.itcast.mr.demo;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 目的：像之前用shell命令操作HDFS，现在使用javaapi的方式实现
 * 步骤：
 * 1.构建客服端
 * 2.执行我们的操作
 * 3.关闭资源
 */
public class HtfsTest {
    /**
     * 这种使用URL的方式不推荐。。。。
     * @throws Exception
     */
    @Test
    public void getUrlClient() throws Exception {
        //让java和HDFS相互认识一下 //第一步：注册hdfs 的url，让java代码能够识别hdfs的url形式
        URL.setURLStreamHandlerFactory( new FsUrlStreamHandlerFactory());
        String strUrl = "hdfs://node01:8020/aaa/biji.txt";
        InputStream inputStream = new URL(strUrl).openStream();
        FileOutputStream outputStream = new FileOutputStream(new File("E:\\资料\\biji1.text"));
        //执行操作
        IOUtils.copy(inputStream,outputStream);
        //关闭资源
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);

    }

    /**
     * 使用文件系统方式访问数据（掌握）
     * 第一种方式获取FileSystem
     */

    @Test
    public void getFileSystemClient01() throws IOException {
        //获取客服端
        Configuration config = new Configuration();

        config.set("fs.defaultFS","hdfs://node01:8020");

        FileSystem fileSystem = FileSystem.get(config);

        System.out.println(fileSystem);
        fileSystem.close();

    }


    /**
     * 第二种方式获取FileSystem
     *
     */
    @Test
    public void getFileSystemClient02() throws Exception {
        URI uri = new URI("hdfs://node01:8020");

        Configuration configuration = new Configuration();

        FileSystem fileSystem = FileSystem.get(uri, configuration);

        System.out.println(fileSystem);
        fileSystem.close();
    }



    /**
     * 通过递归遍历hdfs文件系统的第一种方式
     */
    @Test
    public void getListFiles01() throws Exception {
        //获取连接client
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());

        //执行操作(op)
       // FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));

        //写一个递归方法
        getAllFiles(fileSystem,new Path("/"));

        fileSystem.close();

    }
    //递归遍历根目录下的目录文件
    private void getAllFiles(FileSystem fileSystem, Path path) throws IOException {

        FileStatus[] fileStatuses = fileSystem.listStatus(path);
        /**
         * 递归一般使用if..else..来实现
         * if来判断递归的终止条件
         * else实现自己调自己
         */
        for (FileStatus fileStatus : fileStatuses) {
            if (!fileStatus.isDirectory()){
                System.out.println("文件路径为："+"\t"+fileStatus.getPath().toString());
            }else {
                getAllFiles(fileSystem,fileStatus.getPath());
            }
        }


    }


    /**
     * 通过递归遍历hdfs文件系统的第二种方式，不用自己写递归，可以直接调用方法
     */
    @Test
    public void getListFiles02() throws Exception{
        //连接client
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        //调用递归方法
        RemoteIterator<LocatedFileStatus> remotelterator = fileSystem.listFiles(new Path("/"), true);
        //遍历
       while (remotelterator.hasNext()){
           LocatedFileStatus fileStatus = remotelterator.next();
           System.out.println("文件夹路径为："+"\t"+fileStatus.getPath().toString());
       }
       fileSystem.close();

    }

    /**
     * 下载文件到本地
     */
    @Test
    public void downloadFiles() throws Exception{
        //连接client
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());

        //执行op
        FSDataInputStream inputStream = fileSystem.open(new Path("/aaa/biji.txt"));
        FileOutputStream outputStream = new FileOutputStream(new File("E:\\资料\\biji2.text"));
        IOUtils.copy(inputStream,outputStream);
        //关闭资源
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
        fileSystem.close();
    }


    /**
     * 创建文件夹
     */
    @Test
    public void createDir() throws Exception{
        //获取client
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());
        //创建hdfs文件夹
        fileSystem.mkdirs(new Path("/zhanglong/data1"));
        //关闭资源
        fileSystem.close();
    }

    /**
     * hdfs文件上传
     */
    @Test
    public void upFiles()throws Exception{
        //获取client
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node01:8020"), new Configuration());

        //文件上传
        fileSystem.copyFromLocalFile(new Path("E:\\资料\\biji2.text"),new Path("/zhanglong/data1"));

        //关闭资源
        fileSystem.close();
    }


}
