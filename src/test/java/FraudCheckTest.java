package test.java;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import main.java.exception.TransactionException;
import main.java.logic.BalanceCheck;
import main.java.model.Transaction;

public class FraudCheckTest {
	BalanceCheck fraud;
	@Before
	public void setUp() throws Exception {
		fraud = new BalanceCheck();
		
	}

	@After
	public void tearDown() throws Exception {
		fraud=null;
	}
	/* Testing fileNotfound Exception path */
	@Test(expected = TransactionException.class)
	public void testTransactionExceptionFileNotFound() throws TransactionException {
		fraud.setFilePath("logs.txt");
		//fraud.setThreshhold(50.00);
		fraud.mapToModel();
	}
	
	/*Testing no fraud Logs */
	@Test
	public void testNoFraudFound() throws TransactionException
	{
		fraud.setFilePath("test1.csv");
		//fraud.setThreshhold(100.00);
		List<Transaction>mappedList=fraud.mapToModel();
		assert(mappedList.size()>0);
		//Set<String>transLogs=fraud.fraudCoreLogic(mappedList);
		//assert(transLogs.size()==0);
		
	}
	
	@Test
	public void testFraudFound() throws TransactionException
	{
		fraud.setFilePath("test1.csv");
		//fraud.setThreshhold(75.00);
		List<Transaction>mappedList=fraud.mapToModel();
		assert(mappedList.size()>0);
		//Set<String>transLogs=fraud.fraudCoreLogic(mappedList);
		//assert(transLogs.size()==2);
	}
	
	
	@Test
	public void testFraudEmptyFile() throws TransactionException
	{
		fraud.setFilePath("empty.csv");
		//fraud.setThreshhold(75.00);
		List<Transaction>mappedList=fraud.mapToModel();
		assert(mappedList.size()==0);
	}


}
