package net.xxs.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {
	
	private static final DateFormat[] FORM_DATE_FORMATS = {new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")};
	private static final DateFormat TO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@SuppressWarnings("unchecked")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
        }
		
		Date date = null;
		String value = values[0];
		if (StringUtils.isNotEmpty(value)) {
			for (DateFormat dateFormat : FORM_DATE_FORMATS) {
				try {
                	date = dateFormat.parse(value);
                	if (date != null) {
                		break;
                	}
                } catch (ParseException ignore) {}
            }
		}
		return date;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String convertToString(Map context, Object object) {
		if (object instanceof Date) {
			DateFormat dateFormat = TO_DATE_FORMAT;
			return dateFormat.format((Date)object);
		}
        return "";
	}

}
