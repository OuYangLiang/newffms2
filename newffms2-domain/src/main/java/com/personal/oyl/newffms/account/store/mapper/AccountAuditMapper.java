package com.personal.oyl.newffms.account.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.account.domain.AccountAuditVo;
import com.personal.oyl.newffms.account.domain.AccountKey;

public interface AccountAuditMapper {
    List<AccountAuditVo> select(AccountAuditVo param);

    int insert(AccountAuditVo param);

    int delete(AccountAuditVo param);

    int getCountOfSummary(AccountKey key);

    List<AccountAuditVo> getListOfSummary(Map<String, Object> param);
}
