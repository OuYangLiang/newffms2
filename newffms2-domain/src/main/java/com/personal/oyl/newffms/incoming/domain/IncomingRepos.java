package com.personal.oyl.newffms.incoming.domain;


public interface IncomingRepos {
	/**
	 * 根据收入标识查询收入
	 * 
	 * @param key 账户标识
	 * @return 账户实体
	 */
	Incoming incomingOfId(IncomingKey key);
	
	/**
	 * 创建新的收入
	 * 
	 * @param bean 收入实体
	 * @param operator 操作人
	 */
	void add(Incoming bean, String operator);
}
