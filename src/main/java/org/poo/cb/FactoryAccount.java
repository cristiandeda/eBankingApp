package org.poo.cb;

import org.poo.cb.accounts.*;

/* Implemented Account using Factory Design Pattern */

public class FactoryAccount {
    public Account addFactoryAccount(String currency) {
        switch(currency) {
            case "USD":
                return new USDAccount();
            case "EUR":
                return new EURAccount();
            case "GBP":
                return new GBPAccount();
            case "JPY":
                return new JPYAccount();
            case "CAD":
                return new CADAccount();
            default:
                throw new IllegalArgumentException("Unknown currency");
        }
    }
}
