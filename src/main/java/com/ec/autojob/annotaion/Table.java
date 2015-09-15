package com.ec.autojob.annotaion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * ClassName: Table <br/>
 * Function: 表名注解，name为对应库的表名<br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年6月30日 下午5:31:41 <br/>
 *
 * @author xxg
 * @version 
 * @since JDK 1.7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
   public abstract String name();
}
