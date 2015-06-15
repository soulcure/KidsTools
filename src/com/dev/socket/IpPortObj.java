package com.dev.socket;

/**
 * 网络IP port封装，保证ip和port的对应关系、有效性
 * 
 * @author FWQ 20130530
 */
public class IpPortObj {

	private String ip = null;

	private int port = 0;

	public IpPortObj(String _ip, int _port) {
		if (!isValidIpPort(_ip, _port)) {
			throw new IllegalArgumentException("create IpPortObj is error");
		}
		ip = _ip;
		port = _port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	/**
	 * 判断指定IP和端口有效性
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	public static boolean isValidIpPort(String ip, int port) {
		return isValidIp(ip) && isValidPort(port);

	}

	public static boolean isValidIp(String ip) {
		boolean b = false;
		if (ip != null) {
			b = true;
			String args[] = ip.split("\\.");
			if (args != null && args.length == 4) {
				try {
					for (int i = 0; i < args.length; i++) {
						int ipb = Integer.parseInt(args[i]);
						if (ipb < 0 || ipb > 255) {
							b = false;
							break;
						}
					}
				} catch (Exception e) {
					b = false;
				}
			} else {
				b = false;
			}
		}
		return b;
	}

	public static boolean isValidPort(int port) {
		return port >= 1024 && port <= 65535;
	}
}
