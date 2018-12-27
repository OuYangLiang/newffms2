package com.personal.oyl.newffms.account.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.account.store.mapper.AccountAuditMapper;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepos repos;

    @Autowired
    private AccountAuditMapper auditMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void rollback(String batchNum, String operator) throws AccountKeyEmptyException,
            AccountBatchNumEmptyException, AccountBatchNumInvalidException, NoOperatorException {
        if (null == batchNum || batchNum.trim().isEmpty()) {
            throw new AccountBatchNumEmptyException();
        }

        if (32 != batchNum.length()) {
            throw new AccountBatchNumInvalidException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<AccountAuditVo> audits = repos.auditsOfBatchNum(batchNum);

        if (null == audits || audits.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (AccountAuditVo audit : audits) {
            if (AccountAuditType.rollback.equals(audit.getAdtType())) {
                continue;
            }
            Account acnt = repos.accountOfId(new AccountKey(audit.getAcntOid()));

            if (null == acnt) {
                throw new IllegalStateException();
            }

            acnt.rollback(audit.getChgAmt().negate(), operator);
            
            int n = auditMapper.insert(audit.genRollbackRecord(acnt.getBalance(), operator));
            if (1 != n) {
                throw new IllegalStateException();
            }

            n = auditMapper.invalidate(audit.getAdtOid());
            if (1 != n) {
                throw new IllegalStateException();
            }
        }
    }

}
