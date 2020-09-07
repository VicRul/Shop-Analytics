package org.vicrul.shop.model;

import javax.persistence.Column;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;
import javax.persistence.Entity;

@Entity
@Table(name = "all_data")
@Data
@ToString(exclude = "id")
@NoArgsConstructor
public class PeriodData {

	@Id
	private int id;
	
	@Column (name = "surname")
	private String lastName;

	@Column (name = "name")
	private String firstName;

	@Column (name = "type")
	private String productName;

	@Column
	private long price;

	@Column(name = "date_purchase")
	private Date datePurchase;

	public PeriodData(String surname, String name, String type, long price, Date datePurchase) {
		this.lastName = surname;
		this.firstName = name;
		this.productName = type;
		this.price = price;
		this.datePurchase = datePurchase;
	}	
		
}
