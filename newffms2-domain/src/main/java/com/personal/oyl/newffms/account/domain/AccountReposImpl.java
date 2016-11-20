package com.personal.oyl.newffms.account.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.account.store.mapper.AccountMapper;

public class AccountReposImpl implements AccountRepos {
	
	@Autowired
	private AccountMapper mapper;

	@Override
	public Account accountOfId(AccountKey key) {
		if (null == key || null == key.getAcntOid()) {
			throw new IllegalArgumentException();
		}
		
		Account param = new Account();
		param.setKey(key);
		
		List<Account> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		return list.get(0);
	}

	@Override
	public void add(Account bean, String operator) {
		if (null == bean) {
			throw new IllegalArgumentException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		bean.setCreateBy(operator);
		bean.setCreateTime(new Date());
		
		mapper.insert(bean);
		bean.setSeqNo(1);
	}

}
