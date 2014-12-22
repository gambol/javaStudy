package gambol.examples.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * User: zhenbao.zhou
 * Date: 12/17/14
 * Time: 8:34 PM
 */
public class HttpRequestSender {

    public static String getWithStringResponse(final String httpGet){

        HttpGet httpget = new HttpGet(httpGet);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()){
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }catch (Exception e){
            System.out.println("HTTP GET 发生异常");
        }

        return "";
    }

    public static void main(String[] args) {

        String s = getWithStringResponse("http://www.baidu.com");
        System.out.println(s);
    }

}
