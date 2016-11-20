package com.personal.oyl.newffms.account.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.account.domain.AccountAuditVo;

public interface AccountAuditMapper {
	List<AccountAuditVo> select(AccountAuditVo param);
	
	int insert(AccountAuditVo param);
	
	int delete(AccountAuditVo param);
	
}
