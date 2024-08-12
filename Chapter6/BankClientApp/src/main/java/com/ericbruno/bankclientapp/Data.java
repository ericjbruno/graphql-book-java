/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ericbruno.bankclientapp;

import com.ericbruno.bankaccountdata.Account;
import com.ericbruno.bankaccountdata.AccountInput;
import com.ericbruno.bankaccountdata.AccountType;
import com.ericbruno.bankaccountdata.Balance;
import com.ericbruno.bankaccountdata.Customer;
import com.ericbruno.bankaccountdata.Entity;
import com.ericbruno.bankaccountdata.MutationResolver;
import com.ericbruno.bankaccountdata.QueryResolver;
import com.ericbruno.bankaccountdata.States;
import java.util.List;

/**
 *
 * @author ebruno
 */
public class Data implements QueryResolver, MutationResolver {
    private Customer customer;
    private List<Customer> customers;
    private Account account;
    private List<Account> accounts;
    private String addCustomer;
    private String addAccount;
    private String id;
    private String data;

    public Data() {
    }
    
    @Override
    public String addAccount(AccountInput accountInput) throws Exception {
        return account.getId();
    }

    @Override
    public String addCustomer(String lastName, String firstName, String email, String street, String city, States state, String zip, String phone, String ssn) throws Exception {
        return customer.getId();
    }

    @Override
    public List<Customer> customers() throws Exception {
        return this.getCustomers();
    }

    @Override
    public Customer customer(String customerId) throws Exception {
        return this.getCustomer();
    }

    @Override
    public List<Account> accounts() throws Exception {
        return this.getAccounts();
    }

    @Override
    public Account account(String accountId) throws Exception {
        return this.getAccount();
    }

    @Override
    public List<Entity> entities() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public List<Account> accountsByCustomer(String customerId) throws Exception {
        return this.getAccounts();
    }

    @Override
    public List<Account> accountsByType(AccountType type, String customerId) throws Exception {
        return this.getAccounts();
    }

    @Override
    public Balance balance(String accountId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Double totalDeposits(String customerId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Customer getCustomer() throws Exception {
        return customer;
    }

    public Account getAccount() throws Exception {
        return account;
    }
    
    public List<Customer> getCustomers() throws Exception {
        return customers;
    }
    
    public List<Account> getAccounts() throws Exception {
        return accounts;
    }
    
    public String getId() throws Exception {
        return id;
    }

    public String getAddCustomer() throws Exception {
        return addCustomer;
    }

    public String getAddAccount() throws Exception {
        return addAccount;
    }

    @Override
    public Boolean addTransaction(String accountId, Double amount) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
