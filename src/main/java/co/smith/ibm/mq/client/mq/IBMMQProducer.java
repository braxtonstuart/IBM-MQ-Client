package co.smith.ibm.mq.client.mq;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueueConnectionFactory;

import co.smith.ibm.mq.client.dto.Message;


/**
 * Sample IBM MQ Client
 *
 * Author: braxtonstuart
 * Date: 3/6/20
 */
public class IBMMQProducer
{
	private Logger LOG = LoggerFactory.getLogger(IBMMQProducer.class);

	public void sendMessage(Message message)
	{
		MQQueueConnectionFactory connectionFactory = mqQueueConnectionFactory();

		if(connectionFactory != null)
		{
			JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setDefaultDestinationName("OMS.INBOUND.SAP.SALESORDER.SYNC.Q");
			jmsTemplate.convertAndSend(message);
			LOG.info("Message sent successfully!");
		}
		else
		{
			LOG.error("Connection Factory is NULL!");
		}
	}

	protected MQQueueConnectionFactory mqQueueConnectionFactory()
	{
		SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();

		SSLContext sslContext = null;
		try
		{
			// Trust All Certificates
			sslContextBuilder.loadTrustMaterial(new TrustAllStrategy());
			sslContext = sslContextBuilder.build();
		}
		catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e)
		{
			LOG.error("Error during setup of SSL Context!", e);
		}

		MQQueueConnectionFactory connectionFactory = null;
		if(sslContext != null)
		{
			connectionFactory = new MQQueueConnectionFactory();
			try
			{
				connectionFactory.setQueueManager("OM_QMGR");
				connectionFactory.setChannel("SYSTEM.SSL.SVRCONN");
				connectionFactory.setHostName("bnc-oms.dev.coc.ibmcloud.com");
				connectionFactory.setPort(1415);
				connectionFactory.setAppName("SAP Commerce");

				// Oracle JRE CipherSuite
				// Ensure -Dcom.ibm.mq.cfg.useIBMCipherMappings=false is passed as a JVM argument
				// Refer To: https://www.ibm.com/support/knowledgecenter/SSFKSJ_8.0.0/com.ibm.mq.dev.doc/q113220_.htm
				connectionFactory.setSSLCipherSuite("TLS_RSA_WITH_AES_128_CBC_SHA256");

				connectionFactory.setTransportType(1);
				connectionFactory.setSSLSocketFactory(sslContext.getSocketFactory());
			}
			catch (Exception e)
			{
				LOG.error("Error during setup of Connection Factory!", e);
			}
		}

		return connectionFactory;
	}
}
