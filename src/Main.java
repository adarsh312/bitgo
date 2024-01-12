import Algorithm.Ancestry;
import bean.Transaction;
import externalApiHealper.ApiHelpers;
import service.TransactionData;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ApiHelpers ap = new ApiHelpers();
        String hashValue = ap.getBlockHash(680000);
        System.out.println("TheHashValue Is " + hashValue);
//        List<Transaction> transactions = ap.findAllTransactions(hashValue);
//        File fileWriter = new File("transactions.json");
//        fileHandler.writeAsJson(transactions, fileWriter);
//        File fileReader = new File("transactions.json");
//        List<Transaction> transactionList = fileHandler.readAsJson(fileReader);
        TransactionData transactionData = new TransactionData(hashValue);
        List<Transaction> transactionList = transactionData.getTransactions();
        Ancestry ancestry = new Ancestry(transactionList);
        ancestry.computeAllTransactionHistory();
        ancestry.printTopK(10);
    }
}