package com.personal.oyl.newffms.consumption.domain;

import com.personal.oyl.newffms.account.domain.AccountException.AccountBalanceInsufficiencyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumEmptyException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountBatchNumInvalidException;
import com.personal.oyl.newffms.account.domain.AccountException.AccountKeyEmptyException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAlreadyConfirmedException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAmountNotMatchException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionBatchNumEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemAmountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemAmountInvalidException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemCategoryEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemDescEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemOwnerEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemsEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionNotConfirmedException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAccountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAmountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAmountInvalidException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentsEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionTimeEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionTypeEmptyException;

public interface ConsumptionOperation {
    /**
     * 确认消费
     * 
     * @param operator 操作者
     * @throws NoOperatorException
     * @throws ConsumptionAlreadyConfirmedException
     * @throws AccountBalanceInsufficiencyException
     * @throws NewffmsSystemException
     */
    public void confirm(String operator) throws NoOperatorException, ConsumptionAlreadyConfirmedException,
            AccountBalanceInsufficiencyException, NewffmsSystemException;
    
    /**
     * 取消确认
     * 
     * @param operator 操作者
     * @throws AccountKeyEmptyException 
     * @throws AccountBatchNumInvalidException 
     * @throws AccountBatchNumEmptyException 
     * @throws NoOperatorException 
     * @throws ConsumptionNotConfirmedException 
     */
    public void unconfirm(String operator)
            throws NoOperatorException, NewffmsSystemException, ConsumptionNotConfirmedException;
    
    /**
     * 更新消费信息
     * 
     * @param operator 操作人
     * @throws ConsumptionTypeEmptyException
     * @throws ConsumptionBatchNumEmptyException
     * @throws ConsumptionTimeEmptyException
     * @throws ConsumptionAlreadyConfirmedException
     * @throws ConsumptionItemsEmptyException
     * @throws ConsumptionItemDescEmptyException
     * @throws ConsumptionItemAmountEmptyException
     * @throws ConsumptionItemAmountInvalidException
     * @throws ConsumptionItemOwnerEmptyException
     * @throws ConsumptionItemCategoryEmptyException
     * @throws ConsumptionPaymentsEmptyException
     * @throws ConsumptionPaymentAmountEmptyException
     * @throws ConsumptionPaymentAmountInvalidException
     * @throws ConsumptionPaymentAccountEmptyException
     * @throws ConsumptionAmountNotMatchException
     * @throws NoOperatorException
     * @throws NewffmsSystemException
     */
    public void updateAll(String operator)
            throws ConsumptionTypeEmptyException, ConsumptionBatchNumEmptyException, ConsumptionTimeEmptyException,
            ConsumptionAlreadyConfirmedException, ConsumptionItemsEmptyException, ConsumptionItemDescEmptyException,
            ConsumptionItemAmountEmptyException, ConsumptionItemAmountInvalidException,
            ConsumptionItemOwnerEmptyException, ConsumptionItemCategoryEmptyException,
            ConsumptionPaymentsEmptyException, ConsumptionPaymentAmountEmptyException,
            ConsumptionPaymentAmountInvalidException, ConsumptionPaymentAccountEmptyException,
            ConsumptionAmountNotMatchException, NoOperatorException, NewffmsSystemException;
}
