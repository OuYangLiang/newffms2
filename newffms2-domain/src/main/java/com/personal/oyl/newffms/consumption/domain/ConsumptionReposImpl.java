package com.personal.oyl.newffms.consumption.domain;

import java.util.Date;
import java.util.List;

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
	private AccountConsumptionMapper paymentMapper;

	@Override
	public void add(Consumption bean, String operator) {
		if (null == bean) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		bean.setCreateBy(operator);
		bean.setCreateTime(new Date());
		mapper.insert(bean);
		
		for (ConsumptionItemVo item : bean.getItems()) {
			item.setCpnOid(bean.getKey().getCpnOid());
			itemMapper.insert(item);
		}
		
		for (AccountConsumptionVo payment : bean.getPayments()) {
			payment.setCpnOid(bean.getKey().getCpnOid());
			paymentMapper.insert(payment);
		}
		
		bean.setSeqNo(1);
	}

	@Override
	public Consumption consumptionOfId(ConsumptionKey key) {
		if (null == key || null == key.getCpnOid()) {
			throw new IllegalArgumentException();
		}
		
		Consumption param = new Consumption();
		param.setKey(key);
		
		List<Consumption> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		Consumption bean = list.get(0);
		
		ConsumptionItemVo itemParam = new ConsumptionItemVo();
		itemParam.setCpnOid(key.getCpnOid());
		
		List<ConsumptionItemVo> items = itemMapper.select(itemParam);
		bean.setItems(items);
		
		AccountConsumptionVo paymentParam = new AccountConsumptionVo();
		paymentParam.setCpnOid(key.getCpnOid());
		
		List<AccountConsumptionVo> payments = paymentMapper.select(paymentParam);
		bean.setPayments(payments);
		
		return bean;
	}

}
