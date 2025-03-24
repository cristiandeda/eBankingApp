package org.poo.cb.accounts;

public abstract class Account {
    public abstract double getAmount();
    public abstract void addAmount(double amount);
    public abstract void removeAmount(double amount);
    public abstract String getCurrencyType();
}
