package com.personal.oyl.newffms.account.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDebtEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDebtInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDebtPlusBalanceNeqQuotaException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescTooLongException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountNotExistException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOwnerEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountQuotaEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountQuotaInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountTypeEmptyException;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
import com.personal.oyl.newffms.account.store.mapper.AccountMapper;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.incoming.domain.AccountIncomingVo;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingKeyEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingKey;
import com.personal.oyl.newffms.incoming.store.mapper.AccountIncomingMapper;
import com.personal.oyl.newffms.user.domain.UserKey;

public class AccountReposImpl implements AccountRepos {

    @Autowired
    private AccountMapper mapper;

    @Autowired
    private AccountAuditMapper auditMapper;

    @Autowired
    private AccountIncomingMapper acntIncomingMapper;

    @Override
    public Account accountOfId(AccountKey key) throws AccountKeyEmptyException {
        if (null == key || null == key.getAcntOid()) {
            throw new AccountKeyEmptyException();
        }

        Account param = new Account();
        param.setKey(key);

        List<Account> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void add(Account bean, String operator)
            throws AccountDescEmptyException, AccountTypeEmptyException, NoOperatorException,
            AccountBalanceEmptyException, AccountBalanceInvalidException, AccountOwnerEmptyException,
            AccountQuotaEmptyException, AccountQuotaInvalidException, AccountDebtEmptyException,
            AccountDebtInvalidException, AccountDebtPlusBalanceNeqQuotaException, AccountDescTooLongException {

        if (null == bean || null == bean.getAcntDesc() || bean.getAcntDesc().trim().isEmpty()) {
            throw new AccountDescEmptyException();
        }

        if (bean.getAcntDesc().trim().length() > 30) {
            throw new AccountDescTooLongException();
        }

        if (null == bean.getAcntType()) {
            throw new AccountTypeEmptyException();
        }

        if (null == bean.getBalance()) {
            throw new AccountBalanceEmptyException();
        }

        if (bean.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountBalanceInvalidException();
        }

        if (null == bean.getOwner()) {
            throw new AccountOwnerEmptyException();
        }
        
        if (null == bean.getDisabled()) {
            bean.setDisabled(false);
        }

        if (AccountType.Creditcard.equals(bean.getAcntType())) {
            if (null == bean.getQuota()) {
                throw new AccountQuotaEmptyException();
            }

            if (bean.getQuota().compareTo(BigDecimal.ZERO) < 0) {
                throw new AccountQuotaInvalidException();
            }

            if (null == bean.getDebt()) {
                throw new AccountDebtEmptyException();
            }

            if (bean.getDebt().compareTo(BigDecimal.ZERO) < 0) {
                throw new AccountDebtInvalidException();
            }

            if ((bean.getBalance().add(bean.getDebt())).subtract(bean.getQuota()).compareTo(BigDecimal.ZERO) != 0) {
                throw new AccountDebtPlusBalanceNeqQuotaException();
            }
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        bean.setCreateBy(operator);
        bean.setCreateTime(new Date());

        mapper.insert(bean);
        bean.setSeqNo(1);
    }

    @Override
    public List<AccountAuditVo> auditsOfBatchNum(String batchNum)
            throws AccountBatchNumEmptyException, AccountBatchNumInvalidException {
        if (null == batchNum || batchNum.trim().isEmpty()) {
            throw new AccountException.AccountBatchNumEmptyException();
        }

        if (32 != batchNum.length()) {
            throw new AccountException.AccountBatchNumInvalidException();
        }

        AccountAuditVo param = new AccountAuditVo();
        param.setBatchNum(batchNum);

        List<AccountAuditVo> list = auditMapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void remove(AccountKey key) throws AccountKeyEmptyException, AccountNotExistException {
        if (null == key || null == key.getAcntOid()) {
            throw new AccountKeyEmptyException();
        }

        Account obj = this.accountOfId(key);

        // TODO check if this account could be removed.

        if (null == obj) {
            throw new AccountNotExistException();
        }

        int n = mapper.delete(key);

        if (1 != n) {
            throw new IllegalStateException();
        }

        AccountAuditVo detailParam = new AccountAuditVo();
        detailParam.setAcntOid(key.getAcntOid());
        auditMapper.delete(detailParam);

    }

    @Override
    public List<Account> accountsOfUser(UserKey key, boolean includesDisabled) throws AccountOwnerEmptyException {
        if (null == key || null == key.getUserOid()) {
            throw new AccountOwnerEmptyException();
        }

        Account param = new Account();
        param.setOwner(key);
        if (!includesDisabled) {
            param.setDisabled(false);
        }

        List<Account> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }

    @Override
    public Tuple<Integer, List<AccountAuditVo>> auditsOfAccount(AccountKey key, int page, int sizePerPage)
            throws AccountKeyEmptyException {
        if (null == key || null == key.getAcntOid()) {
            throw new AccountKeyEmptyException();
        }

        List<AccountAuditVo> list = null;
        int count = auditMapper.getCountOfSummary(key);
        if (count > 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("acntOid", key.getAcntOid());
            param.put("start", (page - 1) * sizePerPage);
            param.put("sizePerPage", sizePerPage);
            list = auditMapper.getListOfSummary(param);
        }

        return new Tuple<Integer, List<AccountAuditVo>>(count, list);
    }

    @Override
    public Account accountOfIncoming(IncomingKey key) throws IncomingKeyEmptyException {
        if (null == key || null == key.getIncomingOid()) {
            throw new IncomingKeyEmptyException();
        }

        AccountIncomingVo voParam = new AccountIncomingVo();
        voParam.setIncomingOid(key.getIncomingOid());

        List<AccountIncomingVo> voList = acntIncomingMapper.select(voParam);

        if (null == voList || voList.isEmpty()) {
            return null;
        }

        Account param = new Account();
        param.setKey(new AccountKey(voList.get(0).getAcntOid()));

        List<Account> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

}
