package jds_project.scanner.engine;

import java.net.InetSocketAddress;

import sockslib.client.Socks5;
import sockslib.client.SocksProxy;

public class HEADER_PROXY {
    public static SocksProxy proxy = new Socks5(new InetSocketAddress("localhost",9050));
}
