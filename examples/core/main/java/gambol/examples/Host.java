package gambol.examples;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;

import java.util.regex.Pattern;

/**
 * Created by zhenbao.zhou on 17/2/10.
 */
@Slf4j
public class Host {

    private final static Pattern DOMAIN_PATTERN = Pattern.compile(".*\\.(?=.*\\.)");

    /**
     * 根据host 获得domain
     *
     * @param host
     * @return
     */
    private String extractDomain(String host) {
        if (StringUtils.isBlank(host))
            return host;
        String domain = DOMAIN_PATTERN.matcher(host).replaceAll("");
        System.out.println("extract domain. host:{} domain:{}" + host + "," +  domain);
        return domain;
    }


    public static void main(String[] args) {
        String host = "44.a.sss.com";
        String host2 = "zzz.com";
        String host3 = "ccc";
        Host h = new Host();
        String d1 = h.extractDomain(host);
        String d2 = h.extractDomain(host2);
        String d3 = h.extractDomain(host3);
        System.err.println(d1 + "   " + d2 + "   " + d3);


        try {
            System.err.println("hahahaha");
            throw new RuntimeException("jimin nihao ");
        } catch (Exception e) {
            log.error("host:{}, host2:{}, {}", host, host2, e);
        }

    }
}
