import java.util.List;

public class AdditionalInformation {

	private List<Mail> mailList;
	private List<Phone> phoneList;
	
	public AdditionalInformation() {}

	public AdditionalInformation(List<Mail> mailList, List<Phone> phoneList) {
		this.mailList = mailList;
		this.phoneList = phoneList;
	}

	public List<Mail> getMailList() {
		return mailList;
	}

	public void setMailList(List<Mail> mailList) {
		this.mailList = mailList;
	}

	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}

	@Override
	public String toString() {
		return "AdditionalInformation [mail=" + mailList + ", phone=" + phoneList + "]";
	}

}
