package com.mec.pojo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Company {
	@Id
	private String companyName;
	
	public Company(){
		
	}
}
