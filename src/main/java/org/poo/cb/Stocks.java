package org.poo.cb;

import java.util.HashMap;

public class Stocks {
    private String companyName;
    private int nrOfStocks;
    private HashMap<String, Double> lastTenDaysValues;

    public Stocks(String companyName) {
        this.companyName = companyName;
        this.lastTenDaysValues = new HashMap<>();
    }

    public Stocks(String companyName, int nrOfStocks) {
        this.companyName = companyName;
        this.nrOfStocks = nrOfStocks;
        this.lastTenDaysValues = new HashMap<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNrOfStocks() {
        return nrOfStocks;
    }

    public void setNrOfStocks(int nrOfStocks) {
        this.nrOfStocks = nrOfStocks;
    }

    public HashMap<String, Double> getLastTenDaysValues() {
        return lastTenDaysValues;
    }

    public void setLastTenDaysValues(HashMap<String, Double> lastTenDaysValues) {
        this.lastTenDaysValues = lastTenDaysValues;
    }

    public void increaseNrOfStocks(int nrOfStocks) {
        this.nrOfStocks += nrOfStocks;
    }
}
