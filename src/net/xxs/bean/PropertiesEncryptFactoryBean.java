package net.xxs.bean;

import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

public class PropertiesEncryptFactoryBean implements FactoryBean {

	private Properties properties;

	public Object getObject() throws Exception {
		return getProperties();
	}

	public Class getObjectType() {
		return java.util.Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("user");
		String originalPassword = properties.getProperty("password");
		if (originalUsername != null) {
			String newUsername = deEncryptUsername(originalUsername);
			properties.put("user", newUsername);
		}
		if (originalPassword != null) {
			String newPassword = deEncryptPassword(originalPassword);
			properties.put("password", newPassword);
		}
	}

	private String deEncryptUsername(String originalUsername) {
		return deEncryptString(originalUsername);
	}

	private String deEncryptPassword(String originalPassword) {
		return deEncryptString(originalPassword);
	}

	
	private String deEncryptString(String originalString) {
		
		
		/**/Eryptogram etg =new Eryptogram ();
		 etg.setDeskey("11111111".getBytes());
		 
		 String newString = "";
				 try {
			newString = new String(etg.DesDecryptData(etg.hextobyte(originalString)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("NNNNNNNNNNNN="+newString);
		return newString;
		
	}

}
