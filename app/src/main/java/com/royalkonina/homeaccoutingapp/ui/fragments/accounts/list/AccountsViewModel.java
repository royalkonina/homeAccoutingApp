package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.list;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AccountsViewModel extends ViewModel {

    private LiveData<List<Account>> accountsLiveData = AccountStorage.getInstance().getAccountsLiveData();

    public LiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }
}
