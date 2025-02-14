package db;

import javax.net.ssl.SSLContext;


public class TLSVersionCheck {
     public static void main(String[] args) throws Exception {
        System.out.println("Protocolos TLS soportados:");
        for (String protocol : SSLContext.getDefault().getSupportedSSLParameters().getProtocols()) {
            System.out.println(protocol);
        }
    }
}
