package com.mec.pojo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: SeatType
 *
 */
@Entity
@Table(name="SEAT_TYPE")
public class SeatType implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private int price;
	private int quantity;	
	
	//Bi-directional one-to-many association to Seat
	@OneToMany(mappedBy="seatType", fetch=FetchType.EAGER)
	private List<Seat> seats;

	public SeatType() {
		super();
	}
   
}
