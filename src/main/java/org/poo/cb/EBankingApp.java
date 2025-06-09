package org.poo.cb;

import com.opencsv.exceptions.CsvException;
import org.poo.cb.accounts.Account;
import org.poo.cb.exchanges.*;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EBankingApp {
    private static EBankingApp instance;
    private HashMap<String, User> usersEmails;
    private ArrayList<Stocks> recommendList;

    private EBankingApp() {
        usersEmails = new HashMap<>();
        recommendList = new ArrayList<>();
    }

    public static EBankingApp getInstance() {
        if (instance == null) {
            instance = new EBankingApp();
        }
        return instance;
    }

    public void createUser(String email, String lastName, String firstName, String address) {
        if (usersEmails.containsKey(email)) {
            System.out.println("User with " + email + " already exists");
        } else {
            User user = new User(email, lastName, firstName, address);
            usersEmails.put(email, user);
        }
    }

    public void addFriend(String emailUser, String emailFriend) {
        if (!usersEmails.containsKey(emailUser)) {
            System.out.println("User with " + emailUser + " doesn't exist");
            return;
        }

        if (!usersEmails.containsKey(emailFriend)) {
            System.out.println("User with " + emailFriend + " doesn't exist");
            return;
        }

        User userToAddFriend = usersEmails.get(emailUser);
        User userToBeAddedAsFriend = usersEmails.get(emailFriend);

        if (userToAddFriend.getFriends().contains(userToBeAddedAsFriend)) {
            System.out.println("User with " + emailFriend + " is already a friend");
            return;
        }

        userToAddFriend.addFriend(userToBeAddedAsFriend);
        userToBeAddedAsFriend.addFriend(userToAddFriend);
    }

    public void addAccount(String email, String currency) {
        User userToAddAccount = usersEmails.get(email);

        for (Account account : userToAddAccount.getUserAccounts()) {
            if (account.getCurrencyType().equals(currency)) {
                System.out.println("Account in currency " + currency + " already exists for user");
            }
        }

        FactoryAccount factoryAccount = new FactoryAccount();
        userToAddAccount.addAccount(factoryAccount.addFactoryAccount(currency));
    }

    public void addMoney(String email, String currency, String stringAmount) {
        double amount = Double.parseDouble(stringAmount);
        User userToAddMoney = usersEmails.get(email);
        Account accountToAddMoney = getAccountByCurrency(userToAddMoney, currency);

        if (accountToAddMoney != null) {
            accountToAddMoney.addAmount(amount);
        }
    }

    public void exchangeMoney(String email, String sourceCurrency, String destinationCurrency, String stringAmount) {
        double destinationAmount = Double.parseDouble(stringAmount);
        User userToExchangeMoney = usersEmails.get(email);

        Account sourceAccount = getAccountByCurrency(userToExchangeMoney, sourceCurrency);
        if (sourceAccount == null) {
            System.out.println("Account in currency " + destinationCurrency + " doesn't exist");
            return;
        }

        Account destinationAccount = getAccountByCurrency(userToExchangeMoney, destinationCurrency);
        if (destinationAccount == null) {
            System.out.println("Account in currency " + destinationCurrency + " doesn't exist");
            return;
        }

        ExchangeContext exchangeContext;
        double sourceAmount;

        switch (destinationCurrency) {
            case "EUR":
                exchangeContext = new ExchangeContext(new ExchangeFromEUR());
                sourceAmount = exchangeContext.executeExchangeStrategy(destinationAmount, sourceCurrency);
                break;
            case "GBP":
                exchangeContext = new ExchangeContext(new ExchangeFromGBP());
                sourceAmount = exchangeContext.executeExchangeStrategy(destinationAmount, sourceCurrency);
                break;
            case "JPY":
                exchangeContext = new ExchangeContext(new ExchangeFromJPY());
                sourceAmount = exchangeContext.executeExchangeStrategy(destinationAmount, sourceCurrency);
                break;
            case "CAD":
                exchangeContext = new ExchangeContext(new ExchangeFromCAD());
                sourceAmount = exchangeContext.executeExchangeStrategy(destinationAmount, sourceCurrency);
                break;
            case "USD":
                exchangeContext = new ExchangeContext(new ExchangeFromUSD());
                sourceAmount = exchangeContext.executeExchangeStrategy(destinationAmount, sourceCurrency);
                break;
            default:
                throw new IllegalArgumentException("Unknown currency");
        }

        if (sourceAccount.getAmount() >= sourceAmount) {
            sourceAccount.removeAmount(sourceAmount);

            if (destinationAmount > 0.5 * sourceAccount.getAmount()) {
                sourceAccount.removeAmount(sourceAmount * 0.01);
            }

            destinationAccount.addAmount(destinationAmount);
        } else {
            System.out.println("Insufficient amount in account " + sourceCurrency + " for exchange");
        }
    }

    public void transferMoney(String email, String friendEmail, String currency, String stringAmount) {
        double amount = Double.parseDouble(stringAmount);
        User userToTransferMoney = usersEmails.get(email);

        Account accountToTransfer = getAccountByCurrency(userToTransferMoney, currency);
        if (accountToTransfer == null) {
            System.out.println("User with " + email + " doesn't exist");
            return;
        }

        if (accountToTransfer.getAmount() < amount) {
            System.out.println("Insufficient amount in account " + currency + " for transfer");
            return;
        }

        User userToReceiveMoney = usersEmails.get(friendEmail);
        if (!userToTransferMoney.getFriends().contains(userToReceiveMoney)) {
            System.out.println("You are not allowed to transfer money to " + friendEmail);
            return;
        }

        Account accountToReceive = getAccountByCurrency(userToReceiveMoney, currency);
        if (accountToReceive == null) {
            System.out.println("User with " + friendEmail + " doesn't exist");
            return;
        }

        accountToTransfer.removeAmount(amount);
        accountToReceive.addAmount(amount);
    }

    /* for buying stocks, the currency is always USD */
    public void buyStocks(String email, String company, String noOfStocks, String stockValuesFile) {
        String currency = "USD";
        int nrOfStocks = Integer.parseInt(noOfStocks);

        User userToBuyStocks = usersEmails.get(email);
        if (userToBuyStocks == null) {
            System.out.println("User with " + email + " doesn't exist");
            return;
        }

        Account accountToBuyStocks = getAccountByCurrency(userToBuyStocks, currency);
        if (accountToBuyStocks == null) {
            System.out.println("Account in currency " + currency + " doesn't exist");
            return;
        }

        double stockPrice;

        try {
            CSVReader reader = new CSVReader(new FileReader(stockValuesFile));
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                if (row[0].equals(company)) {
                    stockPrice = Double.parseDouble(row[row.length - 1]);
                    stockPrice *= nrOfStocks;

                    if (stockPrice <= accountToBuyStocks.getAmount()) {
                        accountToBuyStocks.removeAmount(stockPrice);
                        Stocks stocks = new Stocks(company, nrOfStocks);
                        userToBuyStocks.addStocks(stocks);
                    } else {
                        System.out.println("Insufficient amount in account for buying stock");
                    }
                }
            }

            reader.close();
        } catch (IOException | CsvException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void recommendStocks(String stockValuesFile) {
        try {
            CSVReader reader = new CSVReader(new FileReader(stockValuesFile));
            List<String[]> rows = reader.readAll();

            for (int i = 1; i < rows.size(); i++) {
                double shortTermAverage = 0;
                double longTermAverage = 0;

                for (int j = 1; j < rows.get(i).length; j++) {
                    longTermAverage += Double.parseDouble(rows.get(i)[j]);
                }

                for (int k = 6; k < rows.get(i).length; k++) {
                    shortTermAverage += Double.parseDouble(rows.get(i)[k]);
                }

                longTermAverage /= 10;
                shortTermAverage /= 5;

                if (shortTermAverage > longTermAverage) {
                    String company = rows.get(i)[0];
                    Stocks stockToRecommend = new Stocks(company);
                    recommendList.add(stockToRecommend);
                }
            }

            System.out.print("{\"stocksToBuy\":[");

            if (!recommendList.isEmpty()) {
                int i;
                for (i = 0; i < recommendList.size() - 1; i++) {
                    System.out.print("\"" + recommendList.get(i).getCompanyName() + "\",");
                }
                System.out.println("\"" + recommendList.get(i).getCompanyName() + "\"]}");
            }

            reader.close();
        } catch (IOException | CsvException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Account getAccountByCurrency(User user, String currency) {
        for (Account account : user.getUserAccounts()) {
            if (account.getCurrencyType().equals(currency)) {
                return account;
            }
        }
        return null;
    }

    public void listUser(String email) {
        User user = usersEmails.get(email);

        if (user != null) {
            System.out.print("{\"email\":\"" + user.getEmail() + "\",\"firstname\":\"" +
                    user.getFirstName() + "\",\"lastname\":\"" + user.getLastName() +
                    "\",\"address\":\"" + user.getAddress() + "\",\"friends\":[");

            if (!user.getFriends().isEmpty()) {
                int i;
                for (i = 0; i < user.getFriends().size() - 1; i++) {
                    System.out.print("\"" + user.getFriends().get(i).getEmail() + "\",");
                }
                System.out.print("\"" + user.getFriends().get(i).getEmail() + "\"");
            }

            System.out.println("]}");
        } else {
            System.out.println("User with " + email + " doesn't exist");
        }
    }

    public void listPortfolio(String email) {
        User user = usersEmails.get(email);

        if (user != null) {
            System.out.print("{\"stocks\":[");

            if (!user.getUserStocks().isEmpty()) {
                int i;
                for (i = 0; i < user.getUserStocks().size() - 1; i++) {
                    System.out.print("{\"stockName\":\"" + user.getUserStocks().get(i).getCompanyName() +
                            "\",\"amount\":" + user.getUserStocks().get(i).getNrOfStocks() + "},");
                }
                System.out.print("{\"stockName\":\"" + user.getUserStocks().get(i).getCompanyName() +
                        "\",\"amount\":" + user.getUserStocks().get(i).getNrOfStocks() + "}");
            }

            System.out.print("],\"accounts\":[");

            if (!user.getUserAccounts().isEmpty()) {
                int j;
                for (j = 0; j < user.getUserAccounts().size() - 1; j++) {
                    String twoDecimalsAmount = String.format("%.2f", user.getUserAccounts().get(j).getAmount());

                    System.out.print("{\"currencyName\":\"" + user.getUserAccounts().get(j).getCurrencyType() +
                            "\",\"amount\":\"" + twoDecimalsAmount + "\"},");
                }
                String twoDecimalsAmount = String.format("%.2f", user.getUserAccounts().get(j).getAmount());

                System.out.print("{\"currencyName\":\"" + user.getUserAccounts().get(j).getCurrencyType() +
                        "\",\"amount\":\"" + twoDecimalsAmount + "\"}");
            }

            System.out.println("]}");

        } else {
            System.out.println("User with " + email + " doesn't exist");
        }
    }

    public void cleanup() {
        if (!usersEmails.isEmpty()) {
            usersEmails.clear();
        }
        if (!recommendList.isEmpty()) {
            recommendList.clear();
        }
    }
}
