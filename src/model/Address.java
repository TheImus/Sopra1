package model;

public class Address {

	private String street;

	private String city;

	private String zipCode;

	private String addressAdditional;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Clones the actual address object
	 * 
	 * @return new Address object with same attributes as the original one
	 */
	public Address clone() {
		Address result = new Address();
		result.addressAdditional = this.getAddressAdditional();
		result.city = this.getCity();
		result.street = this.getStreet();
		result.zipCode = this.getZipCode();
		
		return result;
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

}
