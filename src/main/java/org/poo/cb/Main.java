package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* Design Patterns: Singleton / Factory / Strategy / Facade */

public class Main {

    private static void executeCommand(String commandAux, EBankingAppFacade eBankingAppFacade, String stockValuesFile) {
        String[] commandArgs = commandAux.split(" ");
        String command = null;

        if (commandArgs.length > 1) {
            command = commandArgs[0] + ' ' + commandArgs[1];
        }

        if (command != null) {
            switch (command) {
                case "CREATE USER":
                    String userAddress = commandArgs[5] + " " + commandArgs[6] +
                            " " + commandArgs[7] + " " + commandArgs[8] + " " +
                            commandArgs[9] + " " + commandArgs[10];
                    eBankingAppFacade.createUser(commandArgs[2], commandArgs[4], commandArgs[3], userAddress);
                    break;
                case "ADD FRIEND":
                    eBankingAppFacade.addFriend(commandArgs[2], commandArgs[3]);
                    break;
                case "ADD ACCOUNT":
                    eBankingAppFacade.addAccount(commandArgs[2], commandArgs[3]);
                    break;
                case "ADD MONEY":
                    eBankingAppFacade.addMoney(commandArgs[2], commandArgs[3], commandArgs[4]);
                    break;
                case "EXCHANGE MONEY":
                    eBankingAppFacade.exchangeMoney(commandArgs[2], commandArgs[3], commandArgs[4], commandArgs[5]);
                    break;
                case "TRANSFER MONEY":
                    eBankingAppFacade.transferMoney(commandArgs[2], commandArgs[3], commandArgs[4], commandArgs[5]);
                    break;
                case "BUY STOCKS":
                    eBankingAppFacade.buyStocks(commandArgs[2], commandArgs[3], commandArgs[4], stockValuesFile);
                    break;
                case "RECOMMEND STOCKS":
                    eBankingAppFacade.recommendStocks(stockValuesFile);
                    break;
                case "LIST USER":
                    eBankingAppFacade.listUser(commandArgs[2]);
                    break;
                case "LIST PORTFOLIO":
                    eBankingAppFacade.listPortfolio(commandArgs[2]);
                    break;
                default:
                    System.out.println("[ERROR]: Invalid Command!");
            }
        }
    }
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Running Main");
        } else {
            EBankingAppFacade eBankingAppFacade = new EBankingAppFacade();

            String inputCommandsFile = "src/main/resources/" + args[2];
            String stockValuesFile = "src/main/resources/" + args[1];

            try {
                BufferedReader br = new BufferedReader(new FileReader(inputCommandsFile));
                String command;

                while ((command = br.readLine()) != null) {
                    executeCommand(command, eBankingAppFacade, stockValuesFile);
                }

                eBankingAppFacade.cleanup();
                br.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}