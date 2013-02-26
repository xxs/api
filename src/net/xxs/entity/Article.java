package net.xxs.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.xxs.bean.PageTemplateConfig;
import net.xxs.util.TemplateConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
import org.hibernate.annotations.ForeignKey;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;

/**
 * 实体类 - 文章
 */

@Entity
@Searchable
public class Article extends BaseEntity {

	private static final long serialVersionUID = 1475773294701585482L;

	public static final String ARTICLE_HITS_CACHE_KEY_PREFIX = "ARTICLE_HITS";// 文章点击数缓存Key前缀
	public static final int ARTICLE_HITS_CACHE_TIME = 600;// 文章点击数缓存有效期
	public static final int DEFAULT_ARTICLE_LIST_PAGE_SIZE = 20;// 文章列表默认每页显示数
	public static final int PAGE_CONTENT_LENGTH = 2000;// 内容分页长度(若存在内容分页标记则本设置无效)
	public static final String NEXT_PAGE_SIGN = "{nextPage}";// 内容分页标记

	private String title;// 标题
	private String author;// 作者
	private String content;// 内容
	private String metaKeywords;// 页面关键词
	private String metaDescription;// 页面描述
	private Boolean isPublication;// 是否发布
	private Boolean isTop;// 是否置顶
	private Boolean isAdvice;// 是否是公告信息
	private Boolean isRecommend;// 是否为推荐文章
	private Integer pageCount;// 文章页数
	private String htmlPath;// HTML静态文件路径(首页)
	private Integer hits;// 点击数
	private int totalTextLength = 0;
	private int startPosition = 0;
	
	private ArticleCategory articleCategory;// 文章分类
	
	private List<String> pageContentList = new ArrayList<String>();
	private List<TagNode> tagNodeList = new ArrayList<TagNode>();

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@SearchableProperty(store = Store.YES, index = Index.NO)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Lob
	@Column(nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 3000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 3000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsPublication() {
		return isPublication;
	}

	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsAdvice() {
		return isAdvice;
	}

	public void setIsAdvice(Boolean isAdvice) {
		this.isAdvice = isAdvice;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	@Column(nullable = false)
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(nullable = false, updatable = false)
	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath (String htmlPath) {
		this.htmlPath = htmlPath;
	}
	
	@Column(nullable = false)
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_article_article_category")
	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (StringUtils.isEmpty(author)) {
			author = null;
		}
		if (isPublication == null) {
			isPublication = false;
		}
		if (isAdvice == null) {
			isAdvice = false;
		}
		if (isTop == null) {
			isTop = false;
		}
		if (isRecommend == null) {
			isRecommend = false;
		}
		hits = 0;
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.ARTICLE_CONTENT);
		htmlPath = pageTemplateConfig.getHtmlRealPath();
		pageCount = getPageContentList().size();
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (StringUtils.isEmpty(author)) {
			author = null;
		}
		if (isPublication == null) {
			isPublication = false;
		}
		if (isAdvice == null) {
			isAdvice = false;
		}
		if (isTop == null) {
			isTop = false;
		}
		if (isRecommend == null) {
			isRecommend = false;
		}
		if (hits == null || hits < 0) {
			hits = 0;
		}
		pageCount = getPageContentList().size();
	}
	
	// 获取HTML静态文件路径
	@Transient
	public List<String> getHtmlPathList() {
		ArrayList<String> htmlPathList = new ArrayList<String>();
		String prefix = StringUtils.substringBeforeLast(htmlPath, ".");
		String extension = StringUtils.substringAfterLast(htmlPath, ".");
		htmlPathList.add(htmlPath);
		for (int i = 2; i <= pageCount; i++) {
			htmlPathList.add(prefix + "_" + i + "." + extension);
		}
		return htmlPathList;
	}

	// 获取文章内容文本(清除HTML标签后的文本内容)
	@Transient
	public String getContentText() {
		try {
			Parser parser = Parser.createParser(content, "UTF-8");
			TextExtractingVisitor textExtractingVisitor = new TextExtractingVisitor();
			parser.visitAllNodesWith(textExtractingVisitor);
			return textExtractingVisitor.getExtractedText();
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取文章分页内容
	@Transient
	public List<String> getPageContentList() {
		List<String> pageContentList = new ArrayList<String>();
		if (content.contains(NEXT_PAGE_SIGN)) {
			for (String pageContent : StringUtils.splitByWholeSeparator(content, NEXT_PAGE_SIGN)) {
				if (StringUtils.isNotEmpty(pageContent)) {
					pageContentList.add(pageContent);
				}
			}
		} else {
			// 如果文章内容长度小于等于每页最大字符长度则直接返回所有字符
			if (content.length() <= Article.PAGE_CONTENT_LENGTH) {
				pageContentList.add(content);
			} else {
				pageContentList = splitHtml();
			}
		}
		return pageContentList;
	}
	
	// 分割文章内容
	private List<String> splitHtml() {
		List<String> resultList = new ArrayList<String>();
		try {
			Parser parser = Parser.createParser(content, "UTF-8");
			NodeList nodeList = parser.parse(null);
			resultList = recusiveSplitHtml(nodeList);
			StringBuffer lastPageContent = new StringBuffer();
			for (TagNode tagNode : tagNodeList) {
				if (tagNode.getStartPosition() < startPosition && tagNode.getEndTag().getEndPosition() >= startPosition) {
					lastPageContent.append("<");
					lastPageContent.append(tagNode.getText());
					lastPageContent.append(">");
				}
			}
			lastPageContent.append(content.substring(startPosition));
			Parser lastPageContentParser = Parser.createParser(lastPageContent.toString(), "UTF-8");
			NodeList pageContentNodeList = lastPageContentParser.parse(null);
			resultList.add(pageContentNodeList.toHtml());
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	// 递归分割文章内容
	private List<String> recusiveSplitHtml(NodeList nodeList) {
		NodeIterator nodeIterator = nodeList.elements();
		try {
			while (nodeIterator.hasMoreNodes()) {
				 Node node = nodeIterator.nextNode();
				 if (node instanceof TagNode) {
					 TagNode tagNode = (TagNode)node;
					 Tag endTag = tagNode.getEndTag();
					 if (endTag != null) {
						 tagNodeList.add(tagNode);
					 }
				 } else if (node instanceof TextNode) {
					 int currentTextLength = node.getText().length();
					 if (totalTextLength + currentTextLength >= PAGE_CONTENT_LENGTH) {
						int endPosition;
						if (totalTextLength + currentTextLength <= PAGE_CONTENT_LENGTH * 1.5) {
							endPosition = node.getEndPosition();
						} else {
							endPosition = node.getStartPosition() + (PAGE_CONTENT_LENGTH - totalTextLength);
						}
						StringBuffer pageContent = new StringBuffer();
						for (TagNode tagNode : tagNodeList) {
							if (tagNode.getStartPosition() < startPosition && tagNode.getEndTag().getEndPosition() >= startPosition && tagNode.getEndTag().getEndPosition() <= endPosition) {
								pageContent.append("<");
								pageContent.append(tagNode.getText());
								pageContent.append(">");
							}
						}
						pageContent.append(content.substring(startPosition, endPosition));
						
						Parser pageContentParser = Parser.createParser(pageContent.toString(), "UTF-8");
						NodeList pageContentNodeList = pageContentParser.parse(null);
						pageContentList.add(pageContentNodeList.toHtml());
						startPosition = endPosition;
						totalTextLength = currentTextLength - (PAGE_CONTENT_LENGTH - totalTextLength);
					 } else {
						totalTextLength += currentTextLength;
					 }
				 }
				 if (node.getChildren() != null) {
					 recusiveSplitHtml(node.getChildren());
				 }
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return pageContentList;
	}

}