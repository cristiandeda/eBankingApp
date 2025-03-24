package org.poo.cb.exchanges;

public class ExchangeFromJPY implements ExchangeStrategy {
    public double exchange(double amount, String destinationCurrency) {
        switch (destinationCurrency) {
            case "GBP":
                return amount * 0.0068;
            case "JPY":
                return amount;
            case "CAD":
                return amount * 0.0113;
            case "EUR":
                return amount * 0.0077;
            case "USD":
                return amount * 0.0123;
            default:
                throw new IllegalArgumentException("Unknown currency");
        }
    }
}
