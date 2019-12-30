package jds_project.scanner.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import sockslib.client.SocksSocket;

public class ScanThread extends Thread implements Runnable {
	private String HOST;
	private int PORT;

	public ScanThread(String host, int port) {
		this.setHOST(host);
		this.setPORT(port);
	}

	@Override
	public void run() {
		if (this.checkPort(this.getHOST(), this.getPORT())) {
			String data = this.getHOST() + ":" + this.getPORT();
			System.out.println(data);
			try (FileWriter writer = new FileWriter("log.txt", true)) {
				writer.write(data);
				writer.append('\n');
				writer.flush();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	private boolean checkPort(String host, int port) {
		Socket aaSocket = null;
		try {
			aaSocket = new SocksSocket(HEADER_PROXY.proxy);
			aaSocket.connect(new InetSocketAddress(this.getHOST(), this.getPORT()), 5000);
		} catch (IOException e) {
			try {
				aaSocket.close();
			} catch (IOException e1) {
			}
			return false;
		}
		try {
			aaSocket.close();
		} catch (IOException e) {
		}
		return true;
	}

	/**
	 * @return the hOST
	 */
	public String getHOST() {
		return this.HOST;
	}

	/**
	 * @param hOST the hOST to set
	 */
	public void setHOST(String hOST) {
		this.HOST = hOST;
	}

	/**
	 * @return the pORT
	 */
	public int getPORT() {
		return this.PORT;
	}

	/**
	 * @param pORT the pORT to set
	 */
	public void setPORT(int pORT) {
		this.PORT = pORT;
	}
}