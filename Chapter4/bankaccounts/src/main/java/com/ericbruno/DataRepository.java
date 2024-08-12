package com.ericbruno;

import com.ericbruno.bankaccountdata.Account;
import com.ericbruno.bankaccountdata.AccountType;
import com.ericbruno.bankaccountdata.Address;
import com.ericbruno.bankaccountdata.Balance;
import com.ericbruno.bankaccountdata.Customer;
import com.ericbruno.bankaccountdata.Entity;
import com.ericbruno.bankaccountdata.States;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ebruno
 */
public class DataRepository {
    private static DataRepository _INSTANCE = new DataRepository();
    public static DataRepository getInstance() {
        return _INSTANCE;
    }

    private AtomicInteger NEXT_ID = new AtomicInteger(1);

    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Transaction> transactions = new HashMap<>();

    private DataRepository() {

        // Start with some sample data:
        // A customer
        // Two accounts
        // A few transactions
        //
        Address address = new Address();
        address.setCity("Anytown");
        address.setState(States.NEW_YORK);
        address.setStreet("123 Main Street");
        address.setZip("10001");

        Customer customer = addCustomer(
                Boolean.TRUE,
                "Eric",
                "Bruno",
                "eric@ericbruno.com",
                address,
                "914-555-1234",
                "987-65-4321");

        Balance balance = new Balance(0.00, getTodaysDate() );

        Account account = addAccount(
                AccountType.BASIC_CHECKING,
                "MyChecking",
                customer,
                balance);

        Transaction transaction = createTransaction(
                account.getId(),
                TransactionType.CREDIT,
                1_000.00);

        System.out.println("Ready");
    }

    public Customer addCustomer(Boolean active,
            String firstName,
            String lastName,
            String email,
            Address address,
            String phone,
            String ssn) {
        Customer customer = new Customer();
        customer.setId(NEXT_ID.getAndIncrement() + "");
        customer.setActive(active);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setSsn(ssn);
        this.customers.put(customer.getId(), customer);
        return customer;
    }

    public Account addAccount(AccountType type,
            String name,
            Customer owner,
            Balance openingBalance) {
        Account account = new Account(
                NEXT_ID.getAndIncrement() + "",
                true,
                name,
                type,
                owner,
                openingBalance,
                openingBalance);

        this.accounts.put(account.getId(), account);
        return account;
    }

    public Transaction createTransaction(String accountId,
            TransactionType transType,
            double amount) {
        try {
            Account account = accounts.get(accountId);
            Customer customer = account.getOwner();
            if (customer.getActive() == true) {
                // Record bank transaction
                //
                Transaction transaction = new Transaction();
                transaction.setId(NEXT_ID.getAndIncrement() + "");
                transaction.setAccountId(accountId);
                transaction.setTransactionType(transType);
                transaction.setAmount(amount);
                transactions.put(transaction.getId(), transaction);
                
                // Reconcile account balance
                //
                Balance current = account.getAvailableBalance();
                if ( transaction.getTransactionType() == TransactionType.CREDIT ) {
                    current.setAmount( 
                        current.getAmount() + transaction.getAmount() );
                }
                else {
                    current.setAmount( 
                        current.getAmount() - transaction.getAmount() );
                }
                current.setAsOf( getTodaysDate() );
                account.setAvailableBalance(current);
                
                return transaction;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getTodaysDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String todaysdate = dateFormat.format(date);
        return todaysdate;
    }

    public List<Account> getAllAccounts() {
        List<Account> list = List.copyOf(accounts.values());
        return list;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = List.copyOf(customers.values());
        return list;
    }
    
    public List<Entity> getAllEntities() {
        List<Entity> entities = new ArrayList<>(); 
        entities.addAll(this.getAllAccounts());
        entities.addAll(this.getAllCustomers());
        return entities;
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public Customer getCustomer(String id) {
        return customers.get(id);
    }
    
    public Balance getBalance(String accountId) {
        return getAccount(accountId).getTotalBalance();
    }
    
    public Double getTotalDeposits(String customerId) {
        // Iterate through all the transactions for this customer
        // across their accounts and then add up all deposit transactions
        //
        /*
        List<Transaction> filtered = 
                transactions
                    .values()
                    .stream()
                    .filter(account -> getAccount(account.getAccountId()).getOwner().getId().equals(customerId) )
                    .toList();
        
        List<Transaction> deposits = 
                filtered
                    .stream()
                    .filter(transaction -> transaction.getTransactionType().equals(TransactionType.CREDIT))
                    .toList();
        */
        List<Account> accounts
                = getAllAccounts()
                        .stream()
                        .filter(account -> account.getOwner().getId().equals(customerId))
                        .toList();

        Double totalDeposits = 0D;
        for ( Account account: accounts ) {
            totalDeposits += account.getTotalBalance().getAmount();
        }
        
        return totalDeposits;
        
    }
}
