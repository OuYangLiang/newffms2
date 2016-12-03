package com.personal.oyl.newffms.account.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;

public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepos repos;
	
	@Autowired
	private AccountAuditMapper auditMapper;

	@Override
	public void rollback(String batchNum, String operator) {
		if (null == batchNum || batchNum.trim().isEmpty() || 32 != batchNum.length()) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		List<AccountAuditVo> audits = repos.auditsOfBatchNum(batchNum);
		
		if (null == audits || audits.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if (2 < audits.size()) {
			throw new IllegalStateException();
		}
		
		for (AccountAuditVo audit : audits) {
			Account acnt = repos.accountOfId(new AccountKey(audit.getAcntOid()));
			
			if (null == acnt) {
				throw new IllegalStateException();
			}
			
			acnt.rollback(audit.getChgAmt().negate(), operator);
			
			AccountAuditVo param = new AccountAuditVo();
			param.setAdtOid(audit.getAdtOid());
			
			int n = auditMapper.delete(param);
			
			if (1 != n) {
				throw new IllegalStateException();
			}
		}
	}

}
