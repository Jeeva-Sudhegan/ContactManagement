import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PostPut;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PutContext;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;

public class Service {

	public Service() {
		super();
	}

	public static boolean isString(String word) {
		word = word.trim();
		if (word.length() == 0)
			return false;
		else if (isInteger(word))
			return false;
		else
			return word.matches("^[a-zA-Z0-9 _-]*$");
	}

	public static boolean isInteger(String id) {

		try {
			@SuppressWarnings("unused")
			int x = Integer.parseInt(id);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean validate(String name, String id) {

		if (isString(name) && isInteger(id))
			return true;
		else
			return false;

	}
	
	public static List<Entity> listEntity(String kind) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(kind).addSort("Last_Updated", SortDirection.DESCENDING);
		FetchOptions options = FetchOptions.Builder.withPrefetchSize(10).chunkSize(10);
		List<Entity> pq = ds.prepare(query).asList(options);
		System.out.println(pq);
		return pq;
	}

	public static Iterable<Entity> listEntity(String kind, Key key, String property, String value) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(kind).setAncestor(key);
		query.setFilter(new Query.FilterPredicate(property, FilterOperator.EQUAL, value));
		PreparedQuery pq = ds.prepare(query);
		return pq.asIterable();
	}
	
	public static List<Contact> getContactList() {
		
		List<Contact> list = new ArrayList<Contact>();
		List<Mail> mailList;
		List<Phone> phoneList;
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Contact contact;
		Address address;
		AdditionalInformation additionalInformation;
		Phone phone;
		Mail mail;
		for (Entity entity : listEntity("Contact")) {
			mailList = new ArrayList<Mail>();
			phoneList = new ArrayList<Phone>();
			additionalInformation = new AdditionalInformation();
			contact = new Contact();
			contact.setName(entity.getProperty("Name").toString());
			contact.setLogin(entity.getProperty("Login").toString());
			contact.setID(entity.getKey().getName());
			for (Entity additionalInfo : listEntity("Additional_Information", entity.getKey(), "Type", "E-Mail")) {
				mail = new Mail();
				mail.setMail(additionalInfo.getProperty("Value").toString());
				mail.setMailUUID(additionalInfo.getKey().getName());
				mailList.add(mail);
			}
			for (Entity additionalInfo : listEntity("Additional_Information", entity.getKey(), "Type", "Mobile")) {
				phone = new Phone();
				phone.setPhone(additionalInfo.getProperty("Value").toString());
				phone.setPhoneUUID(additionalInfo.getKey().getName());
				phoneList.add(phone);
			}
			additionalInformation.setMailList(mailList);
			additionalInformation.setPhoneList(phoneList);
			contact.setAdditionalInformation(additionalInformation);
			Entity addressEntity = datastoreService.prepare(new Query("Address").setAncestor(entity.getKey()))
					.asSingleEntity();
			address = new Address();
			address.setHouseNumber(addressEntity.getProperty("House_Number").toString());
			address.setStreetName(addressEntity.getProperty("Street_Name").toString());
			address.setArea(addressEntity.getProperty("Area").toString());
			address.setCity(addressEntity.getProperty("City").toString());
			address.setState(addressEntity.getProperty("State").toString());
			address.setZipCode(addressEntity.getProperty("ZipCode").toString());
			address.setAddressUUID(addressEntity.getKey().getName());
			contact.setAddress(address);
			list.add(contact);
		}

		return list;
	}

	@PostPut(kinds = {"Additional_Information","Address"})
    void updateTime(PutContext context){
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		try {
			Entity e = datastoreService.get(context.getCurrentElement().getParent());
			e.setProperty("Last_Updated", new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a").format(new Date()));
			datastoreService.put(transaction, e);
			transaction.commit();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
		}
    }
    
	
	public static Entity isContactPresent(String login) {

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Contact");
		query.setFilter(new Query.FilterPredicate("Login", FilterOperator.EQUAL, login));
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		return preparedQuery.asSingleEntity();
	}

	public boolean addName(String login, String id, String name, AdditionalInformation additionalInformation,
			Address address) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		if (isContactPresent(login) != null) {
			return false;
		} else {
			Transaction transaction = datastoreService.beginTransaction();
			Entity contact = new Entity("Contact", id);
			contact.setProperty("Login", login);
			contact.setProperty("Name", name);
			contact.setProperty("Created", new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a").format(new Date()));
			contact.setProperty("Last_Updated", new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a").format(new Date()));
			datastoreService.put(transaction, contact);
			Entity additionalEntity;
			List<Entity> list = new ArrayList<>();
			List<Mail> mail = additionalInformation.getMailList();
			if(mail != null) {
				for (Mail email : mail) {
					additionalEntity = new Entity("Additional_Information", email.getMailUUID(), contact.getKey());
					additionalEntity.setProperty("Value", email.getMail());
					additionalEntity.setProperty("Type", "E-Mail");
					list.add(additionalEntity);
				}
			}
			List<Phone> phone = additionalInformation.getPhoneList();
			if(phone!=null) {
				for (Phone mobile : phone) {
					additionalEntity = new Entity("Additional_Information", mobile.getPhoneUUID(), contact.getKey());
					additionalEntity.setProperty("Value", mobile.getPhone());
					additionalEntity.setProperty("Type", "Mobile");
					list.add(additionalEntity);
				}
			}
			datastoreService.put(transaction, list);
			Entity addressEntity = new Entity("Address", address.getAddressUUID(), contact.getKey());
			addressEntity.setProperty("House_Number", address.getHouseNumber());
			addressEntity.setProperty("Street_Name", address.getStreetName());
			addressEntity.setProperty("Area", address.getArea());
			addressEntity.setProperty("City", address.getCity());
			addressEntity.setProperty("State", address.getState());
			addressEntity.setProperty("ZipCode", address.getZipCode());
			datastoreService.put(transaction, addressEntity);
			transaction.commit();
			return true;
		}
	}

	public boolean deleteName(String ID) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey("Contact", ID);
		Query query = new Query().setAncestor(key);
		PreparedQuery pq = datastoreService.prepare(query.setKeysOnly());
		for (Entity entity : pq.asIterable()) {
			datastoreService.delete(entity.getKey());
		}
		return true;
	}

	public boolean updateContact(String id, String login, String name) {
		// TODO Auto-generated method stub
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		Key parentKey = KeyFactory.createKey("Contact",id);
		try {
			if(isContactPresent(login)==null) {
				Entity contact = datastoreService.get(parentKey);
				if(login != null)
					contact.setProperty("Login", login);
				if(name != null)
					contact.setProperty("Name", name);
				contact.setProperty("Last_Updated", new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a").format(new Date()));
				datastoreService.put(transaction,contact);
				transaction.commit();
				return true;
			} 
			return false;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	public boolean updateAdditionalInformation(String id, AdditionalInformation additionalInformation)  {
		// TODO Auto-generated method stub
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		List<Mail> mailList = additionalInformation.getMailList();
		List<Phone> phoneList = additionalInformation.getPhoneList();
		Key parentKey = KeyFactory.createKey("Contact", id);
		try {
			if(mailList!=null) {
				for (Mail mail : mailList) {
					Entity mailEntity = datastoreService.get(KeyFactory.createKey(parentKey, "Additional_Information", mail.getMailUUID()));
					mailEntity.setProperty("Value", mail.getMail());
					datastoreService.put(transaction,mailEntity);
				}
			}
			if(phoneList!=null) {
				for (Phone phone : phoneList) {
					Entity phoneEntity = datastoreService.get(KeyFactory.createKey(parentKey,"Additional_Information", phone.getPhoneUUID()));
					phoneEntity.setProperty("Value", phone.getPhone());
					datastoreService.put(transaction,phoneEntity);
				}
			}
			transaction.commit();
			return true;
		} catch (EntityNotFoundException exception) {
			exception.printStackTrace();
			transaction.rollback();
			return false;
		}
		
	}

	public boolean updateAddress(String id, Address address) {
		// TODO Auto-generated method stub
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastoreService.beginTransaction();
		Key parentKey = KeyFactory.createKey("Contact", id);
		try {
			Entity addressEntity = datastoreService.get(KeyFactory.createKey(parentKey, "Address", address.getAddressUUID()));
			if(address.getHouseNumber()!=null)
				addressEntity.setProperty("House_Number", address.getHouseNumber());
			if(address.getStreetName()!=null)
				addressEntity.setProperty("Street_Name", address.getStreetName());
			if(address.getArea()!=null)
				addressEntity.setProperty("Area", address.getArea());
			if(address.getState()!=null)
				addressEntity.setProperty("State", address.getState());
			if(address.getCity()!=null)
				addressEntity.setProperty("City", address.getCity());
			if(address.getZipCode()!=null)
				addressEntity.setProperty("ZipCode", address.getZipCode());
			datastoreService.put(transaction, addressEntity);
			transaction.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static void adding_contacts_for_test() {
		String name = "Jeeva";
		String mailDomain = "@gmail.com";
		for(int i=1;i<=10;i++) {
			boolean result = new Service().addName(name+i+mailDomain,UUID.randomUUID().toString(), name+i,new AdditionalInformation(Arrays.asList(new Mail("mail"+i, UUID.randomUUID().toString())),Arrays.asList(new Phone("phone"+i, UUID.randomUUID().toString()))),new Address(Integer.toString(i), "streetName"+i, "area"+i, "city"+i, "state"+i, "zipCode"+i, UUID.randomUUID().toString()));
		}
				
	}
}