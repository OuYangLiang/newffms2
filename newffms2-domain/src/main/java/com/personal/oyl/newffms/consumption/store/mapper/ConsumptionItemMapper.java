package com.personal.oyl.newffms.consumption.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.consumption.domain.ConsumptionItemPaginationVo;
import com.personal.oyl.newffms.consumption.domain.ConsumptionItemVo;

public interface ConsumptionItemMapper {
    List<ConsumptionItemVo> select(ConsumptionItemVo param);

    int insert(ConsumptionItemVo param);

    int delete(ConsumptionItemVo param);

    int getCountOfSummary(Map<String, Object> param);

    List<ConsumptionItemPaginationVo> getListOfSummary(Map<String, Object> param);
}
