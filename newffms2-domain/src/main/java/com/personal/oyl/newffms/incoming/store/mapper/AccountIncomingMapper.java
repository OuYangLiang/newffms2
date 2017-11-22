package com.personal.oyl.newffms.incoming.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.incoming.domain.AccountIncomingVo;

public interface AccountIncomingMapper {
    List<AccountIncomingVo> select(AccountIncomingVo param);

    int insert(AccountIncomingVo param);

    int delete(AccountIncomingVo param);
}
