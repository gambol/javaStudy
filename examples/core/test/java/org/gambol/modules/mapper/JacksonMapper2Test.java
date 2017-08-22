package org.gambol.modules.mapper;

import gambol.examples.mappers.JacksonMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhenbao.zhou on 17/3/9.
 */
public class JacksonMapper2Test {

    public static void main(String[] args) {
        String str = "{\"status\":\"OK\",\"data\":{\"facade\":\"1\",\"item\":{\"name\":[],\"cardno\":[],\"sex\":[],\"folk\":[],\"birthday\":[],\"address\":[],\"issue_authority\":\"北京市公安局海淀分局\",\"valid_period\":\"2007.01.27-2017.01.27\",\"header_pic\":[]}}}";

        YunmaiResult yunmaiResult = JacksonMapper.string2Obj(str, YunmaiResult.class);

        System.err.println("yunmaiResutl:" + yunmaiResult.getData().getItem().getValid_period());
    }

    @Getter
    @Setter
    public static class YunmaiResult {
        public static final String obverseSideFlag = "0";
        public static final String reverseSideFlag = "1";
        // {"status":"OK","data":{"facade":"0","item":{
        // "name":"黄子浩",
        // "cardno":"341621199210291913",
        // "sex":"男","folk":"汉","birthday":"1992年10月29日",
        // "address":"安徽省涡阳县高炉镇杨瓦房行政昔自然村O3",
        // "issue_authority":[],"valid_period":[],"header_pic":[]}}}

        // {"status":"OK","data":{
        // "facade":"1","item":{"name":[],"cardno":[],"sex":[],"folk":[],"birthday":[],"address":[],
        // "issue_authority":"不能用于非法用途公安局",
        // "valid_period":"2008.10.13-2018.10.13","header_pic":[]}}}
        private String status;
        private Data data;
        public boolean isSuccess() {
            return "OK".equals(status);
        }
    }

    @Getter @Setter
    public static class Data {
        private String facade;
        private Item item;
    }

    @Getter @Setter
    public static class Item {
        private String birthday;
        private String sex;
        private String issue_authority;
        private String address;
        private String folk;
        private String name;
        private String valid_period;
        private String cardno;
        private String header_pic;
    }


}
