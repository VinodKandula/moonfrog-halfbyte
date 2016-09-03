/**
 * 
 */
package com.aws.sns.service.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.service.queue.message.IMessage;

/**
 * @author Vinod
 *
 */
public class AsyncMessageQueueUtil {
	private static final Log LOGGER = LogFactory.getLog(AsyncMessageQueueUtil.class);
	
	// Measure the CPU utilization by increasing the number of threads one by
	// one and stop when the CPU utilization reaches close to 90-100%
	private static final int CONSUMER_THREAD_POOL_SIZE = 7;
		
	//private static final int BLOCKING_QUEUE_SIZE = 200;
	
	public static BlockingQueue<IMessage> _QUEUE = new LinkedBlockingQueue<IMessage>();
	
	private static ExecutorService service = Executors.newCachedThreadPool(new NamedThreadFactory("UserActivityTaskExeSvc"));
	
	static {
		for (int i = 0; i < CONSUMER_THREAD_POOL_SIZE; i++) {
			AsyncBlockingConsumer asyncBlockingConsumer = new AsyncBlockingConsumer(_QUEUE);
			service.execute(asyncBlockingConsumer);
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run(){
            	SHUTDOWN.set(true);
            	service.shutdown();
            	service = null;
            }
        });
	}
	
	private static AtomicBoolean SHUTDOWN = new AtomicBoolean(false);
	
	public static void enQueueMessage(IMessage asyncMessage) {
		try {
			AsyncBlockingProducer asyncBlockingProducer = new AsyncBlockingProducer(_QUEUE, asyncMessage);
			service.execute(asyncBlockingProducer);
		} catch (Exception e) {
			LOGGER.error("Error in AsyncMessageQueueUtil : "+e);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*for (int i = 0; i < 25; i++) {
			UserActivityFeedMessage asyncMessage = new UserActivityFeedMessage();
			
			produceMessage(asyncMessage);
		}
		
		try {
			Thread.sleep(61000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 25; i < 50; i++) {
			UserActivityFeedMessage asyncMessage = new UserActivityFeedMessage();
			
			produceMessage(asyncMessage);
		}*/

	}

}
