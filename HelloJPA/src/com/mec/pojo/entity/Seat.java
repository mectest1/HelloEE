package com.mec.pojo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Seat
 *
 */
@Entity
@Table(name="SEAT")
public class Seat implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Seat() {
		super();
	}
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private int price;
	private boolean booked;
	@Transient
	private Seat bookedSeat;
	
	@ManyToOne
	@JoinColumn(name="seat_id")
	private SeatType seatType;
	
	public Seat(int id, String type, int price) {
		super();
		this.id = id;
		this.name = type;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public Seat getBookedSeat() {
		return bookedSeat;
	}

	public void setBookedSeat(Seat bookedSeat) {
		this.bookedSeat = bookedSeat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", name=" + name + ", price=" + price + ", booked=" + booked + ", bookedSeat="
				+ bookedSeat + "]";
	}
}



