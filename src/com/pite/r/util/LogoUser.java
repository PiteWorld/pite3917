package com.pite.r.util;

public class LogoUser {
	// {"ip":"http://203.191.147.81:8011/bms","webappurl":"http://203.191.147.81/BMSystem","orgid":"1","indexpage":"home.html","userid":"1011","nodeid":"0","orgname":"普禄科智能检测设备有限公司",
	// "logourl":"bmcp/logopng/logo.png","linkman":"莫志忠","telnum":"0755-26805755"}
	private String ip;
	private String webappurl;
	private String userid;
	private String orgname;
	private String logourl;
	private String linkman;
	private String orgid;
	private String indexpage;
	private String nodeid;
	private String telnum;
	// 单例模式
	private static LogoUser instance;

	public static LogoUser getInstance() {
		if (instance == null)
			instance = new LogoUser();
		return instance;
	}

	private LogoUser() {

	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getIndexpage() {
		return indexpage;
	}

	public void setIndexpage(String indexpage) {
		this.indexpage = indexpage;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getWebappurl() {
		return webappurl;
	}

	public void setWebappurl(String webappurl) {
		this.webappurl = webappurl;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getLogourl() {
		return logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

}
