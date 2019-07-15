import java.util.UUID;

public class Phone {

	private String phone;
	private String phoneUUID = UUID.randomUUID().toString();

	public Phone() {}
	
	public Phone(String phone, String phoneUUID) {
		this.phone = phone;
		this.phoneUUID = phoneUUID;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneUUID() {
		return phoneUUID;
	}

	public void setPhoneUUID(String phoneUUID) {
		this.phoneUUID = phoneUUID;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.phoneUUID + " " + this.phone;
	}

}
