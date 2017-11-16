package com.personal.oyl.newffms.user.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.user.domain.User;

public interface UserMapper {
    List<User> select(User param);
}
