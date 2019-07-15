import java.util.UUID;

public class Mail {

	private String mail;
	private String mailUUID = UUID.randomUUID().toString();

	public Mail() {}
	
	public Mail(String mail, String mailUUID) {
		this.mail = mail;
		this.mailUUID = mailUUID;
	}



	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMailUUID() {
		return mailUUID;
	}

	public void setMailUUID(String mailUUID) {
		this.mailUUID = mailUUID;
	}

	@Override
	public String toString() {
		return this.mailUUID + " " + this.mail;
	}

}
