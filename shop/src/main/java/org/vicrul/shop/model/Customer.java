package org.vicrul.shop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Data
@ToString(exclude = "purchases")
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String surname;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy = "customer")
	private List<Purchase> purchases;

	public Customer(int id, String surname, String name) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.purchases = new ArrayList<Purchase>();
	}
}
