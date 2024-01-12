package externalApiHealper;

import bean.Transaction;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiHelpers {
    private static String baseURL = "https://blockstream.info/api/";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getBlockHash(int blockNumber) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "block-height/" + blockNumber))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public static List<Transaction> findAllTransactions(String blockHash) throws JsonMappingException {
        int startIndex = 0;
        boolean isAllTransactionsCompleted = false;
        List<Transaction> transactions = new ArrayList<>();

        while(!isAllTransactionsCompleted) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseURL + "block/" + blockHash + "/txs/" + startIndex))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = null;
            startIndex+= 25;
            try {
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

                Transaction[] res = objectMapper.readValue(response.body(), Transaction[].class);
                transactions.addAll(List.of(res));
                System.out.println(transactions.size());
            } catch (Exception e) {
                System.out.println(e);
                isAllTransactionsCompleted = true;
            }

        }
        return transactions;
    }
}
