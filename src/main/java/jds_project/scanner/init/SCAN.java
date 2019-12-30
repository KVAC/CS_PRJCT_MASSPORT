package jds_project.scanner.init;

import java.lang.Thread.State;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;

import jds_project.scanner.engine.HEADER_PROXY;
import jds_project.scanner.engine.ScanThread;
import sockslib.common.UsernamePasswordCredentials;

public class SCAN {
	public static boolean stop = false;

	public static void main(String[] args) {
		// TODO ADD AUTH
		String username = "";
		String password = "";

		HEADER_PROXY.proxy.setCredentials(new UsernamePasswordCredentials(username, password));

		ArrayList<Integer> ports = new ArrayList<Integer>();
		ArrayList<String> addressCIDR = new ArrayList<String>();
		ArrayList<String> address = new ArrayList<String>();
		ArrayList<ScanThread> scanThreads = new ArrayList<>();
		/*
		 * 
		 * 
		 * -H=[94.75.0.0/16] -p=[25]
		 */
		// Аргументы
		for (int i = 0; i < args.length; i++) {
			System.out.println("Аргумент#" + i + ": " + args[i]);
		}
		for (int i = 0; i < args.length; i++) {
			// ПОРТЫ
			if (args[i].toLowerCase().contains("-p".toLowerCase())) {
				String portStringlower = args[i].toLowerCase();
				String pString = StringUtils.substringBetween(portStringlower, "-p=[".toLowerCase(), "]".toLowerCase());
				String[] parts = pString.split(",");
				for (int j = 0; j < parts.length; j++) {
					int a = -10;
					try {
						a = Integer.parseInt(parts[j]);
					} catch (Exception e) {
					}
					ports.add(a);
				}
			} // ПОРТЫ

			// АДРЕСА
			if (args[i].toLowerCase().contains("-H".toLowerCase())) {
				String addrStringlower = args[i].toLowerCase();
				// System.out.println("addrStringlower:"+addrStringlower);
				String aString = StringUtils.substringBetween(addrStringlower, "-h=[".toLowerCase(), "]".toLowerCase());
				String[] parts = aString.split(",");
				for (int j = 0; j < parts.length; j++) {
					if (!addressCIDR.contains(parts[j])) {
						addressCIDR.add(parts[j]);
					}
				}
			} // АДРЕСА

			for (int j = 0; j < addressCIDR.size(); j++) {
				SubnetUtils utils = new SubnetUtils(addressCIDR.get(j));
				String[] allIps = utils.getInfo().getAllAddresses();
				for (int k = 0; k < allIps.length; k++) {
					if (!address.contains(allIps[k])) {
						address.add(allIps[k]);
					}
				}
			}

		} // Аргументы
		/*
		 * for (int j = 0; j < ports.size(); j++) { System.err.println("PORT: " +
		 * ports.get(j)); } for (int j = 0; j < addressCIDR.size(); j++) {
		 * System.err.println("CIDR: " + addressCIDR.get(j)); } for (int j = 0; j <
		 * address.size(); j++) { // System.err.println("address: " + address.get(j)); }
		 */
		//
		new Thread(new Runnable() {

			@Override
			public void run() {
				do {
					for (int j = 0; j < scanThreads.size(); j++) {
						ScanThread thread = scanThreads.get(j);
						if (thread != null) {
							State state = thread.getState();
							if (state.equals(Thread.State.TERMINATED)) {
								scanThreads.remove(thread);
								System.out.println(scanThreads.size());
								thread = null;
								state = null;
							}
						}
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (stop == false);
			}
		}).start();

		for (int j = 0; j < address.size(); j++) {
			for (int j2 = 0; j2 < ports.size(); j2++) {
				while (scanThreads.size() > 2000) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				ScanThread th = new ScanThread(address.get(j), ports.get(j2));
				scanThreads.add(th);
				th.start();
			}
		}
		stop = true;
	}

}
