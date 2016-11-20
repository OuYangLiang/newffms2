package com.personal.oyl.newffms.account.domain;

public interface AccountRepos {

	/**
	 * 根据账户标识查询账户
	 * 
	 * @param key 账户标识
	 * @return 账户实体
	 */
	Account accountOfId(AccountKey key);
	
	/**
	 * 创建新的账户
	 * 
	 * @param bean 账户标识
	 * @param operator 操作人
	 */
	void add(Account bean, String operator);
}
