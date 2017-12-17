package com.personal.oyl.newffms.incoming.domain;

import java.util.Date;
import java.util.List;

import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.common.PaginationParameter;
import com.personal.oyl.newffms.common.Tuple;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAccountEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAlreadyConfirmedException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAmountEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAmountInvalidException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingBatchNumEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingDateEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingDescEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingDescInvalidException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingKeyEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingNotExistException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingOwnerEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingTypeEmptyException;

public interface IncomingRepos {
    /**
     * 根据收入标识查询收入
     * 
     * @param key 账户标识
     * @return 账户实体
     * @throws IncomingKeyEmptyException
     */
    Incoming incomingOfId(IncomingKey key) throws IncomingKeyEmptyException;
	
    /**
     * 创建新的收入
     * 
     * @param bean 收入实体
     * @param operator 操作人
     * @throws IncomingDescEmptyException
     * @throws NoOperatorException
     * @throws IncomingDescInvalidException
     * @throws IncomingAmountEmptyException
     * @throws IncomingAmountInvalidException
     * @throws IncomingTypeEmptyException
     * @throws IncomingAlreadyConfirmedException
     * @throws IncomingOwnerEmptyException
     * @throws IncomingDateEmptyException
     * @throws NewffmsSystemException
     * @throws IncomingAccountEmptyException
     * @throws IncomingBatchNumEmptyException
     */
    void add(Incoming bean, String operator) throws IncomingDescEmptyException, NoOperatorException,
            IncomingDescInvalidException, IncomingAmountEmptyException, IncomingAmountInvalidException,
            IncomingTypeEmptyException, IncomingAlreadyConfirmedException, IncomingOwnerEmptyException,
            IncomingDateEmptyException, NewffmsSystemException, IncomingAccountEmptyException, IncomingBatchNumEmptyException;
	
    /**
     * 删除收入
     * 
     * @param key 待删除收入标识
     * @throws IncomingKeyEmptyException
     * @throws IncomingNotExistException
     * @throws IncomingAlreadyConfirmedException
     * @throws NewffmsSystemException
     */
    void remove(IncomingKey key) throws IncomingKeyEmptyException, IncomingNotExistException,
            IncomingAlreadyConfirmedException, NewffmsSystemException;
    
    /**
     * 根据指定条件查询收入
     * 
     * @param condition 查询条件
     * @param param 分页信息
     * @return 收入分页信息
     */
    Tuple<Integer, List<Incoming>> queryIncomings(IncomingCondition condition, PaginationParameter param);
    
    /**
     * 根据日期范围查询收入
     * 
     * @param from 起始日期
     * @param to 截止日期
     * @return 收入实体集合
     */
    List<Incoming> queryIncomingsByDateRange(Date from, Date to);
}
