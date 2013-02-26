<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${article.title}<#if setting.isShowPoweredInfo> - XXS</#if></title>
<meta name="Author" content="XXS-DW" />
<meta name="Copyright" content="XXS" />
<#if (article.metaKeywords)! != ""><meta name="keywords" content="${article.metaKeywords}" /></#if>
<#if (article.metaDescription)! != ""><meta name="description" content="${article.metaDescription}" /></#if>
<#include "/WEB-INF/template/card/head.ftl">
</head>
<body class="articleContent">
	<#include "/WEB-INF/template/card/header.ftl">
	<div class="column">
	<div class="column_left">
        <ul>
						<@article_list article_category_id=article.articleCategory.id type="hot" count=10; articleList>
							<#list articleList as article>
								<li class="number${article_index + 1}">
									<a href="${base}${article.htmlPath}" title="${article.title}">${substring(article.title, 24, "...")}</a>
								</li>
							</#list>
						</@article_list>		
        </ul>
    </div>
     <div class="column_right">
   	    <div class="column_right_1"><p>您尚未登陆，请点击<a href="${base}/card/promotion.action">登陆</a>立即参与到分享二维码，推荐会员拿提成活动</div>
        <div class="column_right_2_shang">
                	<div class="title">
	                     	<a href="${base}${article.htmlPath}">
								${substring(article.title, 40, "...")}
							</a>
						</div>
                          <span class="author">
                            	${article.contentText}
                                	<div class="info">
										作者: <#if article.author == "">未知<#else>${article.author}</#if>&nbsp;
										${article.createDate}&nbsp;
										点击: <a id="hits"></a> 次&nbsp;
									</div>
                                </span>
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
	<script type="text/javascript">
		$().ready( function() {
		
			$hits = $("#hits");
		
			// 统计文章点击数
			$.ajax({
				url: "${base}/card/article!ajaxHits.action?id=${article.id}",
				dataType: "json",
				success: function(data) {
					if (data.status == "success") {
						$hits.text(data.hits);
					}
				}
			});
		
		});
		</script>
</body>
</html>
