package com.ec.autojob.util;
import java.io.InputStream;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 
 * ClassName: PropertiesUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年6月30日 下午2:51:23 <br/>
 * @author xxg
 * @version 
 * @since JDK 1.7
 */
public class PropertiesUtil {
	
	//private static Properties properties=new Properties();
	private static Document taskDoc;//自动任务定义
	
	/*static{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		// 获得配置文件
		InputStream is = cl.getResourceAsStream("/config.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					is = null;
				}
			}
		}
	}
	
    public static String get(String key){
    	Object object=properties.get(key);
    	if(object==null){
    		return null;
    	}else{
    		return object.toString();
    	}
    }*/
    
    public static Element getTaskElement(String id) throws Exception{
    	
		if(taskDoc==null){
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			// 获得配置文件
			InputStream is = cl.getResourceAsStream("task.xml");
			SAXReader saxReader = new SAXReader();
			taskDoc = saxReader.read(is);
		}
		@SuppressWarnings("rawtypes")
		List listTask = taskDoc.selectNodes("/tasks/task");
		for(Object o : listTask){
			Element task = (Element)o;
			if(id.equals(task.attributeValue("id"))){
				return task;
			}
		}
		return null;
	}
     
    
     
}
