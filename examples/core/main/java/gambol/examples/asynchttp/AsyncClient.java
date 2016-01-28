package gambol.examples.asynchttp;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhenbao.zhou on 16/1/28.
 */
public class AsyncClient {

    private final AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();

    private static final Supplier<HashedWheelTimer> TIMER = Suppliers.memoize(new Supplier<HashedWheelTimer>() {
        @Override
        public HashedWheelTimer get() {
            HashedWheelTimer timer = new HashedWheelTimer();
            timer.start();
            return timer;
        }
    });

    private static final Supplier<NioClientSocketChannelFactory> CHANNEL_FACTORY = Suppliers
            .memoize(new Supplier<NioClientSocketChannelFactory>() {
                @Override public NioClientSocketChannelFactory get() {
                    return new NioClientSocketChannelFactory(AsyncClient.forkExecutor(), AsyncClient.forkExecutor(),
                            Runtime.getRuntime().availableProcessors());
                }
            });

    private final Supplier<AsyncHttpClient> lazyClient = Suppliers.memoize(new Supplier<AsyncHttpClient>() {

        @Override
        public AsyncHttpClient get() {
            builder.setExecutorService(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
            NettyAsyncHttpProviderConfig providerConfig = new NettyAsyncHttpProviderConfig();
            providerConfig.setNettyTimer(TIMER.get());
            providerConfig.setBossExecutorService(Executors.newFixedThreadPool(Runtime.getRuntime()
                    .availableProcessors()));

            providerConfig.setSocketChannelFactory(CHANNEL_FACTORY.get());
            builder.setAsyncHttpClientProviderConfig(providerConfig);
            return new AsyncHttpClient(builder.build());
        }
    });

    public static Executor forkExecutor() {
        return new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue());
    }

    public AsyncClient() {
        setCompressionEnabled(true);

        // pooling conf
        setAllowPoolingConnection(true);
        setIdleConnectionInPoolTimeoutInMs(60000);

        // connection conf
        setMaximumConnectionsTotal(100);
        setMaximumConnectionsPerHost(20);

        // request conf
        setConnectionTimeoutInMs(1000);
        setRequestTimeoutInMs(60000);
        setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) Chrome/27.0.1453.94 Safari/537.36 qunarhc/8.0.1");
    }

    public AsyncHttpClient getClient() {
        return lazyClient.get();
    }

    public void setMaximumConnectionsTotal(int defaultMaxTotalConnections) {
        builder.setMaxConnections(defaultMaxTotalConnections);
    }

    public void setMaximumConnectionsPerHost(int defaultMaxConnectionPerHost) {
        builder.setMaxConnectionsPerHost(defaultMaxConnectionPerHost);
    }

    public void setConnectionTimeoutInMs(int defaultConnectionTimeOutInMs) {
        builder.setConnectTimeout(defaultConnectionTimeOutInMs);
    }

    public void setWebSocketIdleTimeoutInMs(int defaultWebSocketIdleTimeoutInMs) {
        builder.setWebSocketTimeout(defaultWebSocketIdleTimeoutInMs);
    }

    public void setIdleConnectionTimeoutInMs(int defaultIdleConnectionTimeoutInMs) {
        builder.setConnectionTTL(defaultIdleConnectionTimeoutInMs);
    }

    public void setIdleConnectionInPoolTimeoutInMs(int defaultIdleConnectionInPoolTimeoutInMs) {
        builder.setPooledConnectionIdleTimeout(defaultIdleConnectionInPoolTimeoutInMs);
    }

    public void setRequestTimeoutInMs(int defaultRequestTimeoutInMs) {
        builder.setRequestTimeout(defaultRequestTimeoutInMs);
    }

    public void setFollowRedirects(boolean redirectEnabled) {
        builder.setFollowRedirect(redirectEnabled);
    }

    public void setMaximumNumberOfRedirects(int maxDefaultRedirects) {
        builder.setMaxRedirects(maxDefaultRedirects);
    }

    public void setCompressionEnabled(boolean compressionEnabled) {
        builder.setCompressionEnforced(compressionEnabled);
    }

    public void setUserAgent(String userAgent) {
        builder.setUserAgent(userAgent);
    }

    public void setAllowPoolingConnection(boolean allowPoolingConnection) {
        builder.setAllowPoolingConnections(allowPoolingConnection);
    }

    <T> ListenableFuture<T> privateGet(String url,  AsyncHandler<T> handler) throws IOException {
        AsyncHttpClient.BoundRequestBuilder builder = getClient().prepareGet(url);


        Request request = builder.build();

        return new GuavaListenableFuture<T>(getClient().executeRequest(request, handler));
    }


    /**
     * 建AsyncCompletionHandlerBase，想记录异常的可以写个子类重写onThrow方法
     */
    public <T> ListenableFuture<T> get(final String url, AsyncHandler<T> handler) throws IOException {
        return privateGet(url,  handler);
    }



    public static void main(String[] args) throws Exception {

        AsyncHttpClient asyncHttpClient = new AsyncClient().getClient();

        Future<Response> f = asyncHttpClient.prepareGet("http://www.baidu.com/").execute(
                new AsyncCompletionHandler<Response>() {

                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        // Do something with the Response
                        // ...
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {

                        }

                        System.out.println("ok, done. on complete:" + response.getStatusCode());
                        return response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        // Something wrong happened.
                        System.out.println("wacao. sss" + t.getMessage());
                    }
                });

        Response r = f.get();
        System.out.println("wocao. response:" + r.getResponseBody());
    }

    class GuavaListenableFuture<T> implements ListenableFuture<T> {

        private final com.ning.http.client.ListenableFuture<T> future;

        public GuavaListenableFuture(com.ning.http.client.ListenableFuture<T> future) {
            this.future = future;
        }

        @Override
        public void addListener(Runnable listener, Executor executor) {
            future.addListener(listener, executor);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return future.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return future.isCancelled();
        }

        @Override
        public boolean isDone() {
            return future.isDone();
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            return future.get();
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return future.get(timeout, unit);
        }
    }


}
