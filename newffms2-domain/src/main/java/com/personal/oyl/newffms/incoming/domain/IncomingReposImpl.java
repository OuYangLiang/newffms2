package com.personal.oyl.newffms.incoming.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.personal.oyl.newffms.incoming.store.mapper.AccountIncomingMapper;
import com.personal.oyl.newffms.incoming.store.mapper.IncomingMapper;

public class IncomingReposImpl implements IncomingRepos {

    @Autowired
    private IncomingMapper mapper;
    @Autowired
    private AccountIncomingMapper acntIncomingMapper;

    @Override
    public Incoming incomingOfId(IncomingKey key) throws IncomingKeyEmptyException {
        if (null == key || null == key.getIncomingOid()) {
            throw new IncomingKeyEmptyException();
        }

        Incoming param = new Incoming();
        param.setKey(key);

        List<Incoming> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        Incoming bean = list.get(0);

        AccountIncomingVo subParam = new AccountIncomingVo();
        subParam.setIncomingOid(key.getIncomingOid());

        List<AccountIncomingVo> subList = acntIncomingMapper.select(subParam);
        bean.setAcntRel(subList.get(0));

        return bean;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void add(Incoming bean, String operator)
            throws IncomingDescEmptyException, NoOperatorException, IncomingDescInvalidException,
            IncomingAmountEmptyException, IncomingAmountInvalidException, IncomingTypeEmptyException,
            IncomingAlreadyConfirmedException, IncomingOwnerEmptyException, IncomingDateEmptyException,
            NewffmsSystemException, IncomingAccountEmptyException, IncomingBatchNumEmptyException {
        if (null == bean || null == bean.getIncomingDesc() || bean.getIncomingDesc().trim().isEmpty()) {
            throw new IncomingDescEmptyException();
        }

        if (bean.getIncomingDesc().trim().length() > 30) {
            throw new IncomingDescInvalidException();
        }

        if (null == bean.getAmount()) {
            throw new IncomingAmountEmptyException();
        }

        if (BigDecimal.ZERO.compareTo(bean.getAmount()) >= 0) {
            throw new IncomingAmountInvalidException();
        }

        if (null == bean.getIncomingType()) {
            throw new IncomingTypeEmptyException();
        }

        if (null == bean.getConfirmed()) {
            bean.setConfirmed(false);
        }

        if (bean.getConfirmed()) {
            throw new IncomingAlreadyConfirmedException();
        }

        if (null == bean.getOwnerOid()) {
            throw new IncomingOwnerEmptyException();
        }

        if (null == bean.getIncomingDate()) {
            throw new IncomingDateEmptyException();
        }

        if (null == bean.getBatchNum() || bean.getBatchNum().trim().isEmpty()) {
            throw new IncomingBatchNumEmptyException();
        }

        if (null == bean.getAcntRel() || null == bean.getAcntRel().getAcntOid()) {
            throw new IncomingAccountEmptyException();
        }

        if (null == operator || operator.trim().isEmpty()) {
            throw new NoOperatorException();
        }

        bean.setCreateBy(operator);
        bean.setCreateTime(new Date());

        int n = mapper.insert(bean);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        bean.getAcntRel().setIncomingOid(bean.getKey().getIncomingOid());
        n = acntIncomingMapper.insert(bean.getAcntRel());

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        bean.setSeqNo(1);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void remove(IncomingKey key) throws IncomingKeyEmptyException, IncomingNotExistException,
            IncomingAlreadyConfirmedException, NewffmsSystemException {
        if (null == key || null == key.getIncomingOid()) {
            throw new IncomingKeyEmptyException();
        }

        Incoming obj = this.incomingOfId(key);

        if (null == obj) {
            throw new IncomingNotExistException();
        }

        if (obj.getConfirmed()) {
            throw new IncomingAlreadyConfirmedException();
        }

        int n = mapper.delete(key);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

        AccountIncomingVo itemParam = new AccountIncomingVo();
        itemParam.setIncomingOid(key.getIncomingOid());
        n = acntIncomingMapper.delete(itemParam);

        if (1 != n) {
            throw new NewffmsSystemException();
        }

    }

    @Override
    public Tuple<Integer, List<Incoming>> queryIncomings(IncomingCondition condition, PaginationParameter param) {
        Map<String, Object> querys = new HashMap<>();
        condition.fillMap(querys);
        param.fillMap(querys);

        int count = mapper.getCountOfSummary(querys);
        List<Incoming> list = null;
        if (count > 0) {
            list = mapper.getListOfSummary(querys);
        }
        return new Tuple<Integer, List<Incoming>>(count, list);
    }

    @Override
    public List<Incoming> queryIncomingsByDateRange(Date from, Date to) {
        Map<String, Object> param = new HashMap<>();
        param.put("from", from);
        param.put("to", to);
        
        List<Incoming> list = mapper.selectByDateRange(param);
        if (null != list && !list.isEmpty()) {
            return list;
        }
        
        return null;
    }

}
