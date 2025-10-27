package klx;

import java.util.Set;

@SuppressWarnings({"java:S1168"}) // not production code, just a demo
public class S7WhatIsJavaStream {

    public static void main(String[] args){
        var account1 = new Object(){
            Set<Transaction> getTransactions(){
                return null;
            }
        };

        double validBalance = account1.getTransactions().parallelStream()
                .filter(Transaction::isValid)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    static class Transaction{
        boolean isValid(){
            return true;
        }
        double getAmount(){
            return 0;
        }
    }
}
