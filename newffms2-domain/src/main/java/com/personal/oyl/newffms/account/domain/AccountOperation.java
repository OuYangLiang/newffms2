package com.personal.oyl.newffms.account.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.personal.oyl.newffms.account.domain.AccountException.AccountAmountInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInsufficiencyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountDescTooLongException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountOperationDescException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountTransferToSelfException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;

public interface AccountOperation {
    /**
     * 变更账户描述
     * 
     * @param newDesc 新描述
     * @param operator 操作人
     * @throws AccountDescEmptyException
     * @throws NoOperatorException
     * @throws AccountDescTooLongException
     */
    void changeDesc(String newDesc, String operator)
            throws AccountDescEmptyException, NoOperatorException, AccountDescTooLongException;
    
    /**
     * 消费扣减
     * 
     * @param amount 扣减金额，正数表示
     * @param desc 描述
     * @param batchNum 流水号
     * @param eventTime 事件发生时间
     * @param operator 操作人
     * @throws AccountAmountInvalidException 
     * @throws AccountBalanceInsufficiencyException 
     * @throws AccountOperationDescException 
     * @throws NoOperatorException 
     */
    public void subtract(BigDecimal amount, String desc, String batchNum, Date eventTime, String operator)
            throws AccountAmountInvalidException, AccountBalanceInsufficiencyException, AccountOperationDescException,
            NoOperatorException;
    
    /**
     * 账户增加
     * 
     * @param amount 增加金额
     * @param desc 描述
     * @param batchNum 流水号
     * @param eventTime 事件发生时间
     * @param operator 操作人
     * @throws AccountAmountInvalidException 
     * @throws AccountOperationDescException 
     * @throws NoOperatorException 
     */
    public void increase(BigDecimal amount, String desc, String batchNum, Date eventTime, String operator)
            throws AccountAmountInvalidException, AccountOperationDescException, NoOperatorException;
    
    /**
     * 账户转账
     * 
     * @param target 目标账户
     * @param amount 增加金额
     * @param operator 操作人
     * @throws AccountAmountInvalidException 
     * @throws AccountBalanceInsufficiencyException 
     * @throws NoOperatorException 
     */
    public void transfer(Account target, BigDecimal amount, String operator)
            throws AccountAmountInvalidException, AccountBalanceInsufficiencyException, NoOperatorException, AccountTransferToSelfException;
}
