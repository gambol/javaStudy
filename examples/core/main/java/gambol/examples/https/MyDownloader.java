//package gambol.examples.https;
//
///*
// * An example program using AsyncHttpClient with SSL certificate verification To the extent possible under law, Kevin
// * Locke has waived all copyright and related or neighboring rights to this work. A legal description of this waiver is
// * available in LICENSE.txt.
// */
//import com.ning.http.client.AsyncHttpClient;
//import com.ning.http.client.AsyncHttpClientConfig;
//import com.ning.http.client.Response;
//import sun.security.util.HostnameChecker;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLPeerUnverifiedException;
//import javax.net.ssl.SSLSession;
//import javax.security.auth.kerberos.KerberosPrincipal;
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.Principal;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.concurrent.ExecutionException;
//
///** Implements the "MyDownloader" application */
//public class MyDownloader {
//
//    /**
//     * HostnameVerifier implementation which implements the same policy as the Java built-in pre-HostnameVerifier
//     * policy.
//     */
//    private static class MyHostnameVerifier implements HostnameVerifier {
//        /**
//         * Checks if a given hostname matches the certificate or principal of a given session.
//         */
//        private boolean hostnameMatches(String hostname, SSLSession session) {
//            HostnameChecker checker = HostnameChecker.getInstance(HostnameChecker.TYPE_TLS);
//
//            boolean validCertificate = false, validPrincipal = false;
//            try {
//                Certificate[] peerCertificates = session.getPeerCertificates();
//
//                if (peerCertificates.length > 0 && peerCertificates[0] instanceof X509Certificate) {
//                    X509Certificate peerCertificate = (X509Certificate) peerCertificates[0];
//
//                    try {
//                        checker.match(hostname, peerCertificate);
//                        // Certificate matches hostname
//                        validCertificate = true;
//                    } catch (CertificateException ex) {
//                        // Certificate does not match hostname
//                    }
//                } else {
//                    // Peer does not have any certificates or they aren't X.509
//                }
//            } catch (SSLPeerUnverifiedException ex) {
//                // Not using certificates for peers, try verifying the principal
//                try {
//                    Principal peerPrincipal = session.getPeerPrincipal();
//                    if (peerPrincipal instanceof KerberosPrincipal) {
//                        validPrincipal = HostnameChecker.match(hostname, (KerberosPrincipal) peerPrincipal);
//                    } else {
//                        // Can't verify principal, not Kerberos
//                    }
//                } catch (SSLPeerUnverifiedException ex2) {
//                    // Can't verify principal, no principal
//                }
//            }
//
//            return validCertificate || validPrincipal;
//        }
//
//        public boolean verify(String hostname, SSLSession session) {
//            if (hostnameMatches(hostname, session)) {
//                return true;
//            } else {
//                // TODO: Add application-specific checks for
//                // hostname/certificate match
//                return false;
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//
//        String url = "https://trial-www.jtbgenesis.com/genesis2-demo/services/GA_HotelAvail_v2013";
//
//        SSLContext context = null;
//        try {
//            context = SSLContext.getInstance("TLS");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        try {
//            context.init(null, null, null);
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//            return;
//        }
//        // System.setProperty("javax.net.debug", "all");
//
//        AsyncHttpClient client = new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setSSLContext(context)
//                .setAcceptAnyCertificate(true)
//                // .setHostnameVerifier(new MyHostnameVerifier())
//                .build());
//
//        System.out.println("start get");
//        Response response = null;
//        try {
//            response = client.prepareGet(url).execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return;
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (response.getStatusCode() / 100 == 2) {
//            try {
//                String responseBody = response.getResponseBody();
//                System.err.println("Successfully downloaded " + url);
//                System.out.println(responseBody);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//        } else {
//            System.err.println("Failure downloading " + url + ": HTTP Status " + response.getStatusCode());
//        }
//
//    }
//}