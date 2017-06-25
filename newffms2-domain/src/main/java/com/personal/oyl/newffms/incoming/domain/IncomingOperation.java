package com.personal.oyl.newffms.incoming.domain;

import com.personal.oyl.newffms.common.NewffmsDomainException.NewffmsSystemException;
import com.personal.oyl.newffms.common.NewffmsDomainException.NoOperatorException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAlreadyConfirmedException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingAmountInvalidException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingDescInvalidException;
import com.personal.oyl.newffms.incoming.domain.IncomingException.IncomingNotConfirmedException;

public interface IncomingOperation {
    /**
     * 确认收入
     * 
     * @param operator 操作者
     * @throws NoOperatorException
     * @throws IncomingAlreadyConfirmedException
     * @throws NewffmsSystemException
     */
    public void confirm(String operator)
            throws NoOperatorException, IncomingAlreadyConfirmedException, NewffmsSystemException;
    
    /**
     * 取消确认
     * 
     * @param operator 操作者
     * @throws NoOperatorException
     * @throws IncomingNotConfirmedException
     * @throws NewffmsSystemException
     */
    public void unconfirm(String operator)
            throws NoOperatorException, IncomingNotConfirmedException, NewffmsSystemException;
    
    /**
     * 更新收入信息
     * 
     * @param operator 操作人
     * @throws NoOperatorException
     * @throws IncomingAlreadyConfirmedException
     * @throws NewffmsSystemException
     * @throws IncomingDescInvalidException
     * @throws IncomingAmountInvalidException
     */
    public void updateAll(String operator) throws NoOperatorException, IncomingAlreadyConfirmedException,
            NewffmsSystemException, IncomingDescInvalidException, IncomingAmountInvalidException;
}
