package jds_project.scanner.init;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;

import jds_project.scanner.engine.ScanThread;

public class SCAN {
	public static void main(String[] args) {
		ArrayList<Integer> ports = new ArrayList<Integer>();
		ArrayList<String> addressCIDR = new ArrayList<String>();
		ArrayList<String> address = new ArrayList<String>();

		// Аргументы
		for (int i = 0; i < args.length; i++) {
			System.out.println("Аргумент" + args[i]);
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
		for (int j = 0; j < address.size(); j++) {
			for (int j2 = 0; j2 < ports.size(); j2++) {
				new ScanThread(address.get(j), ports.get(j2)).start();
			}
		}
		//
	}

}
