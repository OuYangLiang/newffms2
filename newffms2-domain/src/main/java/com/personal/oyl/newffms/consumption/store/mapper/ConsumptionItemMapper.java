package com.personal.oyl.newffms.consumption.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.consumption.domain.ConsumptionItemVo;

public interface ConsumptionItemMapper {
	List<ConsumptionItemVo> select(ConsumptionItemVo param);
	
	int insert(ConsumptionItemVo param);
	
	int delete(ConsumptionItemVo param);
}
