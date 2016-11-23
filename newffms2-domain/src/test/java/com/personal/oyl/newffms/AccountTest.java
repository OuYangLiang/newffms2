package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
import com.personal.oyl.newffms.account.store.mapper.AccountMapper;

import junit.framework.TestCase;

public class AccountTest extends TestCase {
	
	private static ApplicationContext ctx = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		AccountMapper mapper = ctx.getBean(AccountMapper.class);
		AccountAuditMapper mapper2 = ctx.getBean(AccountAuditMapper.class);
		mapper.delete(null);
		mapper2.delete(null);
	}

	public void testCreation() {
		AccountRepos repos = ctx.getBean(AccountRepos.class);
		
		Account bean = new Account();
		bean.setAcntDesc("招商银行");
		bean.setAcntType(AccountType.Bankcard);
		bean.setBalance(BigDecimal.valueOf(100));
		bean.setOwnerOid(BigDecimal.ONE);
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getAcntOid());
		
		Account bean2 = repos.accountOfId(bean.getKey());
		
		assertNotNull(bean2);
		assertEquals(bean.getAcntDesc(), bean2.getAcntDesc());
		assertEquals(bean.getAcntType(), bean2.getAcntType());
		assertTrue(bean.getBalance().compareTo(bean2.getBalance()) == 0);
		assertEquals(bean.getOwnerOid(), bean2.getOwnerOid());
		assertEquals(Integer.valueOf(1), bean.getSeqNo());
		assertNotNull(bean2.getCreateTime());
		assertNotNull(bean2.getCreateBy());
		
		AccountMapper mapper = ctx.getBean(AccountMapper.class);
		mapper.delete(null);
	}
	
	public void testSubtract() {
		AccountRepos repos = ctx.getBean(AccountRepos.class);
		
		Account bean = new Account();
		bean.setAcntDesc("招商银行");
		bean.setAcntType(AccountType.Bankcard);
		bean.setBalance(BigDecimal.valueOf(100));
		bean.setOwnerOid(BigDecimal.ONE);
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getAcntOid());
		
		bean.subtract(BigDecimal.valueOf(55), "扣钱了", new Date(), null, "喻敏");
		
		Account bean2 = repos.accountOfId(bean.getKey());
		
		assertNotNull(bean2);
		assertEquals(bean.getAcntDesc(), bean2.getAcntDesc());
		assertEquals(bean.getAcntType(), bean2.getAcntType());
		assertTrue(bean.getBalance().compareTo(bean2.getBalance()) == 0);
		assertTrue(BigDecimal.valueOf(45).compareTo(bean2.getBalance()) == 0);
		assertEquals(bean.getOwnerOid(), bean2.getOwnerOid());
		assertEquals(Integer.valueOf(2), bean.getSeqNo());
		assertNotNull(bean2.getCreateTime());
		assertNotNull(bean2.getCreateBy());
		
		AccountMapper mapper = ctx.getBean(AccountMapper.class);
		AccountAuditMapper mapper2 = ctx.getBean(AccountAuditMapper.class);
		mapper.delete(null);
		mapper2.delete(null);
	}
	
	public void testIncrease() {
		AccountRepos repos = ctx.getBean(AccountRepos.class);
		
		Account bean = new Account();
		bean.setAcntDesc("招商银行");
		bean.setAcntType(AccountType.Bankcard);
		bean.setBalance(BigDecimal.valueOf(100));
		bean.setOwnerOid(BigDecimal.ONE);
		
		repos.add(bean, "欧阳亮");
		
		assertNotNull(bean.getKey());
		assertNotNull(bean.getKey().getAcntOid());
		
		bean.increase(BigDecimal.valueOf(55), "加钱了", new Date(), null, "喻敏");
		
		Account bean2 = repos.accountOfId(bean.getKey());
		
		assertNotNull(bean2);
		assertEquals(bean.getAcntDesc(), bean2.getAcntDesc());
		assertEquals(bean.getAcntType(), bean2.getAcntType());
		assertTrue(bean.getBalance().compareTo(bean2.getBalance()) == 0);
		assertTrue(BigDecimal.valueOf(155).compareTo(bean2.getBalance()) == 0);
		assertEquals(bean.getOwnerOid(), bean2.getOwnerOid());
		assertEquals(Integer.valueOf(2), bean.getSeqNo());
		assertNotNull(bean2.getCreateTime());
		assertNotNull(bean2.getCreateBy());
		
		AccountMapper mapper = ctx.getBean(AccountMapper.class);
		AccountAuditMapper mapper2 = ctx.getBean(AccountAuditMapper.class);
		mapper.delete(null);
		mapper2.delete(null);
	}
	
}
