package com.macro.mall.portal.config;

import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * security模块配置
 * @author 唐世杰
 * @since
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
	@Autowired
	private UmsMemberService memberService;

	@Bean
	public UserDetailsService userDetailsService() {
		//获取登录用户信息
		return username -> memberService.loadUserByUsername(username);
	}
}
