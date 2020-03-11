package co.smith.ibm.mq.client.mq;

import org.junit.jupiter.api.Test;

import co.smith.ibm.mq.client.dto.Message;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: braxtonstuart
 * Date: 3/11/20
 */
class IBMMQProducerTest
{
	IBMMQProducer producer = new IBMMQProducer();
	Message message = new Message();

	@Test
	void sendMessage()
	{
		message.setContents("TEST");
		producer.sendMessage(message);
	}
}