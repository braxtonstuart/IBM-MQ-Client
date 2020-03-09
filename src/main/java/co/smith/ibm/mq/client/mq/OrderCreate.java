package co.smith.ibm.mq.client.mq;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import com.ibm.mq.jms.MQQueueConnectionFactory;

import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import co.smith.ibm.mq.client.dto.OMSOrderData;


/**
 * Sample IBM MQ Client
 *
 * Author: braxtonstuart
 * Date: 3/6/20
 */
public class OrderCreate
{
	private Logger LOG = LoggerFactory.getLogger(OrderCreate.class);

	public void sendOrderToOMS(OMSOrderData orderData)
	{
		MQQueueConnectionFactory connectionFactory = mqQueueConnectionFactory();

		if(connectionFactory != null)
		{
			JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setDefaultDestinationName("OMS.INBOUND.SAP.SALESORDER.SYNC.Q");
			jmsTemplate.convertAndSend(orderData);
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

				// Oracle JRE CipherSuite
				connectionFactory.setSSLCipherSuite("TLS_RSA_WITH_AES_128_CBC_SHA256");

				// IBM JRE CipherSuite
				// connectionFactory.setSSLCipherSuite("SSL_RSA_WITH_AES_128_CBC_SHA256");

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
