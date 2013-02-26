<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${articleCategory.name} 文章列表<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#if (articleCategory.metaKeywords)! != ""><meta name="keywords" content="${articleCategory.metaKeywords}" /></#if>
<#if (articleCategory.metaDescription)! != ""><meta name="description" content="${articleCategory.metaDescription}" /></#if>
<#include "/WEB-INF/template/card/head.ftl">
</head>
<body class="articleList">
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="column">
	<div class="column_left">
	<div class="column_left_1"><a href="reg.html">免费注册</a><a href="findpassword.html"  class="current" >找回密码</a><a href="login.html">立刻登陆</a>
        </div>
        <ul>
        	<@article_list article_category_id=articleCategory.id type="publication" count=10; articleList>
				<#list articleList as article>
				<li>
					<a href="${base}${article.htmlPath}" title="${article.title}">${substring(article.title, 24, "...")}</a>
				</li>
				</#list>
			</@article_list>
        </ul>
    </div>
    <div class="column_right">
   	    <div class="column_right_1"><p>您尚未登陆，请点击<a href="${base}/card/promotion.action">登陆</a>立即参与到分享二维码，推荐会员拿提成活动</div>
        <div class="column_right_2_shang">
                <#list pager.result as article>
                	<div class="title">
	                     	<a href="${base}${article.htmlPath}">
								${substring(article.title, 40, "...")}
							</a>
						</div>
                          <span class="author">
                            	${substring(article.contentText, 500, "...")}
                                	<div class="info">
										作者: <#if article.author == "">未知<#else>${article.author}</#if>${article.createDate}
										<a href="${base}${article.htmlPath}">[阅读全文]</a>
									</div>
                                </span>
                		</#list>
        </div>
    </div>
</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/card/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/card/footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/card/js/card.js"></script>
</body>
</html>