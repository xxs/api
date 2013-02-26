package net.xxs.util;

import java.util.regex.Pattern;

/**
 * 工具类 - 验证数据
 */

public class CheckUtil {
	
	
	/**
	 * 验证数字Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern numberPattern() {
		return Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
	}
	/**
	 * 验证15位身份证号Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern idcard15Pattern() {
		return Pattern.compile("");
	}
	/**
	 * 验证18位身份证号Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern idcard18Pattern() {
		return Pattern.compile("");
	}
	/**
	 * 验证数字Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern phonePattern() {
		return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 
	}
	/**
	 * 验证IP Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern ipPattern() {
		return Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
	}
	/**
	 * 验证邮箱Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern emailPattern() {
		return Pattern.compile("^([\\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
	}
	/**
	 * 验证字母Pattern
	 * 
	 * @return Pattern
	 */
	public static Pattern alphaintPattern() {
		return Pattern.compile("[a-zA-Z]+");
	}
	/**
	 * 验证数字
	 * 
	 * @return Boolean
	 */
	public static Boolean numberCheck(String param) {
		return CheckUtil.numberPattern().matcher(param).matches(); 
	}
	/**
	 * 验证字母
	 * 
	 * @return Boolean
	 */
	public static Boolean alphaintCheck(String param) {
		return CheckUtil.alphaintPattern().matcher(param).matches(); 
	}
	/**
	 * 验证邮箱
	 * 
	 * @return Boolean
	 */
	public static Boolean emailCheck(String param) {
		return CheckUtil.emailPattern().matcher(param).matches(); 
	}
	/**
	 * 验证ip
	 * 
	 * @return Boolean
	 */
	public static Boolean ipCheck(String param) {
		return CheckUtil.ipPattern().matcher(param).matches(); 
	}
	/**
	 * 验证 手机号码
	 * 
	 * @return Boolean
	 */
	public static Boolean phoneCheck(String param) {
		return CheckUtil.phonePattern().matcher(param).matches(); 
	}
}