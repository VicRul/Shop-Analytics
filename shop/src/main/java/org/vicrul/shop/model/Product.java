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
@Table(name = "products")
@Data
@ToString(exclude = "purchases")
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String type;
	
	@Column
	private int price;
	
	@OneToMany(mappedBy = "product")
	private List<Purchase> purchases;

	public Product(int id, String type, int price) {
		this.id = id;
		this.type = type;
		this.price = price;
		this.purchases = new ArrayList<Purchase>();
	}
}
