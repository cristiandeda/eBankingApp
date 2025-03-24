package org.poo.cb;

import org.poo.cb.exchanges.*;

public class ExchangeContext {
    private ExchangeStrategy exchangeStrategy;

    public ExchangeContext(ExchangeStrategy exchangeStrategy) {
        this.exchangeStrategy = exchangeStrategy;
    }

    public double executeExchangeStrategy(double amount, String currency) {
        return exchangeStrategy.exchange(amount, currency);
    }
}
