package com.royalkonina.homeaccoutingapp.model;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.Operation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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

    public LiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        notifyAccountsChanged();
    }

    public LiveData<Account> getAccountLiveData(long id) {
        MutableLiveData<Account> liveData = new MutableLiveData<>();
        Account account = findAccountById(id);
        if (account != null) {
            liveData.setValue(account);
        }
        return liveData;
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
    }

    public void removeAccount(long accountId) {
        Account account = findAccountById(accountId);
        accounts.remove(account);
        notifyAccountsChanged();
    }
}
