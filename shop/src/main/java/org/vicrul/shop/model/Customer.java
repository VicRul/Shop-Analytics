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
@ToString(exclude = {"id", "purchases"})
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "surname")
	private String lastName;
	
	@Column(name = "name")
	private String firstName;
	
	@OneToMany(mappedBy = "customer")
	private List<Purchase> purchases;

	public Customer(String surname, String name) {
		this.lastName = surname;
		this.firstName = name;
		this.purchases = new ArrayList<Purchase>();
	}
}
