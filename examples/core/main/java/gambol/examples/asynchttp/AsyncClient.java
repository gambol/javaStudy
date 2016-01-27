package gambol.examples.asynchttp;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;

import java.util.concurrent.Future;

/**
 * Created by zhenbao.zhou on 16/1/28.
 */
public class AsyncClient {

    public static void main(String[] args) throws Exception {
        AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder().setConnectTimeout(1).setRequestTimeout(10).build();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(config);

        Future<Response> f = asyncHttpClient.prepareGet("http://www.baidu.com/").execute(new AsyncCompletionHandler<Response>() {

            @Override
            public Response onCompleted(Response response) throws Exception {
                // Do something with the Response
                // ...
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }

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

}
