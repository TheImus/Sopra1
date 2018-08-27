package model;

import java.time.LocalDate;

public class Person {

	private String name;

	private String mailAddress;

	private LocalDate birthDate;

	private String phoneNumber;
	
	
	public Person() {
		this.name = "";
		this.mailAddress = "";
		this.birthDate = LocalDate.now();
		this.phoneNumber = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
