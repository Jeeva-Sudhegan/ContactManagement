import java.util.UUID;

public class Contact {

	private String name;
	private String login;
	private Address address;
	private AdditionalInformation additionalInformation;
	private String ID = UUID.randomUUID().toString();

	public Contact() {

	}

	public Contact(String name, String login, Address address, AdditionalInformation additionalInformation, String iD) {
		this.name = name;
		this.login = login;
		this.address = address;
		this.additionalInformation = additionalInformation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AdditionalInformation getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(AdditionalInformation additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getID() {
		return ID;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
