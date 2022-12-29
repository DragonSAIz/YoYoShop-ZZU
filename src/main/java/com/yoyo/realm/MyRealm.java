package com.yoyo.realm;

import com.yoyo.entity.Admins;
import com.yoyo.service.IAdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IAdminService iAdminService;

    /**
     * 做角色和权限的控制
     * @param principalCollection
     * @return
     */
    @Override protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 做用户登录,用户是否存在,用户密码是否存在
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        //shiro要求程序员自己实现
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //判断用户是否存在,根据用户名查询数据库即可
        Admins admin = iAdminService.getAdminByUserName(username);
        if (admin == null) {
            return null;//告诉shiro用户不存在数据库中,shiro会抛出UnknownAccountException异常
        }
        //如果用户存在,将数据库查询到的密码交给shiro,shiro会自动进行密码的判断
        //如果密码不一致,抛出IncorrectCredentialsException
        return new SimpleAuthenticationInfo(admin, admin.getPassword(), this.getName());
    }
}
