package model;

public class Address {

	private String street;

	private String city;

	private String zipCode;

	private String addressAdditional;

	private Participant participant;

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

	public String getAddressAdditional() {
		return addressAdditional;
	}

	public void setAddressAdditional(String addressAdditional) {
		this.addressAdditional = addressAdditional;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}
