package com.ec.autojob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;





/**
 * Hello world!
 *
 */
public class App 
{   
	private static final Logger LOG = LogManager.getLogger("appLog");

    private static ApplicationContext context;
    
    public static void main(String[] args ){
    	 LOG.info("Main is start..........");
    	 GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    	 ctx.getEnvironment().setActiveProfiles("production");
        // ctx.getEnvironment().setActiveProfiles("develop");
         ctx.load("classpath*:/spring/applicationContext.xml");

         ctx.refresh();
         context = ctx;
       // System.out.println(ctx.getBean(ECNsqProducer.class));
    
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
