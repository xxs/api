package net.xxs.util;
/**
 * 
 * 
 * @author Administrator
 * 
 */
public class XmlStringParse {
	private String src;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public XmlStringParse(String src) {
		this.src = src;
	}

	public String getParameter(String name) {
		String tempstr = src;
		if (tempstr == null) {
			return "";
		}
		int si = tempstr.indexOf("<" + name + ">");
		if (si == -1) {
			return "";
		} else {
			tempstr = tempstr.substring(si + name.length() + 2);
		}
		int ei = tempstr.indexOf("</" + name + ">");
		String ret = tempstr.substring(0, ei);
		if (ret == null) {
			return "";
		} else {
			return ret;
		}
	}

	public static void main(String args[]) {
		XmlStringParse xml = new XmlStringParse(
				"<?xml version=\"1.0\" encoding=\"gb2312\"?><orderinfo><err_msg>��ʱ��֧�ָ������ĳ�ֵ����</err_msg><retcode>11</retcode><orderid></orderid><cardid>140101</cardid><cardnum>1.0</cardnum><ordercash></ordercash><cardname></cardname><sporder_id>A201105302144081000_3</sporder_id><game_userid>19999999999</game_userid><game_area></game_area><game_srv></game_srv><game_state></game_state></orderinfo>");
		System.out.println(xml.getParameter("orderinfo") + "|");
		System.out.println("|" + xml.getParameter("err_msg") + "|");
		System.out.println(xml.getParameter("game_state") + "|");
		System.out.println(xml.getParameter("game_area") + "|");
		System.out.println(xml.getParameter("retcode") + "|");

	}
}
