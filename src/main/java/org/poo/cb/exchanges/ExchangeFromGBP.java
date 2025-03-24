package org.poo.cb.exchanges;

public class ExchangeFromGBP implements ExchangeStrategy {
    public double exchange(double amount, String destinationCurrency) {
        switch (destinationCurrency) {
            case "GBP":
                return amount;
            case "JPY":
                return amount * 147.4;
            case "CAD":
                return amount * 1.67;
            case "EUR":
                return amount * 1.14;
            case "USD":
                return amount * 1.81;
            default:
                throw new IllegalArgumentException("Unknown currency");
        }
    }
}
