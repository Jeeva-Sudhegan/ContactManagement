import java.util.UUID;

public class Address {

	private String houseNumber;
	private String streetName;
	private String area;
	private String city;
	private String state;
	private String zipCode;
	private String addressUUID = UUID.randomUUID().toString();

	public Address() {}
	
	public Address(String houseNumber, String streetName, String area, String city, String state, String zipCode,
			String addressUUID) {
		this.houseNumber = houseNumber;
		this.streetName = streetName;
		this.area = area;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.addressUUID = addressUUID;
	}
	
	public String getAddressUUID() {
		return addressUUID;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	@Override
	public String toString() {
		return "Address [houseNumber=" + houseNumber + ", streetName=" + streetName + ", area=" + area + ", city="
				+ city + ", state=" + state + ", zipCode=" + zipCode + ", addressUUID=" + addressUUID + "]";
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public void setAddressUUID(String addressUUID) {
		this.addressUUID = addressUUID;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}