package net.xxs.action.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.xxs.bean.Setting;
import net.xxs.util.ImageUtil;
import net.xxs.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 文件处理
 */

@ParentPackage("admin")
public class FileAction extends BaseAdminAction {

	private static final long serialVersionUID = 6614132059804452558L;
	
	private static final String[] imageFormatName = new String[] {"jpg", "jpeg", "gif", "bmp", "png"};// 图片格式名称

	private Boolean isFileAction = true;// 是否为FileAction
	private File imgFile;// 上传图片文件
	private String path;// 浏览路径
	private String order;// 排序方式

	// AJAX文件上传
	@InputConfig(resultName = "ajaxError")
	public String ajaxUpload() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (imgFile == null) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "请选择上传文件!");
			return ajax(jsonMap);
		}
		String uploadPath = ImageUtil.copyImageFile(getServletContext(), imgFile);
		jsonMap.put("error", 0);
		jsonMap.put("url", getContextPath() + uploadPath);
		return ajax(jsonMap);
	}
	
	// AJAX上传文件浏览
	@SuppressWarnings("unchecked")
	@InputConfig(resultName = "ajaxError")
	public String ajaxBrowser() throws Exception {
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		if (StringUtils.isEmpty(order)) {
			order = "name";
		}
		
		Setting setting = SettingUtil.getSetting();
		String rootPath = getRealPath(setting.getImageBrowsePath()) + "/";
		String currentPath = rootPath + "/" + path;
		String currentUrl = getContextPath() + setting.getImageBrowsePath() + "/" + path;
		String moveupDirPath = "";
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (path.indexOf("..") >= 0 || !currentPath.endsWith("/")) {
			jsonMap.put("error", 1);
			jsonMap.put("message", "此操作不被允许!");
			return ajax(jsonMap);
		}
		
		File currentPathFile = new File(currentPath);
		if(!currentPathFile.isDirectory()){
			jsonMap.put("error", 1);
			jsonMap.put("message", "当前目录不存在!");
			return ajax(jsonMap);
		}
		
		if (!"".equals(path)) {
			String str = path.substring(0, path.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hashtable.put("is_dir", true);
					hashtable.put("has_file", (file.listFiles() != null));
					hashtable.put("filesize", 0L);
					hashtable.put("is_photo", false);
					hashtable.put("filetype", "");
				} else if(file.isFile()){
					String fileExtension = StringUtils.substringAfterLast(fileName, ".").toLowerCase();
					hashtable.put("is_dir", false);
					hashtable.put("has_file", false);
					hashtable.put("filesize", file.length());
					hashtable.put("is_photo", Arrays.<String>asList(imageFormatName).contains(fileExtension));
					hashtable.put("filetype", fileExtension);
				}
				hashtable.put("filename", fileName);
				hashtable.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hashtable);
			}
		}
		
		if ("SIZE".equalsIgnoreCase(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("TYPE".equalsIgnoreCase(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		
		jsonMap.put("moveup_dir_path", moveupDirPath);
		jsonMap.put("current_dir_path", path);
		jsonMap.put("current_url", currentUrl);
		jsonMap.put("total_count", fileList.size());
		jsonMap.put("file_list", fileList);
		return ajax(jsonMap);
	}
	
	@SuppressWarnings("unchecked")
	private class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filename")).compareTo((String)hashB.get("filename"));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)hashA.get("filesize")) > ((Long)hashB.get("filesize"))) {
					return 1;
				} else if (((Long)hashA.get("filesize")) < ((Long)hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable)a;
			Hashtable hashB = (Hashtable)b;
			if (((Boolean)hashA.get("is_dir")) && !((Boolean)hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)hashA.get("is_dir")) && ((Boolean)hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String)hashA.get("filetype")).compareTo((String)hashB.get("filetype"));
			}
		}
	}

	public Boolean getIsFileAction() {
		return isFileAction;
	}

	public void setIsFileAction(Boolean isFileAction) {
		this.isFileAction = isFileAction;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}