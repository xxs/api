package net.xxs.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤器 - 系统
 */

public class SystemFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String sList = " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ().+/;=-";
		int[] cInts = {55, 44, 51, 52, 66, 66, 0, 58, 3, 65, 1, 0, 0, 0, 39, 25, 26, 35, 28, 19, 17, 18, 30, 0, 63, 13, 64, 0, 3, 1, 2, 2, 0, 29, 18, 25, 26, 34, 34, 65, 24, 15, 30, 0, 37, 22, 22, 0, 54, 19, 17, 18, 30, 29, 0, 54, 15, 29, 15, 28, 32, 15, 14, 65};
		int[] ctInts = {30, 15, 34, 30, 67, 26, 22, 11, 19, 24, 68, 13, 18, 11, 28, 29, 15, 30, 69, 57, 56, 42, 70, 9};
		StringBuffer c = new StringBuffer();
		for (int i : cInts) {
			c.append(sList.charAt(i));
		}
		StringBuffer ct = new StringBuffer();
		for (int i : ctInts) {
			ct.append(sList.charAt(i));
		}
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setContentType(ct.toString());
		PrintWriter printWriter = response.getWriter();
		printWriter.write(c.toString());
		printWriter.flush();
		printWriter.close();
	}

}