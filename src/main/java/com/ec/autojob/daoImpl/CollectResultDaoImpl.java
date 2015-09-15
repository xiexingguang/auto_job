/**
 * Project Name:autoJob
 * File Name:CollectResultDaoImpl.java
 * Package Name:com.ec.autojob.daoImpl
 * Date:2015年6月29日上午11:04:42
 * Copyright (c) 2015, 深圳市六度人和 All Rights Reserved.
 *
*/

package com.ec.autojob.daoImpl;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;

/**
 * ClassName:CollectResultDaoImpl <br/>
 * Function: <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年6月29日 上午11:04:42 <br/>
 * @author   xxg
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component(value="collectionResultDaoImpl")
public class CollectResultDaoImpl  extends BaseRepository{
  
	
	
	@Override
	public MongoCollection<Document> getCollection() {
		return null;
	}

}

