package gambol.examples;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gambol.examples.mappers.JacksonMapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zhenbao.zhou on 17/5/22.
 */
public class StraightChecker {

    public static DateTime parse(String pattern, String date) {
        checkArgument(!Strings.isNullOrEmpty(date));
        return DateTime.parse(date, DateTimeFormat.forPattern(pattern).withLocale(Locale.US));
    }


    public static void main(String[] args) {
       String s = "{\"day\": \"06/16/2017 12:00:00 AM\",\"symbol\": \"AABA\",\"dayOpen\": \"52.7900\",\"dayHigh\": \"53.0800\", \"dayLow\": \"51.9000\",\"dayClose\": \"52.5800\",\"dayCloseType\": 3, \"prevDay\": \"06/15/2017 12:00:00 AM\",\"prevDayClose\": \"52.5800\", \"openInterest\": 0}";

        System.err.println("s:" + s);

        List<String> list = Lists.newArrayList();
        list.add("123123");
        list.add("51234");

//        String s = "[\"123123\",\"51234\"]";
        System.err.println(JacksonMapper.obj2String(list));


        Map m = Maps.newHashMap();

        m.put("adjustStatus", 4);
        m.put("lastAdjustTime", new Date());
        m.put("openTime", new Date());
        m.put("accountStatus", 2);
        m.put("portfolioId", 4);
        m.put("newPortfolioId", 7);

        Map k = Maps.newHashMap();
        k.put("123123", m);

        m = Maps.newHashMap();

        m.put("adjustStatus", 5);
        m.put("lastAdjustTime", new Date());
        m.put("openTime", new Date());
        m.put("accountStatus", 2);
        m.put("portfolioId", 5);
        m.put("newPortfolioId", 7);
        k.put("51234", m);

        System.err.println(JacksonMapper.obj2String(k));

        System.err.println(new BigDecimal("13").compareTo(new BigDecimal("14")));
    }

}
