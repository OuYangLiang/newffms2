package com.personal.oyl.newffms.account.store.mapper;

import java.util.List;
import java.util.Map;

import com.personal.oyl.newffms.account.domain.Account;
import com.personal.oyl.newffms.account.domain.AccountKey;

public interface AccountMapper {
    List<Account> select(Account param);

    int insert(Account param);

    int delete(AccountKey key);

    int changeAccountDesc(Map<String, Object> param);

    int updateBalance(Map<String, Object> param);
}
