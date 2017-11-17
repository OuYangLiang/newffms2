package com.personal.oyl.newffms.user.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.personal.oyl.newffms.user.domain.UserException.UserLoginIdEmptyException;
import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.store.mapper.UserMapper;

public class UserReposImpl implements UserRepos {

    @Autowired
    private UserMapper mapper;

    @Override
    public User userProfileOfId(UserKey key) throws UserKeyEmptyException {
        if (null == key || null == key.getUserOid()) {
            throw new UserKeyEmptyException();
        }

        User param = new User();
        param.setKey(key);

        List<User> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public User userProfileOfLoginId(String loginId) throws UserLoginIdEmptyException {
        if (null == loginId || loginId.trim().isEmpty()) {
            throw new UserLoginIdEmptyException();
        }

        User param = new User();
        param.setLoginId(loginId);

        List<User> list = mapper.select(param);

        if (null == list || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<Module> queryMenusByUser(UserKey key) throws UserKeyEmptyException {
        if (null == key || null == key.getUserOid()) {
            throw new UserKeyEmptyException();
        }

        List<Module> list = mapper.selectMenusByUser(key);
        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }

    @Override
    public List<String> queryUrlsByUser(UserKey key) throws UserKeyEmptyException {
        if (null == key || null == key.getUserOid()) {
            throw new UserKeyEmptyException();
        }

        List<String> list = mapper.selectUrlsByUser(key);
        if (null == list || list.isEmpty()) {
            return null;
        }

        return list;
    }

}
