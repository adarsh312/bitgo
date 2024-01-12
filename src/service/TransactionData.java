package service;

import bean.Transaction;
import externalApiHealper.ApiHelpers;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TransactionData {
    static final String path = ".json";
    String hash;
    File file;

    public TransactionData(String hash) {
        this.hash = hash;
        file = new File(hash + path);
    }

    public List<Transaction> getTransactions() throws IOException {
        if (!file.exists()) {
            System.out.println("Local Dump Not Present calling Remote");
            FileHandler.writeAsJson(ApiHelpers.findAllTransactions(this.hash), file);
        }
        return FileHandler.readAsJson(file);
    }
}
