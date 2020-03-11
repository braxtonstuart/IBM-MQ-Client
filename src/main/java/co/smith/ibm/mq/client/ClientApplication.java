package co.smith.ibm.mq.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.smith.ibm.mq.client.dto.Message;
import co.smith.ibm.mq.client.mq.IBMMQProducer;

@SpringBootApplication
public class ClientApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ClientApplication.class, args);

		// Test IBM MQ Client during startup
		testIBMMQProducer();
	}

	public static void testIBMMQProducer()
	{
		IBMMQProducer producer = new IBMMQProducer();
		Message message = new Message();

		message.setContents("Hello World!");
		producer.sendMessage(message);
	}
}
