package ma.ac.inpt.security.models;

import org.springframework.web.multipart.MultipartFile;
/**
 * @author salmane
 * this class is implemented as a model for the data exchange between front-end and backend
 * to keep to image as a string in the database (Client model) and extract the image as 
 * amultipart file from react
 */
public class ClientDTO {
	
	String first_name;
	String last_name;
	MultipartFile image;
	int age;
	
	
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
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
