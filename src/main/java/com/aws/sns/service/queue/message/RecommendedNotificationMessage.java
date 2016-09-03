/**
 * 
 */
package com.aws.sns.service.queue.message;

import javax.xml.bind.annotation.XmlRootElement;

import com.aws.sns.service.notifications.concurrent.NotificationTaskWorkerInput;

/**
 * @author vinod
 *
 */
@XmlRootElement
public class RecommendedNotificationMessage extends NotificationTaskWorkerInput implements IMessage{

}
