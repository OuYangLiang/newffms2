package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAlreadyConfirmedException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionAmountNotMatchException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionBatchNumEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemAmountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemAmountInvalidException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemCategoryEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemDescEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemOwnerEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionItemsEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionKeyEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionNotExistException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAccountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAmountEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentAmountInvalidException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionPaymentsEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionTimeEmptyException;
import com.personal.oyl.newffms.consumption.domain.ConsumptionException.ConsumptionTypeEmptyException;

public interface ConsumptionRepos {
    /**
     * 创建新的消费
     *
     * @param param 消费实体
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
     */
    void add(Consumption bean, String operator) throws ConsumptionTypeEmptyException, ConsumptionBatchNumEmptyException,
            ConsumptionTimeEmptyException, ConsumptionAlreadyConfirmedException, ConsumptionItemsEmptyException,
            ConsumptionItemDescEmptyException, ConsumptionItemAmountEmptyException,
            ConsumptionItemAmountInvalidException, ConsumptionItemOwnerEmptyException,
            ConsumptionItemCategoryEmptyException, ConsumptionPaymentsEmptyException,
            ConsumptionPaymentAmountEmptyException, ConsumptionPaymentAmountInvalidException,
            ConsumptionPaymentAccountEmptyException, ConsumptionAmountNotMatchException, NoOperatorException;

    /**
     * 根据消费标识查询消费
     *
     * @param key 消费标识
     * @return 消费实体
     * @throws ConsumptionKeyEmptyException
     */
    Consumption consumptionOfId(ConsumptionKey key) throws ConsumptionKeyEmptyException;

    /**
     * 删除消费
     *
     * @param key 待删除消费标识
     * @throws ConsumptionKeyEmptyException
     * @throws ConsumptionNotExistException
     * @throws ConsumptionAlreadyConfirmedException
     * @throws NewffmsSystemException
     */
    void remove(ConsumptionKey key) throws ConsumptionKeyEmptyException, ConsumptionNotExistException,
            ConsumptionAlreadyConfirmedException, NewffmsSystemException;
    
    /**
     * 根据指定条件查询消费明细
     *
     * @param condition 查询条件
     * @return 消费明细
     */
    Tuple<Integer, List<ConsumptionItemPaginationVo>> queryConsumptionItems(ConsumptionCondition condition);
    
    /**
     * 根据时间范围查询用户消费金额，包括类别明细
     *
     * @param start 起始时间
     * @param end 截止时间
     * @return 用户消费金额，包括类别明细
     */
    List<PersonalConsumptionVo> queryPersonalConsumption(Date start, Date end);
    
    
    /**
     * 查询当月消费总额
     *
     * @return 当月消费总额
     */
    BigDecimal queryMonthlyConsumptionTotal();
}
