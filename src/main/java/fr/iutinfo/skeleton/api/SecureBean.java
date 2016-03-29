package fr.iutinfo.skeleton.api;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureBean {
	
	private Logger logger = LoggerFactory.getLogger(SecureBean.class);
	
	public void sanitize(){
		 Class c = this.getClass();
		  Method[] m = c.getMethods();

		  ArrayList<String> getters = new ArrayList<String>();
		  ArrayList<String> setters = new ArrayList<String>();
		  //On parcourt le tableau de méthodes
		  for(int i = 0; i < m.length; i++)
		  {
			  String method =  m[i].getName();//m[i].toString().substring(m[i].toString().lastIndexOf('.') +1);
			  //logger.debug("---------------------------------------------------------");
			  //logger.debug("Method : " + method);
			   Class[] p = m[i].getParameterTypes();
			  if(method.startsWith("set") && p[0].getName().equals("java.lang.String")){
				  //logger.debug("Setter : " + method);
				  setters.add(method);
			  } else if(method.startsWith("get") && !method.equals("getClass")){
				  //logger.debug("Getter : " + method);
				  getters.add(method);
			  }

			  //logger.debug("---------------------------------------------------------");
		  }
		  try {
			  	Class cl = getClass();
				 for(int i = 0; i < setters.size(); i++){
					 int gId = getters.indexOf("get" + setters.get(i).substring(3));
					 Method get = cl.getMethod(getters.get(gId), null);
					 Method set = cl.getMethod(setters.get(i), String.class);
					 String value = (String) get.invoke(this, null);
					 logger.debug("value : " + value);
					 set.invoke(this, ESAPI.encoder().encodeForHTML(value));
				 }
		  } catch(Exception e){
			  logger.debug(e.toString());
		  }
	}
}
