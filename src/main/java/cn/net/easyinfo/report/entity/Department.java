package cn.net.easyinfo.report.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Department implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int index;
	private int id;
	private int fid;
	private String name;
	private String mark;
	private int categoryid;
	private int roleid;
	private int sort;
	private int flag;
	private int noticeType;
	private int setAdmin;
	private int isSend;
	private int modifyScoreRight;
	private int loginMsg; //登录是否用短信
	private int noticeMsg; //通知是否用短信
	private int isJietu; //默认0,0不调用截图脚本 1调用截图脚本
	private int appJietuShow;
	private int appTaskShow;
	
	public int getAppJietuShow() {
		return appJietuShow;
	}
	public void setAppJietuShow(int appJietuShow) {
		this.appJietuShow = appJietuShow;
	}
	public int getAppTaskShow() {
		return appTaskShow;
	}
	public void setAppTaskShow(int appTaskShow) {
		this.appTaskShow = appTaskShow;
	}
	private  Set<Integer> set=new HashSet<Integer>();
	
	public int getIsJietu() {
		return isJietu;
	}
	public void setIsJietu(int isJietu) {
		this.isJietu = isJietu;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Set<Integer> getSet() {
		return set;
	}
	public void setSet(Set<Integer> set) {
		this.set = set;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}
	public int getSetAdmin() {
		return setAdmin;
	}
	public void setSetAdmin(int setAdmin) {
		this.setAdmin = setAdmin;
	}
	public int getIsSend() {
		return isSend;
	}
	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}
	public int getModifyScoreRight() {
		return modifyScoreRight;
	}
	public void setModifyScoreRight(int modifyScoreRight) {
		this.modifyScoreRight = modifyScoreRight;
	}
	public int getLoginMsg() {
		return loginMsg;
	}
	public void setLoginMsg(int loginMsg) {
		this.loginMsg = loginMsg;
	}
	public int getNoticeMsg() {
		return noticeMsg;
	}
	public void setNoticeMsg(int noticeMsg) {
		this.noticeMsg = noticeMsg;
	}
	
}
