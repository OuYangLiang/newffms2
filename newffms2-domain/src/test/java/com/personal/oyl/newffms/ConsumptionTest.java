package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
import com.personal.oyl.newffms.account.store.mapper.AccountMapper;
import com.personal.oyl.newffms.consumption.domain.AccountConsumptionVo;
import com.personal.oyl.newffms.consumption.domain.Consumption;
import com.personal.oyl.newffms.consumption.domain.ConsumptionItemVo;
import com.personal.oyl.newffms.consumption.domain.ConsumptionRepos;
import com.personal.oyl.newffms.consumption.domain.ConsumptionType;
import com.personal.oyl.newffms.consumption.store.mapper.AccountConsumptionMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionItemMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionMapper;

import junit.framework.TestCase;

public class ConsumptionTest extends TestCase {
	
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
		ConsumptionItemMapper mapper2 = ctx.getBean(ConsumptionItemMapper.class);
		AccountAuditMapper mapper3 = ctx.getBean(AccountAuditMapper.class);
		ConsumptionMapper mapper4 = ctx.getBean(ConsumptionMapper.class);
		AccountConsumptionMapper mapper5 = ctx.getBean(AccountConsumptionMapper.class);
		
		mapper.delete(null);
		mapper2.delete(null);
		mapper3.delete(null);
		mapper4.delete(null);
		mapper5.delete(null);
	}

	public void testCreation() {
		
		AccountRepos acntRepos = ctx.getBean(AccountRepos.class);
		ConsumptionRepos consumptionRepos = ctx.getBean(ConsumptionRepos.class);
		
		// 创建账户
		Account acnt1 = new Account();
		acnt1.setAcntDesc("招商银行");
		acnt1.setAcntType(AccountType.Bankcard);
		acnt1.setBalance(BigDecimal.valueOf(150));
		acnt1.setOwnerOid(BigDecimal.ONE);
		acntRepos.add(acnt1, "欧阳亮");
		
		Account acnt2 = new Account();
		acnt2.setAcntDesc("招商银行");
		acnt2.setAcntType(AccountType.Bankcard);
		acnt2.setBalance(BigDecimal.valueOf(150));
		acnt2.setOwnerOid(BigDecimal.valueOf(2));
		acntRepos.add(acnt2, "喻敏");
		
		// 创建消费
		Consumption bean = new Consumption();
		bean.setCpnType(ConsumptionType.Online);
		bean.setAmount(BigDecimal.valueOf(115.53));
		bean.setCpnTime(new Date());
		bean.setBatchNum(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
		bean.setConfirmed(false);
		
		ConsumptionItemVo item = new ConsumptionItemVo();
		item.setItemDesc("desc1");
		item.setCategoryOid(BigDecimal.ONE);
		item.setAmount(BigDecimal.valueOf(100.50));
		item.setOwnerOid(BigDecimal.ONE);
		bean.addItem(item);
		
		item = new ConsumptionItemVo();
		item.setItemDesc("desc2");
		item.setCategoryOid(BigDecimal.valueOf(2));
		item.setAmount(BigDecimal.valueOf(15.03));
		item.setOwnerOid(BigDecimal.valueOf(3));
		bean.addItem(item);
		
		AccountConsumptionVo payment = new AccountConsumptionVo();
		payment.setAcntOid(acnt1.getKey().getAcntOid());
		payment.setAmount(BigDecimal.valueOf(50));
		bean.addPayment(payment);
		
		payment = new AccountConsumptionVo();
		payment.setAcntOid(acnt2.getKey().getAcntOid());
		payment.setAmount(BigDecimal.valueOf(65.53));
		bean.addPayment(payment);
		
		consumptionRepos.add(bean, "欧阳亮");
		
		bean = consumptionRepos.consumptionOfId(bean.getKey());
		bean.confirm("喻敏");
		
		bean.unconfirm("XXX");
	}
	
}
