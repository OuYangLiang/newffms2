package com.personal.oyl.newffms.consumption.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.consumption.domain.AccountConsumptionVo;

public interface AccountConsumptionMapper {
    List<AccountConsumptionVo> select(AccountConsumptionVo param);

    int insert(AccountConsumptionVo param);

    int delete(AccountConsumptionVo param);
}
