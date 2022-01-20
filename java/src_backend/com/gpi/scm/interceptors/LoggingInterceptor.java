package com.gpi.scm.interceptors;

import java.util.UUID;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

public class LoggingInterceptor {
	/**
		 * 
		 */
	private static final Logger _logger = Logger.getLogger(LoggingInterceptor.class);

	private static final String KEY_LOGGING="REQ_ID";
	/**
	 * Calcolo dei tempi e logging dei metodi di business
	 * 
	 * 
	 * @param ic
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object logging(InvocationContext ic) throws Exception {
		
		long startTime = 0;
		boolean exist=MDC.get(KEY_LOGGING)!=null;
		try {
			if(!exist){
				MDC.put(KEY_LOGGING, UUID.randomUUID());
			}
			_logger.debug("SERVICE." + ic.getMethod().getName());
			startTime = System.currentTimeMillis();
			return ic.proceed();
			
		} finally {
			_logger.debug("SERVICE." + ic.getMethod().getName() 
					+ " execute in "+ (System.currentTimeMillis() - startTime) +"ms");
			if(!exist){
				MDC.remove(KEY_LOGGING);
			}
		}
	}

}
