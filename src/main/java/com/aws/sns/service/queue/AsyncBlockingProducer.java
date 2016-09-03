/**
 * 
 */
package com.aws.sns.service.queue;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aws.sns.service.queue.message.IMessage;

/**
 * @author Vinod
 *
 */
public class AsyncBlockingProducer implements Runnable {
	private static final Log LOGGER = LogFactory.getLog(AsyncBlockingProducer.class);
	private final BlockingQueue<IMessage> queue;
	
	private IMessage asyncMessage;

	AsyncBlockingProducer(BlockingQueue<IMessage> q, IMessage asyncMessage) {
		queue = q;
		this.asyncMessage = asyncMessage;
	}

	public void run() {
		try {
			LOGGER.info(Thread.currentThread().getName()+ " Async Producer start ...");
			if (!queue.offer(this.asyncMessage)) {
				/*
				 * The non-blocking offer() method returns false if it was
				 * not possible to add the element to this queue.
				 */
				long start = System.currentTimeMillis();
				/*
				 * Now use the put method as its a blocking call and it wail
				 * until the queue space is available.
				 */
				queue.put(asyncMessage);
				LOGGER.info(Thread.currentThread().getName()+ " It seems Consumer is slow. Producer waited for "
								+ (System.currentTimeMillis() - start)
								+ "ms");
			}
		} catch (InterruptedException ex) {
			LOGGER.info("Error in AsyncBlockingProducer : "+ex);
		}
	}

}
