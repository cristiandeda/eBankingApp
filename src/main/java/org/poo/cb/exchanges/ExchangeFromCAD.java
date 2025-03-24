package org.poo.cb.exchanges;

public class ExchangeFromCAD implements ExchangeStrategy {
    public double exchange(double amount, String destinationCurrency) {
        switch (destinationCurrency) {
            case "GBP":
                return amount * 0.6;
            case "JPY":
                return amount * 88.5;
            case "CAD":
                return amount;
            case "EUR":
                return amount * 0.68;
            case "USD":
                return amount * 1.09;
            default:
                throw new IllegalArgumentException("Unknown currency");
        }
    }
}
