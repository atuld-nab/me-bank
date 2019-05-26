package test.java;

import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import main.java.exception.TransactionException;
import main.java.logic.BalanceCheck;
import main.java.model.Transaction;

public class BalanceCheckTest {
	BalanceCheck balanceCheck;
	@Before
	public void setUp() throws Exception {
		balanceCheck = new BalanceCheck();
		
	}

	@After
	public void tearDown() throws Exception {
		balanceCheck=null;
	}
	/* Testing fileNotfound Exception path */
	@Test(expected = TransactionException.class)
	public void testTransactionExceptionFileNotFound() throws TransactionException {
		balanceCheck.setFilePath("logs.txt");
		balanceCheck.mapToModel();
	}
	
	/*Testing no fraud Logs */
	@Test
	public void testNoBalanceFound() throws TransactionException
	{
		String fromTimeStamp = "2017-02-03T09:00:00";
		String toTimeStamp = "2017-02-04T16:30:30";
		balanceCheck.setFilePath("test1.csv");
		balanceCheck.setAccount("PNB126");
		balanceCheck.setTimeStampStart(LocalDateTime.parse(fromTimeStamp));
		balanceCheck.setTimeStampEnd(LocalDateTime.parse(toTimeStamp));
		List<Transaction>mappedList=balanceCheck.mapToModel();
		assert(mappedList.size()>0);
		assert(balanceCheck.scanReversalPayments(mappedList).size()==0);
		assert(balanceCheck.BalanceCoreLogic(mappedList)==0.0);
		
	}
	
	@Test
	public void testNoReversalFound() throws TransactionException
	{
		String fromTimeStamp = "2017-02-03T09:00:00";
		String toTimeStamp = "2017-02-04T16:30:30";
		balanceCheck.setFilePath("test1.csv");
		balanceCheck.setAccount("PNB135");
		balanceCheck.setTimeStampStart(LocalDateTime.parse(fromTimeStamp));
		balanceCheck.setTimeStampEnd(LocalDateTime.parse(toTimeStamp));
		List<Transaction>mappedList=balanceCheck.mapToModel();
		assert(mappedList.size()>0);
		assert(balanceCheck.scanReversalPayments(mappedList).size()==0);
		assert(balanceCheck.BalanceCoreLogic(mappedList)>0.0);
	}
	
	
	@Test
	public void validateReversalPayments() throws TransactionException
	{
		String fromTimeStamp = "2017-02-03T09:00:00";
		String toTimeStamp = "2017-02-04T16:30:30";
		balanceCheck.setFilePath("records.csv");
		balanceCheck.setAccount("PNB135");
		balanceCheck.setTimeStampStart(LocalDateTime.parse(fromTimeStamp));
		balanceCheck.setTimeStampEnd(LocalDateTime.parse(toTimeStamp));
		List<Transaction>mappedList=balanceCheck.mapToModel();
		assert(mappedList.size()>0);
		assert(balanceCheck.scanReversalPayments(mappedList).size()>0);
		assert(balanceCheck.BalanceCoreLogic(mappedList)>0.0);
	}


}
