package org.poo.cb.exchanges;

public class ExchangeFromUSD implements ExchangeStrategy {
    public double exchange(double amount, String destinationCurrency) {
        switch (destinationCurrency) {
            case "GBP":
                return amount * 0.55;
            case "JPY":
                return amount * 81.3;
            case "CAD":
                return amount * 0.92;
            case "EUR":
                return amount * 0.91;
            case "USD":
                return amount;
            default:
                throw new IllegalArgumentException("Unknown currency");
        }
    }
}
