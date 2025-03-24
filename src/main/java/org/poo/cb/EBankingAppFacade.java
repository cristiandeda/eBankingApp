package org.poo.cb;

public class EBankingAppFacade {
    private EBankingApp eBankingApp;

    public EBankingAppFacade() {
        this.eBankingApp = EBankingApp.getInstance();
    }

    public void createUser(String email, String lastName, String firstName, String address) {
        eBankingApp.createUser(email, lastName, firstName, address);
    }

    public void addFriend(String emailUser, String emailFriend) {
        eBankingApp.addFriend(emailUser, emailFriend);
    }

    public void addAccount(String email, String currency) {
        eBankingApp.addAccount(email, currency);
    }

    public void addMoney(String email, String currency, String stringAmount) {
        eBankingApp.addMoney(email, currency, stringAmount);
    }

    public void exchangeMoney(String email, String sourceCurrency, String destinationCurrency, String stringAmount) {
        eBankingApp.exchangeMoney(email, sourceCurrency, destinationCurrency, stringAmount);
    }

    public void transferMoney(String email, String friendEmail, String currency, String stringAmount) {
        eBankingApp.transferMoney(email, friendEmail, currency, stringAmount);
    }

    public void buyStocks(String email, String company, String noOfStocks, String stockValuesFile) {
        eBankingApp.buyStocks(email, company, noOfStocks, stockValuesFile);
    }

    public void recommendStocks(String stockValuesFile) {
        eBankingApp.recommendStocks(stockValuesFile);
    }

    public void listUser(String email) {
        eBankingApp.listUser(email);
    }

    public void listPortfolio(String email) {
        eBankingApp.listPortfolio(email);
    }

    public void cleanup() {
        eBankingApp.cleanup();
    }
}
