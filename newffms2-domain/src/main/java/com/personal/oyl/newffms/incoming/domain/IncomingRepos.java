package com.personal.oyl.newffms.incoming.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAccountEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAlreadyConfirmedException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAmountEmptyException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAmountInvalidException;
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
     * @param bean
     * @param operator
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
     */
    void add(Incoming bean, String operator) throws IncomingDescEmptyException, NoOperatorException,
            IncomingDescInvalidException, IncomingAmountEmptyException, IncomingAmountInvalidException,
            IncomingTypeEmptyException, IncomingAlreadyConfirmedException, IncomingOwnerEmptyException,
            IncomingDateEmptyException, NewffmsSystemException, IncomingAccountEmptyException;
	
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
}
