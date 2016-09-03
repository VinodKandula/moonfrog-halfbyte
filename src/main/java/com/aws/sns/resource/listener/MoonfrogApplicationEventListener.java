/**
 * 
 */
package com.aws.sns.resource.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * @author Vinod
 *
 */
public class MoonfrogApplicationEventListener implements ApplicationEventListener {
	private static final Log LOGGER = LogFactory.getLog(MoonfrogApplicationEventListener.class);

	@SuppressWarnings("incomplete-switch")
	@Override
	public void onEvent(ApplicationEvent event) {
		switch (event.getType()) {
        case INITIALIZATION_FINISHED:
        	LOGGER.info("Jersey application started.");
            break;
		}
		
	}

	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		return new MoonfrogRequestEventListener();
	}
	
	 public static class MoonfrogRequestEventListener implements RequestEventListener {
	        private volatile long methodStartTime;

	        @SuppressWarnings("incomplete-switch")
			@Override
	        public void onEvent(RequestEvent requestEvent) {
	            switch (requestEvent.getType()) {
	                case RESOURCE_METHOD_START:
	                    methodStartTime = System.currentTimeMillis();
	                    break;
	                case RESOURCE_METHOD_FINISHED:
	                    long methodExecution = System.currentTimeMillis() - methodStartTime;
	                    final String methodName = requestEvent.getUriInfo().getMatchedResourceMethod().getInvocable().getHandlingMethod().getName();
	                    LOGGER.info("Method '" + methodName + "' executed. Processing time: " + methodExecution + " ms");
	                    break;
	            }
	        }
	    }

}
