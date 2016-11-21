package com.personal.oyl.newffms.consumption.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.consumption.domain.Consumption;
import com.personal.oyl.newffms.consumption.domain.ConsumptionKey;

public interface ConsumptionMapper {
	List<Consumption> select(Consumption param);
	
	int insert(Consumption param);
	
	int delete(ConsumptionKey key);
	
	int confirm(Map<String, Object> param);
}
