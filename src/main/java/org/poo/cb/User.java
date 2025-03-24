package org.poo.cb;

import org.poo.cb.accounts.Account;

import java.util.ArrayList;

public class User {
    private String email;
    private String lastName;
    private String firstName;
    private String address;
    private ArrayList<User> friends;
    private ArrayList<Stocks> userStocks;
    private ArrayList<Account> userAccounts;


    public User(String email, String lastName, String firstName, String address) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.friends = new ArrayList<>();
        this.userStocks = new ArrayList<>();
        this.userAccounts = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Stocks> getUserStocks() {
        return userStocks;
    }

    public void setUserStocks(ArrayList<Stocks> userStocks) {
        this.userStocks = userStocks;
    }

    public ArrayList<Account> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(ArrayList<Account> userAccounts) {
        this.userAccounts = userAccounts;
    }

    public void addFriend(User user) {
        this.friends.add(user);
    }

    public void addAccount(Account account) {
        this.userAccounts.add(account);
    }

    public void addStocks(Stocks stocks) {
        this.userStocks.add(stocks);
    }
}
