package com.augustars.xmall.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.augustars.xmall.util.ShiroDBRealm;

@Configuration
public class ShiroFilterConfiguration {
	// 设定自主定义的认证域位置
	@Bean
	public ShiroDBRealm shiroDBRealm() {
		return new ShiroDBRealm();
	}
	
	// 设定Shiro核心认证对象
	@Bean
	public SecurityManager securityManager(ShiroDBRealm shiroDBRealm) {
		DefaultWebSecurityManager securityManager = 
				new DefaultWebSecurityManager();
		securityManager.setRealm(shiroDBRealm);
		return securityManager;
	}
	
	// 设定认证规则过滤器
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		// 设定Shiro核心管理对象
		shiroFilter.setSecurityManager(securityManager);
		// 设定认证地址
		shiroFilter.setLoginUrl("/user/login");
		// 设定认证成功之后，默认的重定向地址
		shiroFilter.setSuccessUrl("/index");
		// 配置整个系统的认证规则
		Map<String, String> filterChainDefinitionMap = 
				new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/index", "anon");
		filterChainDefinitionMap.put("/goods/list/**", "anon");
		filterChainDefinitionMap.put("/goods/detail/*", "anon");
		filterChainDefinitionMap.put("/user/regist", "anon");
		filterChainDefinitionMap.put("/user/logout", "logout");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return shiroFilter;
	}
}
