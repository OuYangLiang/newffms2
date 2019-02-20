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
import com.personal.oyl.newffms.common.NewffmsDomainException;
import com.personal.oyl.newffms.incoming.domain.AccountIncomingVo;
import com.personal.oyl.newffms.incoming.domain.Incoming;
import com.personal.oyl.newffms.incoming.domain.IncomingRepos;
import com.personal.oyl.newffms.incoming.domain.IncomingType;
import com.personal.oyl.newffms.incoming.store.mapper.AccountIncomingMapper;
import com.personal.oyl.newffms.incoming.store.mapper.IncomingMapper;
import com.personal.oyl.newffms.user.domain.UserKey;

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
        AccountMapper mapper3 = ctx.getBean(AccountMapper.class);
        AccountAuditMapper mapper4 = ctx.getBean(AccountAuditMapper.class);

        mapper.delete(null);
        mapper2.delete(null);
        mapper3.delete(null);
        mapper4.delete(null);
    }

    public void testCreation() throws NewffmsDomainException {
        IncomingRepos repos = ctx.getBean(IncomingRepos.class);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Date now = new Date();

        Incoming bean = new Incoming();
        bean.setIncomingDesc("desc");
        bean.setAmount(BigDecimal.valueOf(100));
        bean.setBatchNum(uuid);
        bean.setIncomingType(IncomingType.salary);
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
        assertEquals(IncomingType.salary, bean.getIncomingType());
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

    public void testConfirm() throws NewffmsDomainException {

        AccountRepos acntRepos = ctx.getBean(AccountRepos.class);
        IncomingRepos repos = ctx.getBean(IncomingRepos.class);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Date now = new Date();

        // 创建账户
        Account acnt1 = new Account();
        acnt1.setAcntDesc("招商银行");
        acnt1.setAcntType(AccountType.bankcard);
        acnt1.setBalance(BigDecimal.valueOf(150));
        acnt1.setOwner(new UserKey(BigDecimal.ONE));
        acntRepos.add(acnt1, "欧阳亮");

        Incoming bean = new Incoming();
        bean.setIncomingDesc("desc");
        bean.setAmount(BigDecimal.valueOf(100));
        bean.setBatchNum(uuid);
        bean.setIncomingType(IncomingType.salary);
        bean.setOwnerOid(BigDecimal.ONE);
        bean.setConfirmed(false);
        bean.setIncomingDate(now);

        AccountIncomingVo acntRel = new AccountIncomingVo();
        acntRel.setAcntOid(acnt1.getKey().getAcntOid());
        bean.setAcntRel(acntRel);
        repos.add(bean, "欧阳亮");

        bean.confirm("喻敏");

        assertTrue(bean.getConfirmed());
        assertEquals("喻敏", bean.getUpdateBy());
        assertNotNull(bean.getUpdateTime());

        bean = repos.incomingOfId(bean.getKey());
        assertTrue(bean.getConfirmed());
        assertEquals("喻敏", bean.getUpdateBy());
        assertNotNull(bean.getUpdateTime());

        acnt1 = acntRepos.accountOfId(acnt1.getKey());
        assertTrue(acnt1.getBalance().compareTo(BigDecimal.valueOf(250)) == 0);

        bean.unconfirm("XXX");

        acnt1 = acntRepos.accountOfId(acnt1.getKey());
        assertTrue(acnt1.getBalance().compareTo(BigDecimal.valueOf(150)) == 0);

        assertFalse(bean.getConfirmed());
        assertEquals("XXX", bean.getUpdateBy());
        assertNotNull(bean.getUpdateTime());

        bean = repos.incomingOfId(bean.getKey());
        assertFalse(bean.getConfirmed());
        assertEquals("XXX", bean.getUpdateBy());
        assertNotNull(bean.getUpdateTime());
    }

    public void testUpdateInfo() throws NewffmsDomainException {

        IncomingRepos repos = ctx.getBean(IncomingRepos.class);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Date now = new Date();

        Incoming bean = new Incoming();
        bean.setIncomingDesc("desc");
        bean.setAmount(BigDecimal.valueOf(100));
        bean.setBatchNum(uuid);
        bean.setIncomingType(IncomingType.salary);
        bean.setOwnerOid(BigDecimal.ONE);
        bean.setConfirmed(false);
        bean.setIncomingDate(now);

        AccountIncomingVo acntRel = new AccountIncomingVo();
        acntRel.setAcntOid(BigDecimal.valueOf(10));
        bean.setAcntRel(acntRel);
        repos.add(bean, "欧阳亮");

        bean.setIncomingDesc("desc1");
        bean.setAmount(BigDecimal.valueOf(150));
        bean.setIncomingType(IncomingType.bonus);
        bean.setOwnerOid(BigDecimal.TEN);

        bean.updateAll("XXX");

        assertFalse(bean.getConfirmed());
        assertEquals("XXX", bean.getUpdateBy());
        assertNotNull(bean.getUpdateTime());

        bean = repos.incomingOfId(bean.getKey());

        assertNotNull(bean.getKey());
        assertNotNull(bean.getKey().getIncomingOid());
        assertEquals("desc1", bean.getIncomingDesc());
        assertTrue(BigDecimal.valueOf(150).compareTo(bean.getAmount()) == 0);
        assertEquals(uuid, bean.getBatchNum());
        assertEquals(IncomingType.bonus, bean.getIncomingType());
        assertNotNull(bean.getIncomingDate().getTime());
        assertFalse(bean.getConfirmed());
        assertEquals("欧阳亮", bean.getCreateBy());
        assertNotNull(bean.getCreateTime());
        assertEquals("XXX", bean.getUpdateBy());
        assertNotNull(bean.getUpdateTime());

        assertNotNull(bean.getAcntRel());
        assertNotNull(bean.getAcntRel().getIncomingOid());
        assertTrue(BigDecimal.TEN.compareTo(bean.getAcntRel().getAcntOid()) == 0);
        assertTrue(bean.getKey().getIncomingOid().compareTo(bean.getAcntRel().getIncomingOid()) == 0);
    }
}
