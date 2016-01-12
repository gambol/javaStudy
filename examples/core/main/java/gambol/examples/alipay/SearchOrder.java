//package gambol.examples.alipay;
//
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayUserTradeSearchRequest;
//import com.alipay.api.response.AlipayUserTradeSearchResponse;
//
///**
// * Created by zhenbao.zhou on 15/8/12.
// */
//public class SearchOrder {
//
//
//    static void search () throws Exception{
//        String serverUrl = "https://openapi.alipay.com/gateway.do";
//        String appId = "2015081100209209";
//        String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBdxLHQMqobzTeoJIdZfDyPtjrbW57RLWgUpugK9vNdwpwmOCswP3UiaO8TWkdWulI94CGam818icXJwGrGHXFCzJdqew1OYHCmT/cIM68pbh8X6VBVAI/cokQVF4Qf3uDB8rETBEpacknRP7YVznAOHE2C4VV3FXzujPWlkHQXQIDAQAB";
//        String format = "json";
//        String authToken = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//
//        AlipayClient client = new DefaultAlipayClient(serverUrl, appId, privateKey, format);
//        AlipayUserTradeSearchRequest req = new AlipayUserTradeSearchRequest();
//        req.setPageSize("4");
//        req.setEndTime("20150804");
//        req.setStartTime("20150801");
//        req.setPageNo("1");
//        AlipayUserTradeSearchResponse res = client.execute(req, authToken);
//    }
//
//    public static void main(String[] args) throws  Exception{
//        search();;
//    }
//}
