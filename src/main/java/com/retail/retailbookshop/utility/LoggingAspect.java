package com.retail.retailbookshop.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static Log logger = LogFactory.getLog(LoggingAspect.class);

	@AfterThrowing(pointcut = "execution(* com.retail.retailbookshop.service.*Impl.*(..))", throwing = "exception")
	public void LogServiceException(Exception exception) {
		logger.error(exception.getMessage(), exception);
	}
}
