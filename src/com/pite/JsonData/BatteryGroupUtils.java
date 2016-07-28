package com.pite.JsonData;

/**
 * �������Ϣ
 */
public class BatteryGroupUtils {
	private int nodeid; // ID
	private String nodename; // վ��
	private String groupname; // ����
	private int device; // ���״̬
	private int network; // ��������
	private int groupStatus; // �豸״̬
	private	String rooturl;
	// ��������
	private String typename; // ��������
	private int equipmentStatus; // ���״̬
	private int netStatus; // ����״̬
	// ͼ�� �ı���
	private String batterytypeName; // �������
	private String nominalR; // ��׼����
	private String vMin; // ��С��ѹ
	private String vMinN;
	private String rMaxN;
	private String rMax; // �������
	private String testgroupvoltage;// ���ѹ
	private String testgroupampere; // �����
	private int groupID; // ��ط���ID
	private String status; // ֱ��ͼ״̬
	private String workStatus;
	private int nodeType;
	private String devtype;
	private String wSColor;
	private String gScolor;
	private String wSName;
	private String gSname;
	private String hctype;
	//2D��ͼ����
	private String longitude;
	//2D��ͼγ��
	private String latitude;
	
	//�������ͱ�ʶ
	private String testType;
	
	//�澯��Ϣ
	//�¼�
	private String alarmMsg;
	//ʱ��
	private String Alarmtime;
	//�澯��ʶ��
	private String AlarmFlag;
	
	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String gethctype() {
		return hctype;
	}

	public void sethctype(String hctype) {
		this.hctype = hctype;
	}
	public String getwSColor() {
		return wSColor;
	}

	public void setwSColor(String wSColor) {
		this.wSColor = wSColor;
	}

	public String getgScolor() {
		return gScolor;
	}

	public void setgScolor(String gScolor) {
		this.gScolor = gScolor;
	}

	public String getwSName() {
		return wSName;
	}

	public void setwSName(String wSName) {
		this.wSName = wSName;
	}

	public String getgSname() {
		return gSname;
	}

	public void setgSname(String gSname) {
		this.gSname = gSname;
	}

	public String getDevtype() {
		return devtype;
	}

	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}
	public String getRooturl() {
		return rooturl;
	}
	
	public void setRooturl(String rooturl) {
		this.rooturl = rooturl;
	}

	public int getNodeType() {
		return nodeType;
	}

	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getvMinN() {
		return vMinN;
	}

	public void setvMinN(String vMinN) {
		this.vMinN = vMinN;
	}

	public String getrMaxN() {
		return rMaxN;
	}

	public void setrMaxN(String rMaxN) {
		this.rMaxN = rMaxN;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getBatterytypeName() {
		return batterytypeName;
	}

	public void setBatterytypeName(String batterytypeName) {
		this.batterytypeName = batterytypeName;
	}

	public String getNominalR() {
		return nominalR;
	}

	public void setNominalR(String nominalR) {
		this.nominalR = nominalR;
	}

	public String getvMin() {
		return vMin;
	}

	public void setvMin(String vMin) {
		this.vMin = vMin;
	}

	public String getrMax() {
		return rMax;
	}

	public void setrMax(String rMax) {
		this.rMax = rMax;
	}

	public String getTestgroupvoltage() {
		return testgroupvoltage;
	}

	public void setTestgroupvoltage(String testgroupvoltage) {
		this.testgroupvoltage = testgroupvoltage;
	}

	public String getTestgroupampere() {
		return testgroupampere;
	}

	public void setTestgroupampere(String testgroupampere) {
		this.testgroupampere = testgroupampere;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(int equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public int getNetStatus() {
		return netStatus;
	}

	public void setNetStatus(int netStatus) {
		this.netStatus = netStatus;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	public int getNodeid() {
		return this.nodeid;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getNodename() {
		return this.nodename;
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public int getDevice() {
		return this.device;
	}

	public void setNetwork(int network) {
		this.network = network;
	}

	public int getNetwork() {
		return this.network;
	}

	public void setGroupStatus(int groupStatus) {
		this.groupStatus = groupStatus;
	}

	public int getGroupStatus() {
		return this.groupStatus;
	}

	public String getAlarmMsg() {
		return alarmMsg;
	}

	public void setAlarmMsg(String alarmMsg) {
		this.alarmMsg = alarmMsg;
	}

	public String getAlarmtime() {
		return Alarmtime;
	}

	public void setAlarmtime(String alarmtime) {
		Alarmtime = alarmtime;
	}

	public String getAlarmFlag() {
		return AlarmFlag;
	}

	public void setAlarmFlag(String alarmFlag) {
		AlarmFlag = alarmFlag;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "BatteryGroupUtils [nodeid=" + nodeid + ", nodename=" + nodename + ", groupname=" + groupname
				+ ", device=" + device + ", network=" + network + ", groupStatus=" + groupStatus + ", rooturl="
				+ rooturl + ", typename=" + typename + ", equipmentStatus=" + equipmentStatus + ", netStatus="
				+ netStatus + ", batterytypeName=" + batterytypeName + ", nominalR=" + nominalR + ", vMin=" + vMin
				+ ", vMinN=" + vMinN + ", rMaxN=" + rMaxN + ", rMax=" + rMax + ", testgroupvoltage=" + testgroupvoltage
				+ ", testgroupampere=" + testgroupampere + ", groupID=" + groupID + ", status=" + status
				+ ", workStatus=" + workStatus + ", nodeType=" + nodeType + ", devtype=" + devtype + ", wSColor="
				+ wSColor + ", gScolor=" + gScolor + ", wSName=" + wSName + ", gSname=" + gSname + ", hctype=" + hctype
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", testType=" + testType + ", alarmMsg="
				+ alarmMsg + ", Alarmtime=" + Alarmtime + ", AlarmFlag=" + AlarmFlag + "]";
	}

	
	
	
}
