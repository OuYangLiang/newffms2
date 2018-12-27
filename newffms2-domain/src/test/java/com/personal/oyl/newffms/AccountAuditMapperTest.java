package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.account.domain.AccountAuditVo;
import com.personal.oyl.newffms.account.domain.AccountAuditType;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;

import junit.framework.TestCase;

public class AccountAuditMapperTest extends TestCase {
	
	private static ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		AccountAuditMapper mapper = ctx.getBean(AccountAuditMapper.class);
		mapper.delete(null);
	}

	public void testCreation() {
		AccountAuditMapper mapper = ctx.getBean(AccountAuditMapper.class);
		
		AccountAuditVo bean = new AccountAuditVo();
		bean.setAdtDesc("desc");
		bean.setAdtTime(new Date());
		bean.setAdtType(AccountAuditType.add);
		bean.setBalanceAfter(BigDecimal.valueOf(100));
		bean.setChgAmt(BigDecimal.valueOf(100));
		bean.setAcntOid(BigDecimal.ONE);
		bean.setBatchNum(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		bean.setCreateBy("OYL");
		bean.setCreateTime(new Date());
		
		mapper.insert(bean);
		
	}
	
}
