package net.xxs.bean;


/**
 * Bean类 - card信息
 */

public class Card {
	
	private String num;// 卡号
	private String pwd;// 密码
	private String face;// 面额
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	
}