package com.ec.autojob.nsq;

import com.trendrr.nsq.NSQProducer;

/**
 * Created by ecuser on 2015/12/18.
 */
public class TestNsq {

    public static void main(String[] args) {
        NSQProducer nsqProducer = new NSQProducer();
        nsqProducer.addAddress("10.0.200.51", 6767, 1);
        nsqProducer.start();

    }
}
