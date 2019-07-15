
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import junit.framework.TestCase;

class CheckingValidTest{
	
	@Test
	public void testIsInteger() {
		assertFalse(Service.isInteger(""));
		assertFalse(Service.isInteger("abc"));
		assertFalse(Service.isInteger("!@#"));
		assertFalse(Service.isInteger("a!b#c"));
		assertFalse(Service.isInteger("123abc"));
		assertFalse(Service.isInteger("1@3"));
		assertFalse(Service.isInteger("1234567890000"));
		assertFalse(Service.isInteger("1.12"));
		assertFalse(Service.isInteger("1.12e+10"));
		assertFalse(Service.isInteger("1.12e-10"));
		assertTrue(Service.isInteger("123"));
	}
	
	@Test
	public void testIsString() {
		assertFalse(Service.isString(""));
		assertFalse(Service.isString("  "));
		assertFalse(Service.isString("!@#$"));
		assertFalse(Service.isString("ad#s"));
		assertFalse(Service.isString("123"));
		assertTrue(Service.isString("asd"));
		assertTrue(Service.isString("asd2"));
	}
	
	@Test
	public void testValidate() {
		assertFalse(Service.validate("!@#","%$"));
		assertFalse(Service.validate("",""));
		assertFalse(Service.validate("Je$","43$"));
		assertFalse(Service.validate("Jeeva%$","12"));
		assertFalse(Service.validate("Hello","@#1"));
		assertTrue(Service.validate("Hello","123"));
	}
	
}
