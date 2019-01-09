package com.royalkonina.homeaccoutingapp.model;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.Operation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AccountStorage {

    private static AccountStorage instance;

    private AccountStorage() {
        //empty
    }

    public static AccountStorage getInstance() {
        if (instance == null) {
            instance = new AccountStorage();
        }
        return instance;
    }

    private List<Account> accounts = new ArrayList<>();
    private MutableLiveData<List<Account>> accountsLiveData = new MutableLiveData<>();
    private MutableLiveData<Account> currentAccountLiveData = new MutableLiveData<>();

    public LiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        notifyAccountsChanged();
    }

    public LiveData<Account> getAccountLiveData(long id) {
        Account account = findAccountById(id);
        if (account != null) {
            currentAccountLiveData.setValue(account);
        }
        return currentAccountLiveData;
    }

    @Nullable
    public Account findAccountById(long accountId) {
        for (Account acc : accounts) {
            if (acc.getId() == accountId) {
                return acc;
            }
        }
        return null;
    }

    public void addOperation(Operation operation) {
        Account sourceAccount = findAccountById(operation.getSourceAccountId());
        if (sourceAccount != null) {
            sourceAccount.addOperation(operation);
        }
        Account destinationAccount = findAccountById(operation.getDestinationAccountId());
        if (destinationAccount != null) {
            destinationAccount.addOperation(operation);
        }
        notifyAccountsChanged();
    }

    private void notifyAccountsChanged() {
        accountsLiveData.setValue(accounts);
        if (currentAccountLiveData.getValue() != null) {
            Account account = findAccountById(currentAccountLiveData.getValue().getId());
            if (account != null) {
                currentAccountLiveData.setValue(account);
            }
        }
    }

    public void removeAccount(Long accountId) {
        Account account = findAccountById(accountId);
        Account anotherAccount = findAnotherAccount(accountId);
        if (account != null && anotherAccount != null) {
            for (Operation operation : account.getOperations()) {
                if (operation.getDestinationAccountId() == account.getId()) {
                    operation.setDestinationAccountId(anotherAccount.getId());
                }
                if (operation.getSourceAccountId() == account.getId()) {
                    operation.setSourceAccountId(anotherAccount.getId());
                }
                anotherAccount.addOperation(operation);
            }
        }
        accounts.remove(account);
        notifyAccountsChanged();
    }

    @Nullable
    private Account findAnotherAccount(long accountId) {
        for (Account acc : accounts) {
            if (acc.getId() != accountId) {
                return acc;
            }
        }
        return null;
    }

    public void removeOperation(Operation operation) {
        for (Account account : accounts) {
            account.removeOperation(operation);
        }
        notifyAccountsChanged();
    }

    @VisibleForTesting
    public void clear() {
        accounts.clear();
        notifyAccountsChanged();
    }
}
