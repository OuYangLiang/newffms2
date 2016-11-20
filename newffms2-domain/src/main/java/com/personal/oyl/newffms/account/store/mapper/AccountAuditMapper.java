package com.personal.oyl.newffms.account.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.account.domain.AccountAudit;

public interface AccountAuditMapper {
	List<AccountAudit> select(AccountAudit param);
	
	int insert(AccountAudit param);
	
	int delete(AccountAudit param);
	
}
