package gambol.examples.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;

/**
 * Created by zhenbao.zhou on 16/8/28.
 */
public class MasterSlave {

    private static final String C_PATH = "/zzb/leader";
    private static final String ZK_CONN_STR = "127.0.0.1:2181";

    private void elector() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_CONN_STR, retryPolicy);
        client.start();
        String APP_NAME = Thread.currentThread().getName();

        // ensure path of C_PATH
        new EnsurePath(C_PATH).ensure(client.getZookeeperClient());

        final LeaderSelector leaderSelector = new LeaderSelector(client, C_PATH, new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                try {

                    System.out.println( APP_NAME + " take leader ship, will do some task, will hold time forever");
                    // 添加对于股票的listener

                    while (true) {
                        Thread.sleep(Integer.MAX_VALUE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {

            }
        });

        leaderSelector.start();
        Thread.sleep(Integer.MAX_VALUE);
        client.close();
    }

    public static void main(String[] args) throws Exception {

        new MasterSlave().elector();


    }
}
