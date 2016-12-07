package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.incoming.domain.AccountIncomingVo;
import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingRepos;
import com.personal.oyl.newffms.incoming.domain.IncomingType;
import com.personal.oyl.newffms.incoming.store.mapper.AccountIncomingMapper;
import com.personal.oyl.newffms.incoming.store.mapper.IncomingMapper;

import junit.framework.TestCase;

public class IncomingTest extends TestCase {
	
	private static ApplicationContext ctx = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		IncomingMapper mapper = ctx.getBean(IncomingMapper.class);
		AccountIncomingMapper mapper2 = ctx.getBean(AccountIncomingMapper.class);
		
		mapper.delete(null);
		mapper2.delete(null);
	}
	
	public void testCreation() {
		IncomingRepos repos = ctx.getBean(IncomingRepos.class);
		
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		Date now = new Date();
		
		Incoming bean = new Incoming();
		bean.setIncomingDesc("desc");
		bean.setAmount(BigDecimal.valueOf(100));
		bean.setBatchNum(uuid);
		bean.setIncomingType(IncomingType.Salary);
		bean.setOwnerOid(BigDecimal.ONE);
		bean.setConfirmed(false);
		bean.setIncomingDate(now);
		
		AccountIncomingVo acntRel = new AccountIncomingVo();
		acntRel.setAcntOid(BigDecimal.TEN);
		bean.setAcntRel(acntRel);
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getIncomingOid());
		assertNotNull(bean.getAcntRel().getIncomingOid());
		assertTrue(bean.getKey().getIncomingOid().compareTo(bean.getAcntRel().getIncomingOid()) == 0);
		assertEquals("欧阳亮", bean.getCreateBy());
		assertNotNull(bean.getCreateBy());
		
		bean = repos.incomingOfId(bean.getKey());
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getIncomingOid());
		assertEquals("desc", bean.getIncomingDesc());
		assertTrue(BigDecimal.valueOf(100).compareTo(bean.getAmount()) == 0);
		assertEquals(uuid, bean.getBatchNum());
		assertEquals(IncomingType.Salary, bean.getIncomingType());
		assertNotNull(bean.getIncomingDate().getTime());
		assertFalse(bean.getConfirmed());
		assertEquals("欧阳亮", bean.getCreateBy());
		assertNotNull(bean.getCreateTime());
		
		assertNotNull(bean.getAcntRel());
		assertNotNull(bean.getAcntRel().getIncomingOid());
		assertTrue(BigDecimal.TEN.compareTo(bean.getAcntRel().getAcntOid()) == 0);
		assertTrue(bean.getKey().getIncomingOid().compareTo(bean.getAcntRel().getIncomingOid()) == 0);
		assertTrue(bean.getKey().getIncomingOid().compareTo(bean.getAcntRel().getIncomingOid()) == 0);
	}
}
