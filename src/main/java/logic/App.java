package main.java.logic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.exception.TransactionException;
import main.java.model.Transaction;

public class App {
	private static final Logger log = Logger.getLogger("App.class");

	public static void main(String[] args) throws IOException {
		Double balance = null;
//		try {
		 	String fromTimeStamp="2017-02-03 10:00:30";
		 	String toTimeStamp="2017-02-04 10:00:30";
		 	System.out.println(LocalDateTime.parse(fromTimeStamp));
		 	System.out.println(LocalDateTime.parse(toTimeStamp));
			BalanceCheck fraud = new BalanceCheck();
//			fraud.setFilePath("records.csv");
//			fraud.setTimeStampStart(LocalDateTime.parse(start));
//			fraud.setTimeStampEnd(LocalDateTime.parse(end));
////			List<Transaction> result = fraud.mapToModel();
////			if (null != result && !(result.isEmpty()))
////				balance = fraud.BalanceCoreLogic(result);
////			if(balance!=null)
////			{
////				log.info("account balance"+balance);
////
//////			}
////		} catch (TransactionException ex) {
//////			log.log(Level.SEVERE, "TransactionException in main class", ex);
////		}
		}
	}
