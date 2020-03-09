package co.smith.ibm.mq.client.mq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.smith.ibm.mq.client.dto.OMSOrderData;

/**
 * Used to Test Initial Connectivity to IBM MQ
 *
 * Author: braxtonstuart
 * Date: 3/9/20
 */
class OrderCreateTest
{
	OMSOrderData orderData;
	OrderCreate orderCreate;

	@BeforeEach
	void setUp()
	{
		orderCreate = new OrderCreate();
		orderData = new OMSOrderData();
	}

	@Test
	void sendOrderToOMSTest()
	{
		orderData.setOrderNo("TEST");
		orderCreate.sendOrderToOMS(orderData);
	}
}