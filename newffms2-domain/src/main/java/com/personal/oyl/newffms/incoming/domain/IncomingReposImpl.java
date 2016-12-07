package com.personal.oyl.newffms.incoming.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.incoming.store.mapper.AccountIncomingMapper;
import com.personal.oyl.newffms.incoming.store.mapper.IncomingMapper;

public class IncomingReposImpl implements IncomingRepos {
	
	@Autowired
	private IncomingMapper mapper;
	@Autowired
	private AccountIncomingMapper acntIncomingMapper;

	@Override
	public Incoming incomingOfId(IncomingKey key) {
		if (null == key || null == key.getIncomingOid()) {
			throw new IllegalArgumentException();
		}
		
		Incoming param = new Incoming();
		param.setKey(key);
		
		List<Incoming> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		Incoming bean = list.get(0);
		
		AccountIncomingVo subParam = new AccountIncomingVo();
		subParam.setIncomingOid(key.getIncomingOid());
		
		List<AccountIncomingVo> subList = acntIncomingMapper.select(subParam);
		
		if (subList == null || subList.size() != 1) {
			throw new IllegalStateException();
		}
		
		bean.setAcntRel(subList.get(0));
		
		return bean;
	}

	@Override
	public void add(Incoming bean, String operator) {
		if (null == bean) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		bean.setCreateBy(operator);
		bean.setCreateTime(new Date());
		
		int n = mapper.insert(bean);
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		bean.getAcntRel().setIncomingOid(bean.getKey().getIncomingOid());
		
		n = acntIncomingMapper.insert(bean.getAcntRel());
		
		if (1 != n) {
			throw new IllegalStateException();
		}
		
		bean.setSeqNo(1);
	}

}
