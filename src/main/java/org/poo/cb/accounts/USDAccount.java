package org.poo.cb.accounts;

public class USDAccount extends Account {
    private String currencyType;
    private double amount;

    public USDAccount() {
        this.currencyType = "USD";
    }

    public double getAmount() {
        return this.amount;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public String getCurrencyType() {
        return this.currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void removeAmount(double amount) {
        this.amount -= amount;
    }
}
