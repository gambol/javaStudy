package org.gambol.modules.asyncclient;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import gambol.examples.asynchttp.AsyncClient;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

/**
 * Created by zhenbao.zhou on 16/1/28.
 */
public class AsyncClientTest extends TestCase {

    @Test
    public void testFetch() {
        final Logger logger = LoggerFactory.getLogger(AsyncClient.class);
        AsyncHttpClient asyncHttpClient = new AsyncClient().getClient();

        for (int i = 0; i < 20; i++) {

            final int k = i;
            new Thread(() -> {
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

                                logger.info("ok, done. on complete:" + response.getStatusCode() + " k:" + k);
                                return response;
                            }

                            @Override
                            public void onThrowable(Throwable t) {
                                // Something wrong happened.
                                logger.info("wacao. sss" + t.getMessage() + "  k:" + k);
                            }
                        });

                try {
                    Response r = f.get();
                    // System.out.println("wocao. response:" + r.getResponseBody());
                } catch (Exception e) {

                }
            }).start();
        }
    }

    public static void main(String[] args) {
        AsyncClientTest t = new AsyncClientTest();
        t.testFetch();;
    }
}
