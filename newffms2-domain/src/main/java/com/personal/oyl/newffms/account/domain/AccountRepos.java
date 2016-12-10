package com.personal.oyl.newffms.account.domain;

import java.util.List;

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
	 * @param bean 账户实体
	 * @param operator 操作人
	 */
	void add(Account bean, String operator);
	
	/**
	 * 根据流水号查询账户明细
	 * 
	 * @param batchNum 流水号
	 * @return 账户明细
	 */
	List<AccountAuditVo> auditsOfBatchNum(String batchNum);
	
	/**
	 * 删除账户
	 * 
	 * @param key 待删除账户标识
	 */
	void remove(AccountKey key);
}
