package com.ec.autojob.nsq;

import com.ec.autojob.properties.NsqProperties;
import com.trendrr.nsq.NSQProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ecuser on 2015/12/17.
 */
@Component
public class ECNsqProducer {

    private static final Logger LOG = LogManager.getLogger(ECNsqProducer.class);
    @Autowired
    private NsqProperties nsqProperties;

    public static NSQProducer nsqProducer;

    public ECNsqProducer() {
        System.out.println("ecproduce..");
    }
    @PostConstruct
    public void init(){
        LOG.info("nsq producer init begin");
        String nsqdHost = nsqProperties.nsqdHost;
        int port = nsqProperties.port;
        try {
            if (nsqProducer == null) {
                nsqProducer = new NSQProducer().addAddress(nsqdHost, port, 1);
                nsqProducer.start();
            }
        } catch (Exception e) {
            LOG.error("nsq init producer fail ,may be the exception is",e);
        }
        LOG.info("init nsq producer success, the Nsqdhost is ["+nsqdHost +":"+port+"]");
        }

}
