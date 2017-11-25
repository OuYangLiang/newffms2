package com.personal.oyl.newffms.account.domain;

import java.util.List;

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
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingKeyEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingKey;
import com.personal.oyl.newffms.user.domain.UserKey;

public interface AccountRepos {

    /**
     * 根据账户标识查询账户
     * 
     * @param key 账户标识
     * @return 账户实体
     * @throws AccountKeyEmptyException
     */
    Account accountOfId(AccountKey key) throws AccountKeyEmptyException;
    
    /**
     * 根据收入标识查询账户
     * 
     * @param key 收入标识
     * @return 账户实体
     * @throws IncomingKeyEmptyException
     */
    Account accountOfIncoming(IncomingKey key) throws IncomingKeyEmptyException;
    
    /**
     * 根据用户标识查询账户集合
     * 
     * @param key 用户标识
     * @return 账户实体集合
     * @throws AccountOwnerEmptyException
     */
    List<Account> accountsOfUser(UserKey key) throws AccountOwnerEmptyException;
    
    /**
     * 查询所有账户
     * 
     * @return 账户实体集合
     */
    List<Account> queryAll();
	
    /**
     * 创建新的账户
     * 
     * @param bean 账户实体
     * @param operator 操作人
     * @throws AccountDescEmptyException
     * @throws AccountTypeEmptyException
     * @throws NoOperatorException
     * @throws AccountBalanceEmptyException
     * @throws AccountBalanceInvalidException
     * @throws AccountOwnerEmptyException
     * @throws AccountQuotaEmptyException
     * @throws AccountQuotaInvalidException
     * @throws AccountDebtEmptyException
     * @throws AccountDebtInvalidException
     * @throws AccountDebtPlusBalanceNeqQuotaException
     */
    void add(Account bean, String operator)
            throws AccountDescEmptyException, AccountTypeEmptyException, NoOperatorException,
            AccountBalanceEmptyException, AccountBalanceInvalidException, AccountOwnerEmptyException,
            AccountQuotaEmptyException, AccountQuotaInvalidException, AccountDebtEmptyException,
            AccountDebtInvalidException, AccountDebtPlusBalanceNeqQuotaException, AccountDescTooLongException;

    /**
     * 根据流水号查询账户明细
     * 
     * @param batchNum 流水号
     * @return 账户明细
     */
    List<AccountAuditVo> auditsOfBatchNum(String batchNum)
            throws AccountBatchNumEmptyException, AccountBatchNumInvalidException;
    
    /**
     * 根据账户查询流水
     * 
     * @param key 账户标识
     * @param page 页数
     * @param sizePerPage 每页记录数
     * @return 账户流水分页信息
     * @throws AccountKeyEmptyException
     */
    Tuple<Integer, List<AccountAuditVo>> auditsOfAccount(AccountKey key, int page, int sizePerPage) throws AccountKeyEmptyException;

    /**
     * 删除账户
     * 
     * @param key 待删除账户标识
     * @throws AccountKeyEmptyException
     * @throws AccountNotExistException
     */
    void remove(AccountKey key) throws AccountKeyEmptyException, AccountNotExistException;
}
