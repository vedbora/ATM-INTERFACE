package com.mycompany.javapractise;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Transaction {
    String type;
    double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return type + ": " + amount;
    }
}

class Account {
    private double balance;
    private final ArrayList<Transaction> transactionHistory;

    public Account() {
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds!");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawn: " + amount);
        }
    }

    public void transfer(Account receiver, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds for transfer!");
        } else {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
            System.out.println("Transferred: " + amount);
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction t : transactionHistory) {
                System.out.println(t);
            }
        }
    }

    public void showBalance() {
        System.out.println("Current Balance: " + balance);
    }

}

@SuppressWarnings("InfiniteLoopStatement")
class ATM {
    private final Scanner scanner;
    private final Map<String, String> users;
    private final Map<String, Account> accounts;

    public ATM() {
        scanner = new Scanner(System.in);
        users = new HashMap<>();
        accounts = new HashMap<>();

        users.put("vedant", "5565");
        users.put("sayali", "0202");
        users.put("chinmay", "7878");
        users.put("tanishq", "8718");

        for (String userId : users.keySet()) {
            accounts.put(userId, new Account());
        }
    }

    public void start() {
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.next();
            System.out.print("Enter PIN: ");
            String pin = scanner.next();

            if (authenticate(userId, pin)) {
                showMenu(accounts.get(userId));
            } else {
                System.out.println("Invalid credentials! Try again.");
            }
        }
    }

    private boolean authenticate(String userId, String pin) {
        return users.containsKey(userId) && users.get(userId).equals(pin);
    }

    private void showMenu(Account account) {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Show Balance");
            System.out.println("6. Quit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    account.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter receiver's user ID: ");
                    String receiverId = scanner.next();
                    if (accounts.containsKey(receiverId)) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        account.transfer(accounts.get(receiverId), transferAmount);
                    } else {
                        System.out.println("Invalid receiver ID!");
                    }
                    break;
                case 5:
                    account.showBalance();
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }

}
