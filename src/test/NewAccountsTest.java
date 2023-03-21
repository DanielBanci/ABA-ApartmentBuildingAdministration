package test;

import static org.junit.Assert.*;

import org.junit.Test;

import appInterface.NewAccounts;

public class NewAccountsTest {

	@Test
	public void testIsVaidEmail() {
		assertEquals(true,NewAccounts.isValidEmail("ana@gmail.com") );
		assertEquals(false,NewAccounts.isValidEmail("ana@gmail") );
		assertEquals(false,NewAccounts.isValidEmail("@gmail.com") );
		assertEquals(false,NewAccounts.isValidEmail("") );
	}
	
	@Test
	public void testIsValidUsername() {
		assertEquals(true,NewAccounts.isValidUsername("Ana Pop") );
		assertEquals(true,NewAccounts.isValidUsername("Ana_Pop") );
		assertEquals(true,NewAccounts.isValidUsername("ana2") );
		assertEquals(false,NewAccounts.isValidUsername("") );
	}
	
	@Test
	public void testIsValidPhoneNumber() {
		assertEquals(true,NewAccounts.isValidPhoneNumber("0712345678") );
		assertEquals(true,NewAccounts.isValidPhoneNumber("+40756892431") );
		assertEquals(false,NewAccounts.isValidPhoneNumber("458") );
		assertEquals(false,NewAccounts.isValidPhoneNumber("") );
		assertEquals(false,NewAccounts.isValidPhoneNumber("0712345g78") );
	}

	@Test
	public void testIsStrongPassword() {
		assertEquals(true,NewAccounts.isStrongPassword("12345678Aa@") );
		assertEquals(true,NewAccounts.isStrongPassword("ghhjY68&d2@") );
		assertEquals(false,NewAccounts.isValidPhoneNumber("458") );
		assertEquals(false,NewAccounts.isStrongPassword("") );
		assertEquals(false,NewAccounts.isStrongPassword("0712345g78") );
	}
}
