package net.xxs.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.xxs.util.CommonUtil;

import org.apache.commons.lang.StringUtils;

/**
 * Bean类 - 系统设置
 */

public class Setting {
	
	// 货币种类(人民币、美元、欧元、英磅、加拿大元、澳元、卢布、港币、新台币、韩元、新加坡元、新西兰元、日元、马元、瑞士法郎、瑞典克朗、丹麦克朗、兹罗提、挪威克朗、福林、捷克克朗、葡币)
	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	};
	
	// 小数位精确方式(四舍五入、向上取整、向下取整)
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	// 水印位置(无、左上、右上、居中、左下、右下)
	public enum WatermarkPosition {
		no, topLeft, topRight, center, bottomLeft, bottomRight
	}
	
	// 积分获取方式(禁用积分获取、按订单总额计算、为充值卡单独设置)
	public enum ScoreType {
		disable, orderAmount, cardsSet
	}
	
	// 在线客服位置(左、右)
	public enum InstantMessagingPosition {
		left, right
	}
	
	// 在线留言显示方式(立即显示、回复后显示)
	public enum LeaveMessageDisplayType {
		direct, reply
	}
	
	// 运算符(加、减、乘、除)
	public enum Operator {
		add, subtract, multiply, divide
	}

	public static final String HOT_SEARCH_SEPARATOR = ",";// 热门搜索分隔符

	private String systemName;// 系统名称
	private String systemVersion;// 系统版本
	private String systemDescription;// 系统描述
	private String contextPath;// 虚拟路径
	private String imageUploadPath;// 图片上传路径
	private String imageBrowsePath;// 图片浏览路径
	private String adminLoginUrl;// 后台登录URL
	private String adminLoginProcessingUrl;// 后台登录处理URL
	private Boolean isShowPoweredInfo;// 是否显示Powered信息
	private String cardName;// 网店名称
	private String cardUrl;// 网店网址
	private String cardLogoPath;// 网店LOGO路径
	private String hotSearch;// 热门搜索关键词
	private String metaKeywords;// 首页页面关键词
	private String metaDescription;// 首页页面描述
	private String address;// 联系地址
	private String phone;// 联系电话
	private String zipCode;// 邮编
	private String email;// 联系email
	private String certtext;// 备案号
	private CurrencyType currencyType;// 货币种类
	private String currencySign;// 货币符号
	private String currencyUnit;// 货币单位
	private Integer priceScale;// 价格精确位数
	private RoundType priceRoundType;// 价格精确方式
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数,超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位: 分钟,0表示永久锁定)
	private Boolean isRegisterEnabled;// 是否开放注册
	private Integer withdrawEveryDayCount;// 每天允许提现的最大次数,超出次数后不能提现(单位: 次,0表示不限制)
	private Integer withdrawEveryDayMoney;// 每天允许提现的最大金额,超出金额后不能提现(单位: 元,0表示不限制)
	private Integer withdrawEveryMaxMoney;// 每次提现最大金额(单位: 元,0表示不限制)
	private Integer withdrawEveryMinMoney;// 每次提现最小金额(单位: 元,0表示不限制)
	private Integer withdrawMaxCount;// 提现中的提现最大次数(单位: 次,0表示不限制)
	private Integer withdrawMaxMoney;// 提现中的提现最大金额(单位: 元,0表示不限制)
	private String watermarkImagePath; // 水印图片路径
	private WatermarkPosition watermarkPosition; // 水印位置
	private Integer watermarkAlpha;// 水印透明度
	private Integer bigCardsImageWidth;// 充值卡图片(大)宽度
	private Integer bigCardsImageHeight;// 充值卡图片(大)高度
	private Integer smallCardsImageWidth;// 充值卡图片(小)宽度
	private Integer smallCardsImageHeight;// 充值卡图片(小)高度
	private Integer thumbnailCardsImageWidth;// 充值卡缩略图宽度
	private Integer thumbnailCardsImageHeight;// 充值卡缩略图高度
	private String defaultBigCardsImagePath;// 默认充值卡图片(大)
	private String defaultSmallCardsImagePath;// 默认充值卡图片(小)
	private String defaultThumbnailCardsImagePath;// 默认缩略图
	private String smtpFromMail;// 发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;// SMTP服务器端口
	private String smtpUsername;// SMTP用户名
	private String smtpPassword;// SMTP密码
	private ScoreType scoreType;// 积分获取方式
	private Double scoreScale;// 积分换算比率
	private Integer buildHtmlDelayTime;// 生成HTML任务延时(单位: 秒)
	private Boolean isGzipEnabled;// 是否开启GZIP功能
	private Boolean isInstantMessagingEnabled;// 是否开启在线客服功能
	private InstantMessagingPosition instantMessagingPosition;// 在线客服位置
	private String instantMessagingTitle;// 在线客服标题
	private Boolean isLeaveMessageEnabled;// 是否开启在线留言功能
	private Boolean isLeaveMessageCaptchaEnabled;// 是否开启在线留言验证码功能
	private LeaveMessageDisplayType leaveMessageDisplayType;// 在线留言显示方式
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getImageUploadPath() {
		return imageUploadPath;
	}

	public void setImageUploadPath(String imageUploadPath) {
		this.imageUploadPath = StringUtils.removeEnd(imageUploadPath, "/");
	}
	
	public String getImageBrowsePath() {
		return imageBrowsePath;
	}

	public void setImageBrowsePath(String imageBrowsePath) {
		this.imageBrowsePath = StringUtils.removeEnd(imageBrowsePath, "/");
	}

	public String getAdminLoginUrl() {
		return adminLoginUrl;
	}

	public void setAdminLoginUrl(String adminLoginUrl) {
		this.adminLoginUrl = adminLoginUrl;
	}

	public String getAdminLoginProcessingUrl() {
		return adminLoginProcessingUrl;
	}

	public void setAdminLoginProcessingUrl(String adminLoginProcessingUrl) {
		this.adminLoginProcessingUrl = adminLoginProcessingUrl;
	}

	public Boolean getIsShowPoweredInfo() {
		return isShowPoweredInfo;
	}

	public void setIsShowPoweredInfo(Boolean isShowPoweredInfo) {
		this.isShowPoweredInfo = isShowPoweredInfo;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = StringUtils.removeEnd(cardUrl, "/");
	}

	public String getCardLogoPath() {
		return cardLogoPath;
	}

	public void setCardLogoPath(String cardLogoPath) {
		this.cardLogoPath = cardLogoPath;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public Integer getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public Boolean getIsRegisterEnabled() {
		return isRegisterEnabled;
	}

	public void setIsRegisterEnabled(Boolean isRegisterEnabled) {
		this.isRegisterEnabled = isRegisterEnabled;
	}

	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}

	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}

	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	public Integer getBigCardsImageWidth() {
		return bigCardsImageWidth;
	}

	public void setBigCardsImageWidth(Integer bigCardsImageWidth) {
		this.bigCardsImageWidth = bigCardsImageWidth;
	}

	public Integer getBigCardsImageHeight() {
		return bigCardsImageHeight;
	}

	public void setBigCardsImageHeight(Integer bigCardsImageHeight) {
		this.bigCardsImageHeight = bigCardsImageHeight;
	}

	public Integer getSmallCardsImageWidth() {
		return smallCardsImageWidth;
	}

	public void setSmallCardsImageWidth(Integer smallCardsImageWidth) {
		this.smallCardsImageWidth = smallCardsImageWidth;
	}

	public Integer getSmallCardsImageHeight() {
		return smallCardsImageHeight;
	}

	public void setSmallCardsImageHeight(Integer smallCardsImageHeight) {
		this.smallCardsImageHeight = smallCardsImageHeight;
	}

	public Integer getThumbnailCardsImageWidth() {
		return thumbnailCardsImageWidth;
	}

	public void setThumbnailCardsImageWidth(Integer thumbnailCardsImageWidth) {
		this.thumbnailCardsImageWidth = thumbnailCardsImageWidth;
	}

	public Integer getThumbnailCardsImageHeight() {
		return thumbnailCardsImageHeight;
	}

	public void setThumbnailCardsImageHeight(Integer thumbnailCardsImageHeight) {
		this.thumbnailCardsImageHeight = thumbnailCardsImageHeight;
	}

	public String getDefaultBigCardsImagePath() {
		return defaultBigCardsImagePath;
	}

	public void setDefaultBigCardsImagePath(String defaultBigCardsImagePath) {
		this.defaultBigCardsImagePath = defaultBigCardsImagePath;
	}

	public String getDefaultSmallCardsImagePath() {
		return defaultSmallCardsImagePath;
	}

	public void setDefaultSmallCardsImagePath(String defaultSmallCardsImagePath) {
		this.defaultSmallCardsImagePath = defaultSmallCardsImagePath;
	}

	public String getDefaultThumbnailCardsImagePath() {
		return defaultThumbnailCardsImagePath;
	}

	public void setDefaultThumbnailCardsImagePath(String defaultThumbnailCardsImagePath) {
		this.defaultThumbnailCardsImagePath = defaultThumbnailCardsImagePath;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public ScoreType getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}

	public Double getScoreScale() {
		return scoreScale;
	}

	public void setScoreScale(Double scoreScale) {
		this.scoreScale = scoreScale;
	}
	
	public Integer getBuildHtmlDelayTime() {
		return buildHtmlDelayTime;
	}

	public void setBuildHtmlDelayTime(Integer buildHtmlDelayTime) {
		this.buildHtmlDelayTime = buildHtmlDelayTime;
	}

	public Boolean getIsGzipEnabled() {
		return isGzipEnabled;
	}

	public void setIsGzipEnabled(Boolean isGzipEnabled) {
		this.isGzipEnabled = isGzipEnabled;
	}

	public Boolean getIsInstantMessagingEnabled() {
		return isInstantMessagingEnabled;
	}

	public void setIsInstantMessagingEnabled(Boolean isInstantMessagingEnabled) {
		this.isInstantMessagingEnabled = isInstantMessagingEnabled;
	}

	public InstantMessagingPosition getInstantMessagingPosition() {
		return instantMessagingPosition;
	}

	public void setInstantMessagingPosition(InstantMessagingPosition instantMessagingPosition) {
		this.instantMessagingPosition = instantMessagingPosition;
	}

	public String getInstantMessagingTitle() {
		return instantMessagingTitle;
	}

	public void setInstantMessagingTitle(String instantMessagingTitle) {
		this.instantMessagingTitle = instantMessagingTitle;
	}

	public Boolean getIsLeaveMessageEnabled() {
		return isLeaveMessageEnabled;
	}

	public void setIsLeaveMessageEnabled(Boolean isLeaveMessageEnabled) {
		this.isLeaveMessageEnabled = isLeaveMessageEnabled;
	}

	public Boolean getIsLeaveMessageCaptchaEnabled() {
		return isLeaveMessageCaptchaEnabled;
	}

	public void setIsLeaveMessageCaptchaEnabled(Boolean isLeaveMessageCaptchaEnabled) {
		this.isLeaveMessageCaptchaEnabled = isLeaveMessageCaptchaEnabled;
	}

	public LeaveMessageDisplayType getLeaveMessageDisplayType() {
		return leaveMessageDisplayType;
	}

	public void setLeaveMessageDisplayType(LeaveMessageDisplayType leaveMessageDisplayType) {
		this.leaveMessageDisplayType = leaveMessageDisplayType;
	}

	public Integer getWithdrawEveryDayCount() {
		return withdrawEveryDayCount;
	}

	public void setWithdrawEveryDayCount(Integer withdrawEveryDayCount) {
		this.withdrawEveryDayCount = withdrawEveryDayCount;
	}

	public Integer getWithdrawEveryDayMoney() {
		return withdrawEveryDayMoney;
	}

	public void setWithdrawEveryDayMoney(Integer withdrawEveryDayMoney) {
		this.withdrawEveryDayMoney = withdrawEveryDayMoney;
	}

	public Integer getWithdrawEveryMaxMoney() {
		return withdrawEveryMaxMoney;
	}

	public void setWithdrawEveryMaxMoney(Integer withdrawEveryMaxMoney) {
		this.withdrawEveryMaxMoney = withdrawEveryMaxMoney;
	}

	public Integer getWithdrawEveryMinMoney() {
		return withdrawEveryMinMoney;
	}

	public void setWithdrawEveryMinMoney(Integer withdrawEveryMinMoney) {
		this.withdrawEveryMinMoney = withdrawEveryMinMoney;
	}

	public Integer getWithdrawMaxCount() {
		return withdrawMaxCount;
	}

	public void setWithdrawMaxCount(Integer withdrawMaxCount) {
		this.withdrawMaxCount = withdrawMaxCount;
	}

	public Integer getWithdrawMaxMoney() {
		return withdrawMaxMoney;
	}

	public void setWithdrawMaxMoney(Integer withdrawMaxMoney) {
		this.withdrawMaxMoney = withdrawMaxMoney;
	}

	// 获取热门搜索关键词集合
	public List<String> getHotSearchList() {
		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
	}
	
	// 获取图片上传真实路径
	public String getImageUploadRealPath() {
		return CommonUtil.getPreparedStatementPath(imageUploadPath);
	}

}