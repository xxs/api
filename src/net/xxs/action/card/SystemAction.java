package net.xxs.action.card;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 前台Action类 - 系统
 */

public class SystemAction extends ActionSupport {

	private static final long serialVersionUID = 4535356901892013943L;

	@Action("/SYSTEM")
	public String execute() {
		try {
			byte[] b = {120, 120,115};
			HttpServletResponse response = ServletActionContext.getResponse();
			PrintWriter printWriter = response.getWriter();
			printWriter.write(new String(b));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

}