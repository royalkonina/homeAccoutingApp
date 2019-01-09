package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.info;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.Operation;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class AccountInfoViewModel extends ViewModel {

    private final MutableLiveData<Long> accountIdLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> filterLiveData = new MutableLiveData<>();
    private final LiveData<Account> accountLiveData = Transformations.switchMap(accountIdLiveData, id -> {
        return AccountStorage.getInstance().getAccountLiveData(id);
    });

    private final MediatorLiveData<List<Operation>> operationsLiveData = new MediatorLiveData<>();

    public void setAccountId(Long accountId) {
        if (!accountId.equals(accountIdLiveData.getValue())) {
            accountIdLiveData.setValue(accountId);
            operationsLiveData.removeSource(accountLiveData);
            operationsLiveData.removeSource(filterLiveData);
            operationsLiveData.addSource(accountLiveData, account -> {
                operationsLiveData.setValue(getFilteredOperations(account.getOperations(), filterLiveData.getValue()));
            });
            operationsLiveData.addSource(filterLiveData, filter -> {
                if (accountLiveData.getValue() != null) {
                    operationsLiveData.setValue(getFilteredOperations(accountLiveData.getValue().getOperations(), filter));
                }
            });
        }
    }

    private List<Operation> getFilteredOperations(@NonNull List<Operation> operations, @Nullable String filter) {
        if (filter == null || filter.isEmpty()) return operations;
        List<Operation> result = new ArrayList<>();
        for (Operation operation : operations) {
            if (operation.getDescription().contains(filter)) {
                result.add(operation);
            }
        }
        return result;
    }

    public LiveData<Account> getAccountLiveData() {
        return accountLiveData;
    }

    public LiveData<List<Operation>> getOperationsLiveData() {
        return operationsLiveData;
    }

    public void removeAccount() {
        AccountStorage.getInstance().removeAccount(accountIdLiveData.getValue());
    }

    public void setFilter(String filter) {
        filterLiveData.setValue(filter);
    }
}
