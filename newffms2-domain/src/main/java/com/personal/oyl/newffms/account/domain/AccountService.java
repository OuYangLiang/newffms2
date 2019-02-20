package com.personal.oyl.newffms.account.domain;

import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public interface AccountService {
    /**
     * 根据流水号回滚账户操作
     *
     * @param batchNum 流水号
     * @param operator 操作人
     * @throws AccountKeyEmptyException
     */
    void rollback(String batchNum, String operator) throws AccountKeyEmptyException, AccountBatchNumEmptyException,
            AccountBatchNumInvalidException, NoOperatorException;
}
