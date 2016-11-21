package com.personal.oyl.newffms.consumption.domain;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.consumption.store.mapper.AccountConsumptionMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionItemMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionMapper;

public class ConsumptionReposImpl implements ConsumptionRepos {
	
	@Autowired
	private ConsumptionMapper mapper;
	@Autowired
	private ConsumptionItemMapper itemMapper;
	@Autowired
	private AccountConsumptionMapper acntMapper;

	@Override
	public void add(Consumption bean, String operator) {
		if (null == bean) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		mapper.insert(bean);
		
		for (ConsumptionItemVo item : bean.getItems()) {
			itemMapper.insert(item);
		}
		
		for (AccountConsumptionVo acntItem : bean.getPayments()) {
			acntMapper.insert(acntItem);
		}
	}

	@Override
	public Consumption consumptionOfId(ConsumptionKey key) {
		return null;
	}

}
