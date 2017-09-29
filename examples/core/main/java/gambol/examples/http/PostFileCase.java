package gambol.examples.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhenbao.zhou on 2017/9/18.
 */
public class PostFileCase {

    public static String upload(String url, String filePath) throws IOException {

        String respStr;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(url);
            File f  = new File(filePath);
            httppost.setHeader("Authorization",
                    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0U2l0ZSIsImNyZWF0ZWQiOjE1MDU3MjgxOTk0ODYsInNpdGVJZCI6InE4QlUrQlhnamNQYnVLaEhRYVRVQ21uWFo3ODJQaEZYTjFOa3pUVTFWeGM9IiwiZXhwIjoxNTA1ODQ4MTk5fQ.JR8Bfne1SzQllX4WQNHCA74UMm1ZTHfyua_KvV98kRS7_OFk4S2ZuIwh8mALjggaoCRSrT3cQWqBXnOaY58PeQ");
           //  httppost.setHeader("Content-Type","multipart/form-data; boundary=HereGoes");
            FileBody binFileBody = new FileBody(f);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            // add the file params
            multipartEntityBuilder.addPart("uploadFiles", binFileBody);
            // 设置上传的其他参数

            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            try (CloseableHttpResponse response = httpclient.execute(httppost);) {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                respStr = getRespString(resEntity);
                EntityUtils.consume(resEntity);
            }
        }

        System.out.println("resp=" + respStr);
        return respStr;
    }

    private static String getRespString(HttpEntity entity) throws IOException {
        if (entity == null) {
            return null;
        }
        InputStream is = entity.getContent();
        StringBuffer strBuf = new StringBuffer();
        byte[] buffer = new byte[4096];
        int r = 0;
        while ((r = is.read(buffer)) > 0) {
            strBuf.append(new String(buffer, 0, r, "UTF-8"));
        }
        return strBuf.toString();
    }

    public static void main(String[] args) throws Exception {
        upload("http://tob-open-api--develop.bbaecache.com//v1/file/upload", "/tmp/test-bbae");
    }
}
