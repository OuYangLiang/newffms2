package com.personal.oyl.newffms.user.store.mapper;

import java.util.List;

import com.personal.oyl.newffms.user.domain.Module;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserKey;

public interface UserMapper {
    List<User> select(User param);
    
    List<Module> selectMenusByUser(UserKey key);
    
    List<String> selectUrlsByUser(UserKey key);
}
