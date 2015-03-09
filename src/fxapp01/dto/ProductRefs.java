package fxapp01.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class ProductRefs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8030875135429404808L;
	
	private BigInteger id;
	private String name;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
