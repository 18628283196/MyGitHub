import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zookeeper的watch机制
 */
public class WatchNode {
    public static void main(String[] args) throws Exception {
        //连接客服端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("node01:2181,node02:2181,node03:2181",1000,1000,retryPolicy);
        client.start();

        //设置节点的cache
        TreeCache treeCache = new TreeCache(client,"/hello3");
        //设置监听器和 处理器过程
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                ChildData data = treeCacheEvent.getData();
                if (data != null){
                    switch (treeCacheEvent.getType()){
                        case NODE_ADDED:
                            System.out.println("NODE_ADDED:"+data.getPath()+" 数据:"+new String(data.getData()));
                            break;
                        case NODE_REMOVED:
                            System.out.println("NODE_REMOVED:"+data.getPath()+" 数据:"+new String(data.getData()));
                        case NODE_UPDATED:
                            System.out.println("NODE_UPDATED:"+data.getPath()+" 数据:"+new String(data.getData()));
                        default:
                            break;
                    }
                }else{
                    System.out.println("data is null:"+treeCacheEvent.getType());
                }
            }
        });
        //开始监听
        treeCache.start();
        Thread.sleep(300000);
        client.close();
    }
}
