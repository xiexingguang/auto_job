package com.ec.autojob.core;

import org.quartz.Job;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ecuser on 2015/12/22.
 * 初始化job数据
 */
@Component
public  final class JobContext {

    private final static Map<String, Job> JOB_MAP = new HashMap<>();

    public static Map<String, Job> getJobMap() {
        return JOB_MAP;
    }




}
