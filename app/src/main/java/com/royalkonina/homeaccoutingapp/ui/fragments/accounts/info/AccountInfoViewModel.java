package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.info;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AccountInfoViewModel extends ViewModel {

    private LiveData<Account> accountLiveData;
    private long accountId;

    public void setAccountId(long accountId) {
        if (accountId != this.accountId) {
            accountLiveData = AccountStorage.getInstance().getAccountLiveData(accountId);
            this.accountId = accountId;
        }
    }

    public LiveData<Account> getAccountLiveData() {
        return accountLiveData;
    }

    public void removeAccount() {
        AccountStorage.getInstance().removeAccount(accountId);
    }
}
