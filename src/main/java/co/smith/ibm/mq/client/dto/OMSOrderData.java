package co.smith.ibm.mq.client.dto;

import java.io.Serializable;

public  class OMSOrderData implements Serializable
{
 	private static final long serialVersionUID = 1L;

	private String orderNo;

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(final String orderNo)
	{
		this.orderNo = orderNo;
	}
}
