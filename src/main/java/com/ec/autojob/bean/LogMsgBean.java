package com.ec.autojob.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: LogMsgBean <br/>
 * Function: <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2015年6月29日 上午11:12:30 <br/>
 *
 * @author xxg
 * @version
 * @since JDK 1.7
 */
public class LogMsgBean implements Serializable {

    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	// public String _id ;
    public String sedtime;
    public String sender;
    public String recieve;
    public String msgtype;
    public String corpid;
    public String zdtype;
    public String module;
    /**
     * 1:ec发出。 2:微信发出
     */
    private String sendType;
    
    
    

    public LogMsgBean(String sedtime, String sender, String recieve,
			String msgtype, String corpid, String zdtype, String module,
			String sendType) {
		super();
		this.sedtime = sedtime;
		this.sender = sender;
		this.recieve = recieve;
		this.msgtype = msgtype;
		this.corpid = corpid;
		this.zdtype = zdtype;
		this.module = module;
		this.sendType = sendType;
	}

	public LogMsgBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * public String get_id() { return _id; } public void set_id(String _id) { this._id = _id; }
     */
    public String getSedtime() {
        return sedtime;
    }

    public void setSedtime(String sedtime) {
        this.sedtime = sedtime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecieve() {
        return recieve;
    }

    public void setRecieve(String recieve) {
        this.recieve = recieve;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getZdtype() {
        return zdtype;
    }

    public void setZdtype(String zdtype) {
        this.zdtype = zdtype;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

}
