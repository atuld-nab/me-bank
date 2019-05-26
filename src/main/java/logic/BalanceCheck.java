package main.java.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import main.java.exception.TransactionException;
import main.java.model.Transaction;

public class BalanceCheck {
	private static final Logger log = Logger.getLogger("BalanceCheck.class");
	private String account;
	private LocalDateTime timeStampStart;

	public LocalDateTime getTimeStampStart() {
		return timeStampStart;
	}

	public void setTimeStampStart(LocalDateTime timeStampStart) {
		this.timeStampStart = timeStampStart;
	}

	private LocalDateTime timeStampEnd;
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public LocalDateTime getTimeStampEnd() {
		return timeStampEnd;
	}

	public void setTimeStampEnd(LocalDateTime timeStampEnd) {
		this.timeStampEnd = timeStampEnd;
	}

	public Double BalanceCoreLogic(List<Transaction> tranList) throws TransactionException {
		log.info("start date"+this.timeStampStart+"endDate"+this.timeStampEnd);
		List<Transaction>candidate;
		
		Double balance=0.0;
		try{
			candidate=scanReversalPayments(tranList);
			//scan ids for same date range and remove from parent list//
			if(null!=candidate && candidate.size()>0)
			{
				log.info("size of reverse payment"+candidate.size());
				List<Transaction>traversal=tranList;
				List<String>candidateIds=candidate.parallelStream().
						map(e->e.getReversalTransactionId()).collect(Collectors.toList());
				if(!candidateIds.isEmpty())
				{
					log.info("size of candidate Ids for reversal"+candidateIds.size());
				}
				
				//assuming unique ids//
				for(String id:candidateIds)
				{
					List<Transaction>removeCandidate=traversal.stream().
							filter(account->account.getTransactionId().
									equalsIgnoreCase(id)&&
									account.getTimeStamp().
									isAfter(this.timeStampStart)&& 
									account.getTimeStamp().
									isBefore(this.timeStampEnd)).collect(Collectors.toList());
					
					if(null!=removeCandidate && removeCandidate.size()>0)
					{
						tranList.remove(removeCandidate.get(0));
					}
					
				}
				
				if(tranList.size()>0)
				
				log.info("size of filtered List post reversal"+tranList.size());
			}
			
			List<Transaction>totalTransaction = tranList.stream().
					filter(account->account.getCreditAccount().
							equalsIgnoreCase(this.account) ||account.getDebitAccount().
							equalsIgnoreCase(this.account) && account.getTransactionType().
							equalsIgnoreCase("PAYMENT") &&
							account.getTimeStamp().
							isAfter(this.timeStampStart)&& 
							account.getTimeStamp().
							isBefore(this.timeStampEnd)).collect(Collectors.toList());
			
			if(!totalTransaction.isEmpty())
			{
				log.info("size of total valid Transaction"+totalTransaction.size());
			}
		
				Double creditSum=tranList.stream().filter(
						account->account.getCreditAccount().
						equalsIgnoreCase(this.account)&&account.getTransactionType().
						equalsIgnoreCase("PAYMENT")&&account.getTimeStamp().
						isAfter(this.timeStampStart)&&account.getTimeStamp().isBefore(this.timeStampEnd)).
						mapToDouble(Transaction::getAmount).sum();
				
				Double debitSum=tranList.stream().filter(
						account->account.getDebitAccount().
						equalsIgnoreCase(this.account)&&account.getTransactionType().
						equalsIgnoreCase("PAYMENT")&&account.getTimeStamp().
						isAfter(this.timeStampStart)&&account.getTimeStamp().isBefore(this.timeStampEnd)).
						mapToDouble(Transaction::getAmount).sum();
				
				balance=creditSum-debitSum;
				log.info("total balance"+balance);
		}catch(Exception ex){
			log.log(Level.SEVERE,"TransactionException in BalanceCheck class",ex);
			throw new TransactionException("error in BalanceCheck core logic reason"+ex.getMessage());
			}
		return balance;

	}
	public List<Transaction>scanReversalPayments(List<Transaction>transList)throws TransactionException
	{
		// remove reverseIds//
		List<Transaction>candidate=transList.stream().
				filter(account->account!=null&&account.getTransactionType().equalsIgnoreCase("REVERSAL")).collect(Collectors.toList());
				if(candidate.size()>0)
				{
				log.info("size of reverse payment"+candidate.size());
				}
				
				return candidate;
				
				
		}
	public List<Transaction> mapToModel() throws TransactionException {
		log.info("Mapping csv file to model");
		InputStream is = null;
		List<Transaction> transList = new ArrayList<>();
		BufferedReader br = null;
		try {
			is = new FileInputStream(new File(this.filePath));
			br = new BufferedReader(new InputStreamReader(is));
			transList.addAll(br.lines().map(mapToTransaction).collect(Collectors.toList()));
		} catch (NullPointerException | FileNotFoundException ex) {
			log.log(Level.SEVERE, "TransactionException in BalanceCheck class", ex);
			throw new TransactionException("TransactionException in BalanceCheck class because" + ex.getMessage());

		} finally {
			try {
				if (null != is && null != br) {
					br.close();
				}
			} catch (IOException e) {
				log.log(Level.SEVERE, "TransactionException in BalanceCheck class finally block", e);
			}
		}
		return transList;
	}

	private static Function<String, Transaction> mapToTransaction = (line) -> {
		Transaction entry = null;
		String[] p = line.split(",");
		if (p != null && p.length > 0)
			entry = new Transaction(p[0], p[1], p[2], Double.parseDouble(p[3]), LocalDateTime.parse(p[4]), p[5], p[6]);
		return entry;
	};

}
