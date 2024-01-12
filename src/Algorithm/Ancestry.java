package Algorithm;

import bean.Result;
import bean.Transaction;
import bean.Vin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Ancestry {
    Map<String, Set<String>> tree;

    Queue<Result> priorityQueue = new PriorityQueue<>((o1, o2) -> {
        if (o1.getCount() > o2.getCount())
            return -1;
        if (o1 == o2)
            return 0;
        return 1;
    });
    public Ancestry(List<Transaction> transactionList) {
        tree = new HashMap<>();
        for(Transaction transaction : transactionList) {
            if (!tree.containsKey(transaction.getTxid())) {
                tree.put(transaction.getTxid(), new HashSet<>());
            }
            List<String> list = new ArrayList<>();
            for(Vin tr : transaction.getVin())
                list.add(tr.getTxid());
            tree.get(transaction.getTxid()).addAll(list);
        }
    }

    public void computeAllTransactionHistory() {
        for (Map.Entry<String,Set<String>> entry : tree.entrySet()) {
            Queue<String> q = new LinkedList<>();
            if (Objects.isNull(entry.getKey())) continue;
            q.add(entry.getKey());
            Integer count = 0;
            while(!q.isEmpty()) {
                String top = q.peek();
                q.poll();
                if (tree.containsKey(top)) {
                    for (String an : tree.get(top)) {
                        if (tree.containsKey(an)) {
                            q.add(an);
                            count++;
                        }
                    }
                }
            }
            priorityQueue.add(new Result(entry.getKey(), count));
        }
    }

    public void printTopK(int k) {
        while(!priorityQueue.isEmpty() && k > 0) {
            System.out.println(priorityQueue.peek().getTxId()  +  " " + priorityQueue.peek().getCount());
            priorityQueue.poll();
            k--;
        }
    }
}
