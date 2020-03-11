package co.smith.ibm.mq.client.dto;

import java.io.Serializable;

public class Message implements Serializable
{
 	private static final long serialVersionUID = 1L;

	private String contents;

	public String getContents()
	{
		return contents;
	}

	public void setContents(final String contents)
	{
		this.contents = contents;
	}
}
