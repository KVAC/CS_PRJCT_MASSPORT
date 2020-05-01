package jds_project.scanner.proxy;

import java.net.InetSocketAddress;

import jds_project.scanner.config.HEADER_ALL;
import sockslib.client.Socks5;
import sockslib.common.UsernamePasswordCredentials;

public class InitProxy {
	public static void prepareProxy() {
		// PROXY HOST:PORT
		HEADER_ALL.proxy = new Socks5(
				new InetSocketAddress(HEADER_ALL.config.getProxy_host(), HEADER_ALL.config.getProxy_port()));
		// PROXY HOST:PORT

		// FOR DEFAULT TORRC USE "" in PASS and PASS (in Config.yml)
		// PROXY CREDS
		HEADER_ALL.proxy.setCredentials(new UsernamePasswordCredentials(HEADER_ALL.config.getProxy_user(),
				HEADER_ALL.config.getProxy_password()));
		// PROXY CREDS
	}

}
