/**
 * @Package com.ec.autojob.service
 * @Description: TODO
 * @author Administrator
 * @date 2015年8月4日 下午6:54:13
 */
package com.ec.autojob.service;

import org.springframework.stereotype.Component;

/**
 * @ClassName: TclPhoneService
 * @Description: TODO
 * @author liulg
 * @param <T>
 * @date 2015年8月4日 下午6:54:13
 */
@Component
public interface TclPhoneService<T> {

    public int tclPhoneHandler();
}
