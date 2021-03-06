package com.sandman.blog.config.security;

import com.sandman.blog.entity.system.Permission;
import com.sandman.blog.entity.system.Role;
import com.sandman.blog.entity.system.User;
import com.sandman.blog.service.system.UserService;
import com.sandman.blog.utils.PasswordEncrypt;
import com.sandman.blog.utils.ShiroSecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by renhuiyu on 2018/5/23.
 */
public class MyRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String userName = usernamePasswordToken.getUsername();

		User user = userService.findUserByUserName(userName);

		if(user==null)//根据userName查不出来用户
			throw new UnknownAccountException("用户不存在");

		String salt = user.getSalt();
		String password = PasswordEncrypt.getEncryptedPwdBySalt(String.valueOf(usernamePasswordToken.getPassword()),salt);

		if(!password.equals(user.getPassword()))
			throw new IncorrectCredentialsException("登录密码错误");
		if(user.getAvailable()==0)
			throw new LockedAccountException("账户被锁定");
		if(user != null){
			Session session = ShiroSecurityUtils.getSession();
			session.setAttribute("user",user);
			session.setAttribute("id",user.getId());
			session.setAttribute("userName",user.getUserName());
			session.setAttribute("nickName",user.getNickName());
			// 这里我设置的principal传的是user实体
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password,
					ByteSource.Util.bytes(salt),
					this.getName());
			return info;
		}
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		User currentUser = (User) principals.getPrimaryPrincipal();
		System.out.println("授权userName----" + currentUser.toString());

		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

		Set<String> roles = new HashSet<>();//角色信息
		Set<String> permissions = new HashSet<>();//权限信息
		for(Role role:currentUser.getRoleList()){//遍历角色列表
			roles.add(role.getRoleName());//设置用户角色
			for(Permission permission:role.getPermissionList()){//遍历角色权限
				permissions.add(permission.getPermission());//设置角色权限
			}
		}
		info.setRoles(roles);//设置用户角色
		info.setStringPermissions(permissions);//设置角色权限
		return info;
	}
}
