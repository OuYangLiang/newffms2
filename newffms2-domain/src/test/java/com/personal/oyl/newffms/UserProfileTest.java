package com.personal.oyl.newffms;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.personal.oyl.newffms.user.domain.Gender;
import com.personal.oyl.newffms.user.domain.User;
import com.personal.oyl.newffms.user.domain.UserException.UserLoginIdEmptyException;
import com.personal.oyl.newffms.user.domain.UserException.UserKeyEmptyException;
import com.personal.oyl.newffms.user.domain.UserKey;
import com.personal.oyl.newffms.user.domain.UserRepos;

import junit.framework.TestCase;

public class UserProfileTest extends TestCase {

    private static ApplicationContext ctx = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = new ClassPathXmlApplicationContext("application-context.xml");
    }

    public void test() {

        UserRepos repos = ctx.getBean(UserRepos.class);
        
        User user = null;
        
        // 测试key查询
        try {
            user = repos.userProfileOfId(new UserKey(BigDecimal.valueOf(1)));
        } catch (UserKeyEmptyException e) {
            e.printStackTrace();
        }
        
        assertNotNull(user);
        assertTrue(BigDecimal.ONE.compareTo(user.getKey().getUserOid()) == 0);
        assertEquals("欧阳亮", user.getUserName());
        assertEquals("老公", user.getUserAlias());
        assertEquals(Gender.Male, user.getGender());
        assertEquals("18652022500", user.getPhone());
        assertEquals("ouyanggod@gmail.com", user.getEmail());
        assertEquals("oyl.jpg", user.getIcon());
        assertEquals("苏宁：高级架构师", user.getRemarks());
        assertEquals("oyl", user.getLoginId());
        assertEquals("3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2", user.getLoginPwd());
        assertTrue(BigDecimal.ONE.compareTo(user.getUserTypeOid()) == 0);
        
        try {
            user = repos.userProfileOfId(new UserKey(BigDecimal.valueOf(2)));
        } catch (UserKeyEmptyException e) {
            e.printStackTrace();
        }
        
        assertNotNull(user);
        assertTrue(BigDecimal.valueOf(2).compareTo(user.getKey().getUserOid()) == 0);
        assertEquals("喻敏", user.getUserName());
        assertEquals("老婆", user.getUserAlias());
        assertEquals(Gender.Female, user.getGender());
        assertEquals("18652930160", user.getPhone());
        assertEquals("yumingirl@gmail.com", user.getEmail());
        assertEquals("ym.jpg", user.getIcon());
        assertEquals("中兴：测试工程师", user.getRemarks());
        assertEquals("yumin", user.getLoginId());
        assertEquals("3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2", user.getLoginPwd());
        assertTrue(BigDecimal.ONE.compareTo(user.getUserTypeOid()) == 0);
        
        try {
            user = repos.userProfileOfId(new UserKey(BigDecimal.valueOf(25)));
        } catch (UserKeyEmptyException e) {
            e.printStackTrace();
        }
        
        assertNull(user);
        
        // 测试login id查询
        try {
            user = repos.userProfileOfLoginId("oyl");
        } catch (UserLoginIdEmptyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertNotNull(user);
        assertTrue(BigDecimal.ONE.compareTo(user.getKey().getUserOid()) == 0);
        assertEquals("欧阳亮", user.getUserName());
        assertEquals("老公", user.getUserAlias());
        assertEquals(Gender.Male, user.getGender());
        assertEquals("18652022500", user.getPhone());
        assertEquals("ouyanggod@gmail.com", user.getEmail());
        assertEquals("oyl.jpg", user.getIcon());
        assertEquals("苏宁：高级架构师", user.getRemarks());
        assertEquals("oyl", user.getLoginId());
        assertEquals("3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2", user.getLoginPwd());
        assertTrue(BigDecimal.ONE.compareTo(user.getUserTypeOid()) == 0);
        
        try {
            user = repos.userProfileOfLoginId("yumin");
        } catch (UserLoginIdEmptyException e) {
            e.printStackTrace();
        }
        
        assertNotNull(user);
        assertTrue(BigDecimal.valueOf(2).compareTo(user.getKey().getUserOid()) == 0);
        assertEquals("喻敏", user.getUserName());
        assertEquals("老婆", user.getUserAlias());
        assertEquals(Gender.Female, user.getGender());
        assertEquals("18652930160", user.getPhone());
        assertEquals("yumingirl@gmail.com", user.getEmail());
        assertEquals("ym.jpg", user.getIcon());
        assertEquals("中兴：测试工程师", user.getRemarks());
        assertEquals("yumin", user.getLoginId());
        assertEquals("3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2", user.getLoginPwd());
        assertTrue(BigDecimal.ONE.compareTo(user.getUserTypeOid()) == 0);
        
        try {
            user = repos.userProfileOfLoginId("oyl123");
        } catch (UserLoginIdEmptyException e) {
            e.printStackTrace();
        }
        
        assertNull(user);
    }

}
