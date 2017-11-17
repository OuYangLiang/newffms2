package com.personal.oyl.newffms.account.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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

public class AccountReposImpl implements AccountRepos {
	
	@Autowired
	private AccountMapper mapper;
	
	@Autowired
	private AccountAuditMapper auditMapper;

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
            
            if (bean.getDebt().compareTo(bean.getQuota()) > 0) {
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

}
