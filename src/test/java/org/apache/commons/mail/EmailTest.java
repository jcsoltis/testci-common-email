package org.apache.commons.mail;

import static org.junit.Assert.*;

import java.util.Date;

import javax.mail.internet.InternetAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {

	//static global usage variables to be used in setting email functions
	private static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org",
			"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd" };
	private static final String[] EMPTY_EMAILS = null;
	private static final String[] TEST_HEADER1 = {"name1", "value1"};
	private static final String[] TEST_HEADER2 = {"name2", "value2"};
	private static final String[] TEST_HEADER3 = {"name3", "value3"};
	private static final String[] TEST_HEADER4 = {"name4", "value4"};
	private static final String[] TEST_HEADER_NULL = {null, null};
	private static final String[] TEST_REPLYTO1 = {"email@1.com", "name1"};
	private static final String[] TEST_REPLYTO2 = {"email@2.com", "name2"};
	private static final String[] TEST_REPLYTO3 = {"email@3.com", "name3"};
	private static final String TEST_HOSTNAME = "hostname";
	private static final String TEST_SUBJECT = "subject";
	private static final String TEST_CONTENT = "content";
	private static final boolean TEST_TRUE = true;
	private static final boolean TEST_FALSE = false;
	private static final String TEST_STRING = "string";

	//concrete email object to be tested
	private EmailConcrete emailConcrete;
	
	//Setup method to initialize concrete email
	@Before
	public void setupEmailTest() throws Exception {
		emailConcrete = new EmailConcrete();
	}

	//Test Email addBcc(String... emails)
	@Test
	public void testAddBcc() throws Exception {
		emailConcrete.addBcc(TEST_EMAILS);
		int testBccSize = 3;
		
		assertEquals("AddBcc size", testBccSize, emailConcrete.getBccAddresses().size());
	}
	
	//Test Email addBcc(String... emails) for null list
	@Test
	public void testAddBccEmpty() throws Exception {
		try{     
			emailConcrete.addBcc(EMPTY_EMAILS);
			fail("AddBcc null list not working correctly");
		}catch(EmailException e){}
	}

	//Test Email addCc(String email)
	@Test
	public void testAddCc() throws Exception {
		emailConcrete.addCc(TEST_EMAILS[0]);
		emailConcrete.addCc(TEST_EMAILS[1]);
		int testCcSize = 2;
		
		assertEquals("AddCc size", testCcSize, emailConcrete.getCcAddresses().size());
	}

	//Test Email addHeader(String name, String value)
	@Test
	public void testAddHeader() throws Exception {
		emailConcrete.addHeader(TEST_HEADER1[0], TEST_HEADER1[1]);
		emailConcrete.addHeader(TEST_HEADER2[0], TEST_HEADER2[1]);
		emailConcrete.addHeader(TEST_HEADER3[0], TEST_HEADER3[1]);
		emailConcrete.addHeader(TEST_HEADER4[0], TEST_HEADER4[1]);
		int testHeaderSize = 4;
		
		assertEquals("AddHeader size", testHeaderSize, emailConcrete.headers.size());
	}
	
	//Test Email addHeader(String name, String value) for null name
	@Test
	public void testAddHeaderNullName() throws Exception {
		try{     
			emailConcrete.addHeader(TEST_HEADER_NULL[0],TEST_HEADER2[1]);
			fail("AddHeader null name not working correctly");
		}catch(IllegalArgumentException e){}
	}
	
	//Test Email addHeader(String name, String value) for null value
	@Test
	public void testAddHeaderNullValue() throws Exception {
		try{     
			emailConcrete.addHeader(TEST_HEADER3[0],TEST_HEADER_NULL[1]);
			fail("AddHeader null value not working correctly");
		}catch(IllegalArgumentException e){}
	}

	//Test Email addReplyTo(String email, String name)
	@Test
	public void testAddReplyTo() throws Exception {
		emailConcrete.addReplyTo(TEST_REPLYTO1[0], TEST_REPLYTO1[1]);
		emailConcrete.addReplyTo(TEST_REPLYTO2[0], TEST_REPLYTO2[1]);
		emailConcrete.addReplyTo(TEST_REPLYTO3[0], TEST_REPLYTO3[1]);
		int testReplyToSize = 3;
		
		assertEquals("AddReplyTo size", testReplyToSize, emailConcrete.replyList.size());
	}

	//Test Email buildMimeMessage()
	@Test
	public void testBuildMimeMessage() throws Exception {
		emailConcrete.setHostName(TEST_HOSTNAME);
		emailConcrete.setSubject(TEST_SUBJECT);
		emailConcrete.setContent(TEST_STRING, TEST_CONTENT);
		emailConcrete.setFrom(TEST_EMAILS[2]);
		emailConcrete.addTo(TEST_EMAILS);
		emailConcrete.addBcc(TEST_EMAILS);
		emailConcrete.addCc(TEST_EMAILS[0]);
		emailConcrete.addCc(TEST_EMAILS[1]);
		emailConcrete.addHeader(TEST_HEADER1[0], TEST_HEADER1[1]);
		emailConcrete.addHeader(TEST_HEADER2[0], TEST_HEADER2[1]);
		emailConcrete.addHeader(TEST_HEADER3[0], TEST_HEADER3[1]);
		emailConcrete.addHeader(TEST_HEADER4[0], TEST_HEADER4[1]);
		emailConcrete.addReplyTo(TEST_REPLYTO1[0], TEST_REPLYTO1[1]);
		emailConcrete.addReplyTo(TEST_REPLYTO2[0], TEST_REPLYTO2[1]);
		emailConcrete.addReplyTo(TEST_REPLYTO3[0], TEST_REPLYTO3[1]);
		emailConcrete.setPopBeforeSmtp(TEST_FALSE,TEST_STRING,TEST_STRING,TEST_STRING);
		emailConcrete.buildMimeMessage();
		String testBuildMimeMessageSubject = "subject";
		assertEquals("Build Mime Message subject", testBuildMimeMessageSubject, emailConcrete.getMimeMessage().getSubject());
	}
	
	//Test Email buildMimeMessage() null build
	@Test
	public void testBuildMimeMessageNull() throws Exception {
		try {
			emailConcrete.setHostName(TEST_HOSTNAME);
			emailConcrete.setSubject(null);
			emailConcrete.setContent(null, null);
			emailConcrete.buildMimeMessage();
			fail("BuildMimeMessage null value not working correctly");
		}catch(EmailException e){}
	}	

	//Test Email getHostName()
	@Test
	public void testGetHostName() throws Exception {
		emailConcrete.setHostName(TEST_HOSTNAME);
		String testHostName = "hostname";
		
		assertEquals("GetHostName return", testHostName, emailConcrete.getHostName());
	}
	
	//Test Email getHostName() null hostname
	@Test
	public void testGetHostNameNull() throws Exception {
		
		assertNull("GetHostName null return", emailConcrete.getHostName());
	}

	//Test Email getMailSession
	@Test
	public void testGetMailSession() throws Exception {
		emailConcrete.setHostName(TEST_HOSTNAME);
		
		assertNotNull(emailConcrete.getMailSession());
	}
	
	//Test Email getMailSession() null session
	@Test
	public void testGetMailSessionNull() throws Exception {
		try{     
			emailConcrete.getMailSession();
			fail("GetMailSession null value not working correctly");
		}catch(EmailException e){}
	}

	//Test Email getSentDate()
	@Test
	public void testGetSentDate() throws Exception {
		Date currentSentDate = emailConcrete.getSentDate();
		emailConcrete.setSentDate(emailConcrete.getSentDate());
		
		assertEquals("GetSentDate date return", currentSentDate, emailConcrete.getSentDate());
	}

	//Test Email getSocketConnectionTimeout()
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		assertNotNull("GetSocketTimeout returns null", emailConcrete.getSocketConnectionTimeout());
	}

	//Test Email setFrom(String, email)
	@Test
	public void testSetFrom() throws Exception {
		emailConcrete.setFrom(TEST_EMAILS[2]);
		InternetAddress testFromAddresses = new InternetAddress("abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd");
		
		assertEquals("SetFrom correctly set", testFromAddresses, emailConcrete.getFromAddress());
	}

	//Teardown method blank because nothing to really tear down
	@After
	public void teardownEmailTest() throws Exception {
		
	}
}
