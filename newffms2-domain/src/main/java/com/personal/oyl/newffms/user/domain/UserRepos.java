package com.personal.oyl.newffms.user.domain;

import com.personal.oyl.newffms.user.domain.UserException.UserLoginIdEmptyException;

import java.util.List;

import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;

public interface UserRepos {

    /**
     * 根据用户标识查询用户
     *
     * @param key 用户标识
     * @return 用户实体
     * @throws UserProfileKeyEmptyException
     */
    User userOfId(UserKey key) throws UserKeyEmptyException;
    
    /**
     * 根据登户id查询用户
     *
     * @param loginId 登户id
     * @return 用户实体
     * @throws UserLoginIdEmptyException
     */
    User userOfLoginId(String loginId) throws UserLoginIdEmptyException;
    
    /**
     * 查询所有用户
     *
     * @return 用户实体集合
     */
    List<User> queryAllUser();
    
    /**
     * 根据用户标识查询菜单
     *
     * @param key 用户标识
     * @return 菜单实体集合
     * @throws UserKeyEmptyException
     */
    List<Module> queryMenusByUser(UserKey key) throws UserKeyEmptyException;
    
    /**
     * 根据用户标识查询操作权限
     *
     * @param key 用户标识
     * @return 操作权限集合
     * @throws UserKeyEmptyException
     */
    List<String> queryUrlsByUser(UserKey key) throws UserKeyEmptyException;
}
