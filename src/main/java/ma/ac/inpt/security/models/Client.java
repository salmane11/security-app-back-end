package ma.ac.inpt.security.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long client_id;
	
	String first_name;
	String last_name;
	String image;
	
	int age;
	
	@OneToMany(mappedBy="client")
	List<Orders> orders=new  ArrayList<Orders>();
	
	
	public Client(long client_id, String first_name, String last_name, int age) {
		this.client_id = client_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
	}


	public Client() {
		// TODO Auto-generated constructor stub
	}


	public long getClient_id() {
		return client_id;
	}
	public void setClient_id(long client_id) {
		this.client_id = client_id;
	}


	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
}
