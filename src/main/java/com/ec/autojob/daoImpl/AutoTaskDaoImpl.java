/**
 * Project Name:autoJob
 * File Name:AutoTaskDaoImpl.java
 * Package Name:com.ec.autojob.daoImpl
 * Date:2015年6月29日下午9:22:06
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/
package com.ec.autojob.daoImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ec.autojob.bean.AutoTaskBean;
import com.ec.autojob.bean.StaticWXBean;
import com.ec.autojob.dao.AutoTaskDao;
import com.ec.autojob.db.DBContextHolder;

/**
 * ClassName:AutoTaskDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月29日 下午9:22:06 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */

@Component(value="autoTaskDaoImpl")
public class AutoTaskDaoImpl  implements AutoTaskDao{
	 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final Logger LOG = LogManager.getLogger("appLog");
    
	//private static final Logger LOG = LogManager.getLogger("appLog");
	@Override
	public List<AutoTaskBean> getAutoTasks() {
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE);
		String sql = "SELECT *  FROM d_ec_new_statistics.t_ec_autojob_autotask  t WHERE t.flag = '1' ";
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<AutoTaskBean> autasks = jdbcTemplate.query(sql,new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				AutoTaskBean auto = new AutoTaskBean();
				auto.setId(rs.getInt("id"));
				auto.setTaskcode(rs.getString("taskcode"));
				auto.setTaskName(rs.getString("taskName"));
				auto.setExectime(rs.getString("exectime"));
				auto.setLastStartDate(rs.getTimestamp("lastStartDate")); 
				auto.setLastEndDate(rs.getTimestamp("lastEndDate"));
				auto.setStarting(rs.getString("state"));
				auto.setTaskPath(rs.getString("taskPath"));
				auto.setTaskType(rs.getString("taskType"));
				auto.setFlag(rs.getString("flag"));
				auto.setFrequence(rs.getInt("frequence"));
				auto.setCreateUser(rs.getString("createUser"));
				auto.setCreateDate(rs.getString("createDate"));
				auto.setUpdateUser(rs.getString("updateUser"));
				auto.setUpdateDate(rs.getString("updateDate"));
				return auto;
			}
		});
				
		return autasks;
		
	}

	 /**
	  * 目前业务需要只要求更新 执行状态，
	  * TODO 简单描述该方法的实现功能（可选）.
	  * @see com.ec.autojob.dao.AutoTaskDao#update(com.ec.autojob.bean.AutoTaskBean)
	  */
	@Override
	public void update(final AutoTaskBean task) {
		
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE);
		if(task == null)
			return;
		final String sql = "UPDATE d_ec_new_statistics.t_ec_autojob_autotask t SET t.state = ?,t.lastStartDate = ?,t.lastEndDate=? Where t.id = ? ";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				 ps.setString(1, task.getStarting());
				java.util.Date sDate = task.getLastStartDate();
				java.util.Date eDate = task.getLastEndDate();
				
				if(sDate != null){
				 Timestamp ts = new Timestamp(sDate.getTime());
				 ps.setTimestamp(2, ts);
				}else{
					ps.setTimestamp(2, null);
				}
				if(eDate != null){
				 Timestamp endDate = new Timestamp(eDate.getTime());
				 ps.setTimestamp(3, endDate);
				}else{
					 ps.setTimestamp(3, null);
				}
				ps.setInt(4, task.getId());
				
				if(LOG.isDebugEnabled()){
					 LOG.debug("更新自动任务===》sql"+sql+"设置的参数分别为===》"+task.getStarting()+"=="+sDate+"==="+eDate+"==="+task.getId());
				}
			}
		});
		
	}

	@Override
	public void insert(StaticWXBean bean2) {
		int crop_id = bean2.getCorpId();
		int ec2wxmsgCount = bean2.getEc2wxmsgCount();
		int wx2ecmsgCount= bean2.getWx2ecmsgCount();
		long count = bean2.getTotalMsg();
		String time = bean2.getUsedTime();
		String sql = "insert into d_ec_new_statistics.t_msg_statistics (f_corp_id,f_ec_wx_msg_count,f_wx_ec_msg_count,f_used_time,f_msg_count) "
				+ "values("+crop_id+","+ec2wxmsgCount+","+wx2ecmsgCount+",'"+time+"',"+count+")";
		//LOG.debug("执行统计从微信发到ec的sql为===》"+sql);
		DBContextHolder.setDBType(DBContextHolder.DATA_SOURCE_2);
		//jdbcTemplate.b
		jdbcTemplate.update(sql);
		
	}
}

