import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

/**
 * 创建永久节点
 */
public class CreateNode {
    private  CuratorFramework client = null;

    @Before
    public void getClient(){
        //获取客服端对象
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);

        client = CuratorFrameworkFactory.newClient("node01:2181,node02:2181,node03:2181",1000,1000,retryPolicy);

        //调用start开启客服端
        client.start();
    }
    @Test
    public void createEverNode() throws Exception {

        //通过client来创建节点，并且需要指定节点的类型
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/hello3","hello".getBytes());

        //关闭资源
        client.close();
    }

    /**
     * 创建一个临时节点,需要设置休眠时间，不然看不见文件
     */
    @Test
    public void createLinShiNode() throws Exception {
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/hello","hello tom".getBytes());
        Thread.sleep(50000);
        client.close();
    }

    /**
     * 修改节点数据
     */
    @Test
    public void updateNodeData() throws Exception {
        client.setData().forPath("/hello3","hello11".getBytes());
        client.close();
    }

    /**
     * 节点数据查询
     */
    @Test
    public void queryNodeData() throws Exception {
        byte[] bytes = client.getData().forPath("/hello3");
        System.out.println(new String(bytes));
        client.close();

    }
}
