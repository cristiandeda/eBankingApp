package org.poo.cb.exchanges;

public class ExchangeFromEUR implements ExchangeStrategy {
    public double exchange(double amount, String destinationCurrency) {
        switch (destinationCurrency) {
            case "GBP":
                return amount * 0.88;
            case "JPY":
                return amount * 129.7;
            case "CAD":
                return amount * 1.47;
            case "USD":
                return amount * 1.59;
            case "EUR":
                return amount;
            default:
                throw new IllegalArgumentException("Unknown currency");
        }
    }
}
