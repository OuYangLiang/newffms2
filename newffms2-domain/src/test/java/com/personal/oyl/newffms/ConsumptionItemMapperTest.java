package com.personal.oyl.newffms;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.consumption.domain.ConsumptionItemVo;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionItemMapper;

import junit.framework.TestCase;

public class ConsumptionItemMapperTest extends TestCase {
	
	private static ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		ConsumptionItemMapper mapper = ctx.getBean(ConsumptionItemMapper.class);
		mapper.delete(null);
	}

	public void testCreation() {
		ConsumptionItemMapper mapper = ctx.getBean(ConsumptionItemMapper.class);
		
		ConsumptionItemVo bean = new ConsumptionItemVo();
		bean.setItemDesc("desc");
		bean.setCategoryOid(BigDecimal.ONE);
		bean.setCpnOid(BigDecimal.ONE);
		bean.setAmount(BigDecimal.valueOf(100));
		bean.setOwnerOid(BigDecimal.ONE);
		
		mapper.insert(bean);
	}
	
}
