package it.farmah24.obj;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Picture 
{	
	private int     id;
	private byte[]  byteArray;
	private String  mimeType;
	
	public Picture() {		
	}
	
	public Picture(byte[] byteArray, String mimeType) 
	{
		super();
		this.byteArray = byteArray;
		this.mimeType  = mimeType;
	}

	//-------------------------------------------------------------------------

	public int getId() {
		return id;
	}
	public byte[] getByteArray() {
		return byteArray;
	}
	public String getMimeType() {
		return mimeType;
	}

	
	public void setId(int id) {
		this.id = id;
	}
	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	//-------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Picture [id=" + id + ", byteArray="
				+ Arrays.toString(byteArray) + ", mimeType=" + mimeType + "]";
	}
}