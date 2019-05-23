package main.java.logic;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.exception.TransactionException;
import main.java.model.Transaction;

public class App {
	private static final Logger log = Logger.getLogger("App.class");

	public static void main(String[] args) throws IOException {
		Double balance = null;
		try {
			BalanceCheck fraud = new BalanceCheck();
			fraud.setFilePath("records.csv");
			List<Transaction> result = fraud.mapToModel();
			if (null != result && !(result.isEmpty()))
				balance = fraud.BalanceCoreLogic(result);
			if(balance!=null)
			{
				log.info("account balance"+balance);

			}
		} catch (TransactionException ex) {
			log.log(Level.SEVERE, "TransactionException in main class", ex);
		}

	}
}