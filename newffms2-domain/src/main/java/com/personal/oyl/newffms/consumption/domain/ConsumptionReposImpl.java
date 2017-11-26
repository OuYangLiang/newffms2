package com.personal.oyl.newffms.consumption.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.personal.oyl.newffms.consumption.store.mapper.AccountConsumptionMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionItemMapper;
import com.personal.oyl.newffms.consumption.store.mapper.ConsumptionMapper;

public class ConsumptionReposImpl implements ConsumptionRepos {
	
	@Autowired
	private ConsumptionMapper mapper;
	@Autowired
	private ConsumptionItemMapper itemMapper;
	@Autowired
	private AccountConsumptionMapper paymentMapper;

    @Override
    public void add(Consumption bean, String operator) throws ConsumptionTypeEmptyException,
            ConsumptionBatchNumEmptyException, ConsumptionTimeEmptyException, ConsumptionAlreadyConfirmedException,
            ConsumptionItemsEmptyException, ConsumptionItemDescEmptyException, ConsumptionItemAmountEmptyException,
            ConsumptionItemAmountInvalidException, ConsumptionItemOwnerEmptyException,
            ConsumptionItemCategoryEmptyException, ConsumptionPaymentsEmptyException,
            ConsumptionPaymentAmountEmptyException, ConsumptionPaymentAmountInvalidException,
            ConsumptionPaymentAccountEmptyException, ConsumptionAmountNotMatchException, NoOperatorException {
    	if (null == bean || null == bean.getCpnType()) {
			throw new ConsumptionTypeEmptyException();
		}
		
		if (null == bean.getBatchNum() || bean.getBatchNum().trim().isEmpty()) {
		    throw new ConsumptionBatchNumEmptyException();
		}
		
		if (null == bean.getCpnTime()) {
		    throw new ConsumptionTimeEmptyException();
		}
		
		if (null == bean.getConfirmed()) {
		    bean.setConfirmed(false);
		}
		
		if (bean.getConfirmed()) {
		    throw new ConsumptionAlreadyConfirmedException();
		}
		
		if (null == bean.getItems() || bean.getItems().isEmpty()) {
		    throw new ConsumptionItemsEmptyException();
		}
		
		bean.setAmount(BigDecimal.ZERO);
		for (ConsumptionItemVo item : bean.getItems()) {
		    if (null == item || null == item.getItemDesc() || item.getItemDesc().trim().isEmpty()) {
		        throw new ConsumptionItemDescEmptyException();
		    }
		    
		    if (null == item.getAmount()) {
		        throw new ConsumptionItemAmountEmptyException();
		    }
		    
		    if (BigDecimal.ZERO.compareTo(item.getAmount()) >= 0) {
		        throw new ConsumptionItemAmountInvalidException();
		    }
		    
		    if (null == item.getOwnerOid()) {
		        throw new ConsumptionItemOwnerEmptyException();
		    }
		    
		    if (null == item.getCategoryOid()) {
		        throw new ConsumptionItemCategoryEmptyException();
		    }
		    
		    bean.setAmount(bean.getAmount().add(item.getAmount()));
		}
		
		if (null == bean.getPayments() || bean.getPayments().isEmpty()) {
            throw new ConsumptionPaymentsEmptyException();
        }
		
		BigDecimal paymentTotal = BigDecimal.ZERO;
		for (AccountConsumptionVo payment : bean.getPayments()) {
		    if (null == payment || null == payment.getAmount()) {
		        throw new ConsumptionPaymentAmountEmptyException();
		    }
		    
		    if (BigDecimal.ZERO.compareTo(payment.getAmount()) >= 0) {
                throw new ConsumptionPaymentAmountInvalidException();
            }
		    
		    if (null == payment.getAcntOid()) {
		        throw new ConsumptionPaymentAccountEmptyException();
		    }
		    
		    paymentTotal = paymentTotal.add(payment.getAmount());
		}
		
		if (paymentTotal.compareTo(bean.getAmount()) != 0) {
		    throw new ConsumptionAmountNotMatchException();
		}
		
		if (null == operator || operator.trim().isEmpty()) {
			throw new NoOperatorException();
		}
		
		bean.setCreateBy(operator);
		bean.setCreateTime(new Date());
		mapper.insert(bean);
		
		for (ConsumptionItemVo item : bean.getItems()) {
			item.setCpnOid(bean.getKey().getCpnOid());
			itemMapper.insert(item);
		}
		
		for (AccountConsumptionVo payment : bean.getPayments()) {
			payment.setCpnOid(bean.getKey().getCpnOid());
			paymentMapper.insert(payment);
		}
		
		bean.setSeqNo(1);
	}

	@Override
	public Consumption consumptionOfId(ConsumptionKey key) throws ConsumptionKeyEmptyException {
		if (null == key || null == key.getCpnOid()) {
			throw new ConsumptionKeyEmptyException();
		}
		
		Consumption param = new Consumption();
		param.setKey(key);
		
		List<Consumption> list = mapper.select(param);
		
		if (null == list || list.isEmpty()) {
			return null;
		}
		
		Consumption bean = list.get(0);
		
		ConsumptionItemVo itemParam = new ConsumptionItemVo();
		itemParam.setCpnOid(key.getCpnOid());
		
		List<ConsumptionItemVo> items = itemMapper.select(itemParam);
		bean.setItems(items);
		
		AccountConsumptionVo paymentParam = new AccountConsumptionVo();
		paymentParam.setCpnOid(key.getCpnOid());
		
		List<AccountConsumptionVo> payments = paymentMapper.select(paymentParam);
		bean.setPayments(payments);
		
		return bean;
	}

    @Override
    public void remove(ConsumptionKey key) throws ConsumptionKeyEmptyException, ConsumptionNotExistException,
            ConsumptionAlreadyConfirmedException, NewffmsSystemException {
    	if (null == key || null == key.getCpnOid()) {
			throw new ConsumptionException.ConsumptionKeyEmptyException();
		}
		
		Consumption obj = this.consumptionOfId(key);
		
		if (null == obj) {
			throw new ConsumptionNotExistException();
		}
		
		if (obj.getConfirmed()) {
			throw new ConsumptionAlreadyConfirmedException();
		}
		
		int n = mapper.delete(key);
		
		if (1 != n) {
			throw new NewffmsSystemException();
		}
		
		ConsumptionItemVo itemParam = new ConsumptionItemVo();
		itemParam.setCpnOid(key.getCpnOid());
		n = itemMapper.delete(itemParam);
		
		if (obj.getItems().size() != n) {
		    throw new NewffmsSystemException();
		}
		
		AccountConsumptionVo paymentParam = new AccountConsumptionVo();
		paymentParam.setCpnOid(key.getCpnOid());
		n = paymentMapper.delete(paymentParam);
		
		if (obj.getPayments().size() != n) {
		    throw new NewffmsSystemException();
		}
	}

    @Override
    public Tuple<Integer, List<ConsumptionItemPaginationVo>> queryConsumptionItems(ConsumptionCondition condition) {
        Map<String, Object> querys = new HashMap<>();
        condition.fillMap(querys);
        
        int count = itemMapper.getCountOfSummary(querys);
        List<ConsumptionItemPaginationVo> list = null;
        if (count > 0) {
            list = itemMapper.getListOfSummary(querys);
        }
        
        return new Tuple<Integer, List<ConsumptionItemPaginationVo>>(count, list);
    }

}
