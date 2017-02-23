package com.example.andradejoseph.javaprimers.chapter1_7_classes;

/**
 * A simple model for a consumer credit card
 *
 * @author ANDRADEJOSEPH
 */

public class CreditCard {
    //instance variables
    private String mCustomer; //name of the customer, (e.g "Dominic ")
    private String mBank;     // name of the bank
    private String mAccount;  //account identifier
    private int mLimit;       //credit limit (in dollars)
    protected double mBalance; //current balance (in dollars)

    /**
     * Constructs a new credit card instance
     * @param customer  the name of the customer
     * @param bank      the name of the bank
     * @param account   the account identifier
     * @param limit     the credit limit
     * @param balance   the initial balance
     */
    public CreditCard(String customer, String bank, String account, int limit, double balance) {
        mCustomer = customer;
        mBank = bank;
        mAccount = account;
        mLimit = limit;
        mBalance = balance;
    }

    public CreditCard(String customer, String bank, String account, int limit) {
        this(customer, bank, account, limit, 0.0);
    }

    //Accessors Method
    public String getCustomer() {return  mCustomer;}
    public String getBank() {return  mBank;}
    public String getAccount() {return mAccount;}
    public int getLimit() {return mLimit;}
    public double getBalance() {return mBalance;}

    /**
     * Charges the given price to the card, assuming sufficient credit limit
     * @param price     the amount to be charged
     * @return true     if the charge was accepted;  false if charge was denied
     */
    public boolean charge(double price) {//make a charge
        if(price + mBalance > mLimit) {  //if charge would surpass limit
            return false;                //refuse to charge
        }
        //at this point, the charge is successful
        mBalance += price;              // update the balacne
        return true;                    //announce the good news
    }

    /**
     *  Processes customer payment that reduces balance
     * @param amount    the amount of payment made
     */
    public void makePayment(double amount) { //make a payment
        mBalance -= amount;
    }

    //Utility method to print a card's informatiuon
    public static void printSummary(CreditCard card) {
        System.out.println("Customer " + card.mCustomer);
        System.out.println("Bank " + card.mBank);
        System.out.println("Account " + card.mAccount);
        System.out.println("Balance " + card.mBalance); //implicit cast
        System.out.println("Limit " + card.mLimit); //implicit cast
    }

}
