package com.mec.pojo.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	
	@NotNull
	@Size(min = 1, max = 25, message = "You need to enter a Seat Description (max 25 char)")
	@Pattern(regexp = "[A-za-z ]*", message="Description must contain only letters and spaces")
	private String description;
	
	@NotNull
	private int price;
	
	@NotNull	
	private int quantity;	
	
	
	//Bi-directional one-to-many association to Seat
	@OneToMany(mappedBy="seatType", fetch=FetchType.EAGER)
	private List<Seat> seats;

	
	private SeatPosition position;
	public SeatType() {
		super();
	}
	
	
	public SeatType(Long id, String description, int price, int quantity, List<Seat> seats, SeatPosition position) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.seats = seats;
		this.position = position;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public SeatPosition getPosition() {
		return position;
	}

	public void setPosition(SeatPosition position) {
		this.position = position;
	}



	public static enum SeatPosition{
		 ORCHESTRA("Orchestra", "orchestra")
		,BOX("Box", "box")
		,BALCONY("Balcony", "balcony");
		
		
		private SeatPosition(String label, String dbRepresentation){
			this.label = label;
			this.dbRepresentation = dbRepresentation;
		}
		
		
		public String getLabel() {
			return label;
		}
		public String getDatabaseRepresentation() {
			return dbRepresentation;
		}

		public static SeatPosition fromLabel(String label){
			return label2Position.get(label);
		}

		private final String label;
		private final String dbRepresentation;
		
		private static Map<String, SeatPosition> label2Position = new HashMap<>(); 
		static{
			for(SeatPosition pos : SeatPosition.values()){
				label2Position.put(pos.getLabel(), pos);
			}
		}
	}
   
	@Converter(autoApply=true)
	public static class SeatPositionConverter implements AttributeConverter<SeatPosition, String>{

		@Override
		public String convertToDatabaseColumn(SeatPosition attribute) {
			return attribute.getDatabaseRepresentation();
		}

		@Override
		public SeatPosition convertToEntityAttribute(String dbData) {
			SeatPosition pos =  SeatPosition.fromLabel(dbData);
			if(null == pos){
				throw new IllegalArgumentException("Unknown attribute value: " + dbData);
			}
			return pos;
		}
	}
}
