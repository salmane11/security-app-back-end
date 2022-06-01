package ma.ac.inpt.security.models;




import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.JoinColumn;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long order_id;
	
	private Date order_date;

	private String libelle;
	
	@ManyToOne
	@JoinColumn(name="client_id", referencedColumnName = "client_id")
	private Client client;
	

	public Orders() {
		super();
	}
	public Orders(Date order_date, Client client) {
		super();
		this.order_date = order_date;
		this.client = client;
	}

	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public Date getOrderDate() {
		return order_date;
	}
	public void setOrderDate(Date order_date) {
		this.order_date = order_date;
	}

	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
}
