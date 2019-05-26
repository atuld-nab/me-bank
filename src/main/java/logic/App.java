package main.java.logic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.exception.TransactionException;
import main.java.model.Transaction;

public class App {
	private static final Logger log = Logger.getLogger("App.class");

	public static void main(String[] args) throws IOException {
		Double balance = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		Scanner sc=new Scanner(System.in);
		System.out.println("enter account");
		String account = sc.nextLine();
		System.out.println("enter start date in this format :-dd/MM/yyyy HH:mm");
		String fromTimeStamp = sc.nextLine();
		System.out.println("enter end date in this format :-dd/MM/yyyy HH:mm");
		String toTimeStamp = sc.nextLine();
		BalanceCheck balanceCheck = new BalanceCheck();
		balanceCheck.setFilePath("records.csv");
		balanceCheck.setTimeStampStart(LocalDateTime.parse(fromTimeStamp,formatter));
		balanceCheck.setTimeStampEnd(LocalDateTime.parse(toTimeStamp,formatter));
		balanceCheck.setAccount(account);
		try {
			List<Transaction> result = balanceCheck.mapToModel();
			if (null != result && !(result.isEmpty())) {
				balance = balanceCheck.BalanceCoreLogic(result);
				if (balance != null) {
					log.info("account balance====>" +"("+ balance+")");

				}
			}
		} catch (TransactionException ex) {
			log.log(Level.SEVERE, "TransactionException in main class", ex);
		}
	}
}
