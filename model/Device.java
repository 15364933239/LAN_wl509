package com.shengsheng.checkon.entity;
/**
 * 
 * @author LiPeiTong
 * 2019年3月19日 上午8:58:12
 * <tr>设备实体类</tr>
 * 	<td></td>
 */
public class Device {
	
	private String dname;//设备名
	private String mac;//mac地址

  public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	@Override
	public String toString() {
		return "Device [dname=" + dname + ", mac=" + mac + "]";
	}
}
