package com.ericbruno;

import com.ericbruno.bankaccountdata.Account;
import com.ericbruno.bankaccountdata.AccountType;
import com.ericbruno.bankaccountdata.Address;
import com.ericbruno.bankaccountdata.Balance;
import com.ericbruno.bankaccountdata.BalanceUpdate;
import com.ericbruno.bankaccountdata.Customer;
import com.ericbruno.bankaccountdata.Entity;
import com.ericbruno.bankaccountdata.States;
import graphql.execution.reactive.SubscriptionPublisher;
import io.micronaut.serde.annotation.SerdeImport;
import io.micronaut.serde.annotation.Serdeable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import java.util.function.Consumer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SerdeImport(SubscriptionPublisher.class)
@SerdeImport(Flux.class)

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

    // The list of subscriber queues for a single bank account
    class NewBalanceUpdates {
        ArrayList<BlockingQueue<BalanceUpdate>> queues =
                new ArrayList<>();
    }

    // The map of bank accounts and their subscriber queues
    HashMap<String, NewBalanceUpdates> newBalanceUpdates = 
            new HashMap<>();

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

    public Account addAccount(  AccountType type,
                                String name,
                                Customer owner,
                                Balance openingBalance ) {

        Account account = new Account(
                NEXT_ID.getAndIncrement() + "",
                true,
                name,
                type,
                owner,
                openingBalance,
                openingBalance);

        accounts.put(account.getId(), account);

        // Create a list to track subscriber queues for this account
        // and add it to the map of accounts
        //
        NewBalanceUpdates newBalanceStream = 
                new NewBalanceUpdates();
        newBalanceUpdates.put(
                account.getId(), newBalanceStream);
        
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
                
                // Create a subscription update with the new balance
                //
                BalanceUpdate update = new BalanceUpdate();
                update.setBalance(current.getAmount());

                // Trigger all active subscriptions with the new balance
                //
                NewBalanceUpdates data = 
                        newBalanceUpdates.get(accountId);
                for ( BlockingQueue<BalanceUpdate> queue: data.queues ) {
                    // Add the update to the subscriber queue, which
                    // will unblock the Flux listener to send the
                    // new balance update to the GraphQL subscriber
                    //
                    queue.add( update );
                }
                
                return transaction;
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getTodaysDate() {
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
    
    public Publisher<BalanceUpdate> createNewBalanceStream(String accountId) {
        // Get list of subscribers for this account
        //
        NewBalanceUpdates newBalanceQueues = 
                newBalanceUpdates.get(accountId);

        // Create a new update queue add it to this account's queues
        BlockingQueue<BalanceUpdate> newBalanceQueue = 
                new ArrayBlockingQueue<>(100);
        newBalanceQueues.queues.add(newBalanceQueue);

        // Create the Flux stream that will block on this queue
        // and deliver events to the subscriber as they arrive
        //
        @Serdeable.Serializable
        Flux<BalanceUpdate> stream = 
            Flux.create((Consumer<FluxSink<BalanceUpdate>>) sink -> {
                while (!sink.isCancelled()) {
                    try {
                        sink.next( newBalanceQueue.take() );
                    } catch (Exception e) {
                        sink.error(e);
                    }
                }
                System.out.println("Removing closed subscriber");
                sink.complete();
                newBalanceQueues.queues.remove(newBalanceQueue);
            }, FluxSink.OverflowStrategy.BUFFER).share();    


        return stream;
    }
}
