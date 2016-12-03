package com.personal.oyl.newffms.account.domain;

public interface AccountService {
	/**
	 * 根据流水号回滚账户操作
	 * 
	 * @param batchNum 流水号
	 * @param operator 操作人
	 */
	void rollback(String batchNum, String operator);
}
