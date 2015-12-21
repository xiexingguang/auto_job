package com.ec.autojob.pushPackageDataJob;

import com.ec.apipackage.bean.PackageBean;
import com.ec.apipackage.service.PackageCorpService;
import com.ec.autojob.nsq.ECNsqProducer;
import com.ec.autojob.properties.ConfigProperties;
import com.ec.autojob.util.DataUtil;
import com.ec.autojob.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ecuser on 2015/12/17.
 */
@Component(value="packageDataJob")
public class PackageDataJob implements Job {

    private static final Logger LOG = LogManager.getLogger("collectJobLog");
   @Autowired
    public PackageCorpService packageCorpService;

    @Autowired
    public ConfigProperties configProperties;

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("同步套餐信息任务开始执行==================,执行时间" + new Date().toLocaleString());
        //  init data
        String packageIds = configProperties.PACKAGE_IDS;
        String beginTimePro = configProperties.beginTime;
        String endTimePro = configProperties.endTime;
        String beginTime = null;
        String endTime = null;
        SimpleDateFormat spf = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat spf2 = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        System.out.println("============>"+beginTimePro);
        //jude the data
        if (StringUtil.isNullString(beginTimePro) || StringUtil.isNullString(endTimePro)) { // 如果配置文件木有，则使用默认时间间隔
            beginTime = spf.format(DataUtil.getNextDay(new Date()));
            endTime = spf2.format(DataUtil.getNextDay(new Date()));
        } else {
            beginTime = beginTimePro;
            endTime = endTimePro;
            if (beginTime.equals(endTime)) {
                LOG.warn("时间间隔为同一天");
            }
        }
        List<String> packages = Arrays.asList(packageIds.split(","));
        List<Long> longPackageIds = StringUtil.convertStringList2LongList(packages);

        LOG.info("扫描套餐的变更时间间隔为===》【" + beginTime + "---" + endTime + "】" + "扫描的套餐id为===》" + packageIds);
        List<PackageBean> packageBeans = null;
        try {
            // call dubbo service
            packageBeans = packageCorpService.findPackageBeansByConditions(longPackageIds, beginTime, endTime);
            LOG.info("after call dubbo service ,return result is====>"+ com.alibaba.fastjson.JSON.toJSONString(packageBeans));
        } catch (Exception e) {
             LOG.error("call dubbo service fail , the exception is",e);
            return;
        }
        //放入nsq消费
        for (PackageBean packageBean : packageBeans) {
            String pacakgJson = gson.toJson(packageBean);
            try {
                ECNsqProducer.nsqProducer.produce(configProperties.topic, pacakgJson.getBytes());
                LOG.info("producer the message is=====>" + pacakgJson);
            } catch (Exception e) {
                LOG.warn("produce message fail ,the message is " + pacakgJson+"===>",e);
            }
        }
    }
}
