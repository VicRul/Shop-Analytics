package org.vicrul.shop.model;

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

@Entity
@Table(name = "Customers")
@Data
@NoArgsConstructor
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String surname;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy = "customer")
	private List<Purchases> purchases;

	public Customers(String surname, String name, List<Purchases> purchases) {
		this.surname = surname;
		this.name = name;
		this.purchases = purchases;
	}
}
