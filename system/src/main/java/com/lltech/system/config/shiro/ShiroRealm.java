package com.lltech.system.config.shiro;

import com.lltech.common.utils.JwtUtils;
import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.service.SysMenuDOService;
import com.lltech.system.modules.system.service.SysUserDOService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by L.C.Y on 2018-11-26
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {
    private final static Integer SUPER_ADMIN = 1;

    private final SysUserDOService sysUserDOService;
    private final SysMenuDOService sysMenuDOService;

    @Autowired
    public ShiroRealm(SysUserDOService sysUserDOService, SysMenuDOService sysMenuDOService) {
        this.sysUserDOService = sysUserDOService;
        this.sysMenuDOService = sysMenuDOService;
    }

    /**
     * 授权-判断是否有相应的角色和权限
     * Retrieves the AuthorizationInfo for the given principals from the underlying data store.  When returning
     * an instance from this method, you might want to consider using an instance of
     * {@link SimpleAuthorizationInfo SimpleAuthorizationInfo}, as it is suitable in most cases.
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     * @see SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("Shiro进行鉴权");
        String token = (String) principals.getPrimaryPrincipal();
        Long userId = Long.valueOf(String.valueOf(
                JwtUtils.parse(token).get("userId") == null
                        ? 0 : JwtUtils.parse(token).get("userId")));
        log.info("用户Id：" + userId);
        // 基于权限的控制
        Set<String> perms = new HashSet<>();
        Set<Integer> roleIds = sysUserDOService.listRoleIdByUserId(userId);
        log.info("roleIds：" + roleIds);
        // 如果角色是超级管理员，分配所有权限
        if (roleIds != null && roleIds.size() != 0 && roleIds.contains(SUPER_ADMIN)) {
            log.info("超级管理员授权所有权限");
            List<SysMenuDO> menus = sysMenuDOService.listMenu(new SysMenuDO());
            for (SysMenuDO menu : menus) {
                perms.add(menu.getPerms());
            }
        }
        // 如果角色不是超级管理员，就查询相应的权限
        else {
            log.info("不是超级管理员，授权相应的权限");
            perms = sysUserDOService.listStringPermsByUserId(userId);
        }

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(perms);
        return authorizationInfo;
    }

    /**
     * 认证-将登录信息放入此处，以便后续shiro自动进行鉴权
     * Retrieves authentication data from an implementation-specific datasource (RDBMS, LDAP, etc) for the given
     * authentication token.
     * <p/>
     * For most datasources, this means just 'pulling' authentication data for an associated subject/user and nothing
     * more and letting Shiro do the rest.  But in some systems, this method could actually perform EIS specific
     * log-in logic in addition to just retrieving data - it is up to the Realm implementation.
     * <p/>
     * A {@code null} return value means that no account could be associated with the specified token.
     *
     * @param token the authentication token containing the user's principal and credentials.
     * @return an {@link AuthenticationInfo} object containing account data resulting from the
     * authentication ONLY if the lookup is successful (i.e. account exists and is valid, etc.)
     * @throws AuthenticationException if there is an error acquiring data or performing
     *                                 realm-specific authentication logic for the specified <tt>token</tt>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("Shiro进行认证");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String jwt = (String) usernamePasswordToken.getPrincipal();
        return new SimpleAuthenticationInfo(jwt, jwt, getName());
    }
}
