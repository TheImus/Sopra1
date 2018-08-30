package model;
import java.io.Serializable;
public class Address implements Serializable {

	private String street;

	private String city;

	private String zipCode;

	private String addressAdditional;

	
	/**
	 * Initialize fields with empty values
	 */
	public Address() {
		this.street = "";
		this.city = "";
		this.zipCode = "";
		this.addressAdditional = "";
	}
	
	public Address(String City, String street, String zipCode) {
		this.street = "";
		this.city = "";
		this.zipCode = "";
		this.addressAdditional = "";
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
	
	public String toString(){
		return this.street + ", " + this.addressAdditional;
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

	public String getAddressAdditional() {
		return addressAdditional;
	}

	public void setAddressAdditional(String addressAdditional) {
		this.addressAdditional = addressAdditional;
	}
	
}
