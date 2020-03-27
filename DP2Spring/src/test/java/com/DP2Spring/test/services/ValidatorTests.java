package com.DP2Spring.test.services;

import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class ValidatorTests {
	

		public Validator createValidator() {
			
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		
		localValidatorFactoryBean.afterPropertiesSet();
		
		return localValidatorFactoryBean;
		
		}
		
}