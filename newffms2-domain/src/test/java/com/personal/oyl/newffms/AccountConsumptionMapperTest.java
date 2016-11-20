package com.personal.oyl.newffms;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.consumption.domain.AccountConsumptionVo;
import com.personal.oyl.newffms.consumption.store.mapper.AccountConsumptionMapper;

import junit.framework.TestCase;

public class AccountConsumptionMapperTest extends TestCase {
	
	private static ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		AccountConsumptionMapper mapper = ctx.getBean(AccountConsumptionMapper.class);
		mapper.delete(null);
	}

	public void testCreation() {
		AccountConsumptionMapper mapper = ctx.getBean(AccountConsumptionMapper.class);
		
		AccountConsumptionVo bean = new AccountConsumptionVo();
		bean.setAmount(BigDecimal.valueOf(1));
		bean.setAcntOid(BigDecimal.ONE);
		bean.setCpnOid(BigDecimal.valueOf(2));
		
		mapper.insert(bean);
		
	}
	
}
