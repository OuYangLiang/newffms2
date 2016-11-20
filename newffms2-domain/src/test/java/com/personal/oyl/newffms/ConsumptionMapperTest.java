package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.consumption.domain.Consumption;
import com.personal.oyl.newffms.consumption.domain.ConsumptionType;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionMapper;

import junit.framework.TestCase;

public class ConsumptionMapperTest extends TestCase {
	
	private static ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		ConsumptionMapper mapper = ctx.getBean(ConsumptionMapper.class);
		mapper.delete(null);
	}

	public void testCreation() {
		ConsumptionMapper mapper = ctx.getBean(ConsumptionMapper.class);
		
		Consumption bean = new Consumption();
		bean.setCpnType(ConsumptionType.Online);
		bean.setCpnTime(new Date());
		bean.setConfirmed(false);
		bean.setAmount(BigDecimal.valueOf(1));
		bean.setCreateTime(new Date());
		bean.setCreateBy("OYL");
		
		mapper.insert(bean);
		
	}
	
}
