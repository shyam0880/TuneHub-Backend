package com.example.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DBPing {
	@Id
	private String key;
	private String value;
	
	public DBPing() { super(); }

	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }

	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	
}
