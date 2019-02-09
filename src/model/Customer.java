package model;

/**
 * A simple bean to hold data
 */
public class Customer {
private String username;
private String email;
private String street;
private String city;
private String zipCode;
private String phone;
private String password;
private String nickname;
private String description;
private String photo;
public Customer(String username, String email, String street, String city, String zipCode, String phone,
		String password, String nickname, String description, String photo) {
	super();
	this.username = username;
	this.email = email;
	this.street = street;
	this.city = city;
	this.zipCode = zipCode;
	this.phone = phone;
	this.password = password;
	this.nickname = nickname;
	this.description = description;
	this.photo = photo;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getZipCode() {
	return zipCode;
}
public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}


}