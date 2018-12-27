package com.personal.oyl.newffms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountException.AccountAmountInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInsufficiencyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDebtEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDebtInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDebtPlusBalanceNeqQuotaException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescTooLongException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOperationDescException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOwnerEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountQuotaEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountQuotaInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountTransferToSelfException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountTypeEmptyException;
import com.personal.oyl.newffms.account.domain.AccountRepos;
import com.personal.oyl.newffms.account.domain.AccountType;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
import com.personal.oyl.newffms.account.store.mapper.AccountMapper;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.user.domain.UserKey;

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

    public void testCreation()
            throws AccountKeyEmptyException, AccountDescEmptyException, AccountTypeEmptyException, NoOperatorException,
            AccountBalanceEmptyException, AccountBalanceInvalidException, AccountOwnerEmptyException,
            AccountQuotaEmptyException, AccountQuotaInvalidException, AccountDebtEmptyException,
            AccountDebtInvalidException, AccountDebtPlusBalanceNeqQuotaException, AccountDescTooLongException {
        AccountRepos repos = ctx.getBean(AccountRepos.class);

        Account bean = new Account();
        bean.setAcntDesc("招商银行");
        bean.setAcntType(AccountType.bankcard);
        bean.setBalance(BigDecimal.valueOf(100));
        bean.setOwner(new UserKey(BigDecimal.ONE));

        repos.add(bean, "欧阳亮");

        assertNotNull(bean.getKey());
        assertNotNull(bean.getKey().getAcntOid());

        Account bean2 = repos.accountOfId(bean.getKey());

        assertNotNull(bean2);
        assertEquals(bean.getAcntDesc(), bean2.getAcntDesc());
        assertEquals(bean.getAcntType(), bean2.getAcntType());
        assertTrue(bean.getBalance().compareTo(bean2.getBalance()) == 0);
        assertEquals(bean.getOwner().getUserOid(), bean2.getOwner().getUserOid());
        assertEquals(Integer.valueOf(1), bean.getSeqNo());
        assertNotNull(bean2.getCreateTime());
        assertNotNull(bean2.getCreateBy());

        AccountMapper mapper = ctx.getBean(AccountMapper.class);
        mapper.delete(null);
    }

    public void testSubtract()
            throws AccountAmountInvalidException, AccountBalanceInsufficiencyException, AccountOperationDescException,
            NoOperatorException, AccountKeyEmptyException, AccountDescEmptyException, AccountTypeEmptyException,
            AccountBalanceEmptyException, AccountBalanceInvalidException, AccountOwnerEmptyException,
            AccountQuotaEmptyException, AccountQuotaInvalidException, AccountDebtEmptyException,
            AccountDebtInvalidException, AccountDebtPlusBalanceNeqQuotaException, AccountDescTooLongException {
        AccountRepos repos = ctx.getBean(AccountRepos.class);

        Account bean = new Account();
        bean.setAcntDesc("招商银行");
        bean.setAcntType(AccountType.bankcard);
        bean.setBalance(BigDecimal.valueOf(100));
        bean.setOwner(new UserKey(BigDecimal.ONE));

        repos.add(bean, "欧阳亮");

        assertNotNull(bean.getKey());
        assertNotNull(bean.getKey().getAcntOid());

        bean.subtract(BigDecimal.valueOf(55), "扣钱了", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(),
                new Date(), "喻敏");

        Account bean2 = repos.accountOfId(bean.getKey());

        assertNotNull(bean2);
        assertEquals(bean.getAcntDesc(), bean2.getAcntDesc());
        assertEquals(bean.getAcntType(), bean2.getAcntType());
        assertTrue(bean.getBalance().compareTo(bean2.getBalance()) == 0);
        assertTrue(BigDecimal.valueOf(45).compareTo(bean2.getBalance()) == 0);
        assertEquals(bean.getOwner().getUserOid(), bean2.getOwner().getUserOid());
        assertEquals(Integer.valueOf(2), bean.getSeqNo());
        assertNotNull(bean2.getCreateTime());
        assertNotNull(bean2.getCreateBy());

        AccountMapper mapper = ctx.getBean(AccountMapper.class);
        AccountAuditMapper mapper2 = ctx.getBean(AccountAuditMapper.class);
        mapper.delete(null);
        mapper2.delete(null);
    }

    public void testIncrease() throws AccountAmountInvalidException, AccountOperationDescException, NoOperatorException,
            AccountKeyEmptyException, AccountDescEmptyException, AccountTypeEmptyException,
            AccountBalanceEmptyException, AccountBalanceInvalidException, AccountOwnerEmptyException,
            AccountQuotaEmptyException, AccountQuotaInvalidException, AccountDebtEmptyException,
            AccountDebtInvalidException, AccountDebtPlusBalanceNeqQuotaException, AccountDescTooLongException {
        AccountRepos repos = ctx.getBean(AccountRepos.class);

        Account bean = new Account();
        bean.setAcntDesc("招商银行");
        bean.setAcntType(AccountType.bankcard);
        bean.setBalance(BigDecimal.valueOf(100));
        bean.setOwner(new UserKey(BigDecimal.ONE));

        repos.add(bean, "欧阳亮");

        assertNotNull(bean.getKey());
        assertNotNull(bean.getKey().getAcntOid());

        bean.increase(BigDecimal.valueOf(55), "加钱了", UUID.randomUUID().toString().replaceAll("-", "").toUpperCase(),
                new Date(), "喻敏");

        Account bean2 = repos.accountOfId(bean.getKey());

        assertNotNull(bean2);
        assertEquals(bean.getAcntDesc(), bean2.getAcntDesc());
        assertEquals(bean.getAcntType(), bean2.getAcntType());
        assertTrue(bean.getBalance().compareTo(bean2.getBalance()) == 0);
        assertTrue(BigDecimal.valueOf(155).compareTo(bean2.getBalance()) == 0);
        assertEquals(bean.getOwner().getUserOid(), bean2.getOwner().getUserOid());
        assertEquals(Integer.valueOf(2), bean.getSeqNo());
        assertNotNull(bean2.getCreateTime());
        assertNotNull(bean2.getCreateBy());

        AccountMapper mapper = ctx.getBean(AccountMapper.class);
        AccountAuditMapper mapper2 = ctx.getBean(AccountAuditMapper.class);
        mapper.delete(null);
        mapper2.delete(null);
    }

    public void testTransfer() throws AccountAmountInvalidException, AccountBalanceInsufficiencyException,
            NoOperatorException, AccountKeyEmptyException, AccountDescEmptyException, AccountTypeEmptyException,
            AccountBalanceEmptyException, AccountBalanceInvalidException, AccountOwnerEmptyException,
            AccountQuotaEmptyException, AccountQuotaInvalidException, AccountDebtEmptyException,
            AccountDebtInvalidException, AccountDebtPlusBalanceNeqQuotaException, AccountDescTooLongException,
            AccountTransferToSelfException {
        AccountRepos repos = ctx.getBean(AccountRepos.class);

        Account bean = new Account();
        bean.setAcntDesc("招商银行1");
        bean.setAcntType(AccountType.bankcard);
        bean.setBalance(BigDecimal.valueOf(100));
        bean.setOwner(new UserKey(BigDecimal.ONE));
        repos.add(bean, "欧阳亮");

        Account bean2 = new Account();
        bean2.setAcntDesc("招商银行2");
        bean2.setAcntType(AccountType.bankcard);
        bean2.setBalance(BigDecimal.valueOf(100));
        bean2.setOwner(new UserKey(BigDecimal.ONE));
        repos.add(bean2, "喻敏");

        List<Account> accounts = repos.accountsOfUser(new UserKey(BigDecimal.ONE), false);
        assertNotNull(accounts);
        assertEquals(2, accounts.size());
        assertEquals("招商银行1", accounts.get(0).getAcntDesc());
        assertEquals("招商银行2", accounts.get(1).getAcntDesc());

        accounts = repos.accountsOfUser(new UserKey(BigDecimal.valueOf(2)), false);
        assertNull(accounts);

        bean.transfer(bean2, BigDecimal.valueOf(30), "欧");

        assertTrue(BigDecimal.valueOf(70).compareTo(bean.getBalance()) == 0);
        assertTrue(BigDecimal.valueOf(130).compareTo(bean2.getBalance()) == 0);

        bean = repos.accountOfId(bean.getKey());
        bean2 = repos.accountOfId(bean2.getKey());

        assertTrue(BigDecimal.valueOf(70).compareTo(bean.getBalance()) == 0);
        assertTrue(BigDecimal.valueOf(130).compareTo(bean2.getBalance()) == 0);

        AccountMapper mapper = ctx.getBean(AccountMapper.class);
        AccountAuditMapper mapper2 = ctx.getBean(AccountAuditMapper.class);
        mapper.delete(null);
        mapper2.delete(null);
    }

}
