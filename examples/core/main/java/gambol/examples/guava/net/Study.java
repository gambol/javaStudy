package gambol.examples.guava.net;

import com.google.common.net.InetAddresses;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * @author kris.zhang
 */
public class Study {
    public static void main(String[] args) throws UnknownHostException, ParseException {
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("coerce:      "+InetAddresses.coerceToInteger(localhost));
        System.out.println("addrString:  "+InetAddresses.toAddrString(localhost));
        System.out.println("uriString    "+InetAddresses.toUriString(localhost));
        System.out.println("fromInteger  "+InetAddresses.fromInteger(InetAddresses.coerceToInteger(localhost)));
        System.out.println("forString    "+InetAddresses.forString("127.0.0.1"));
        System.out.println("forUriString "+InetAddresses.forUriString("127.0.0.1"));
    }
}
