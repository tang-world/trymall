package com.macro.mall.config;

import com.macro.mall.model.UmsResource;
import com.macro.mall.security.component.DynamicSecurityService;
import com.macro.mall.security.config.SecurityConfig;
import com.macro.mall.service.UmsAdminService;
import com.macro.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 * @author 唐世杰
 * @since
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法前方法后验证身份
public class MallSecurityConfig extends SecurityConfig {
	@Autowired
	private UmsAdminService adminService;
	@Autowired
	private UmsResourceService resourceService;

	@Bean
	public UserDetailsService userDetailsService() {
		//获取登录用户信息
		UserDetailsService userDetailsService = username -> adminService.loadUserByUsername(username);
		//这里的lambda表达式,UserDetailsService接口只有一个方法,返回的是接口类型,而不是方法的返回类型
		return userDetailsService;
	}

	@Bean
	public DynamicSecurityService dynamicSecurityService() {
		return ()->{
			Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
			List<UmsResource> resourceList = resourceService.listAll();
			for (UmsResource resource : resourceList) {
				map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
			}
			return map;
		};
		//可能是lambda表示式错误导致不能启动
		/*return new DynamicSecurityService() {
			@Override
			public Map<String, ConfigAttribute> loadDataSource() {
				Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
				List<UmsResource> resourceList = resourceService.listAll();
				for (UmsResource resource : resourceList) {
					map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
				}
				return map;
			}
		};*/
	}
}
