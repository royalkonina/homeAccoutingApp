package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.add;

import android.util.Log;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;
import com.royalkonina.homeaccoutingapp.model.IdGenerator;

import androidx.lifecycle.ViewModel;

public class AddAccountViewModel extends ViewModel {

    public boolean addAccount(String name, String description, String balance) {
        try {
            AccountStorage.getInstance().addAccount(new Account(IdGenerator.generateId(), name, description, Long.parseLong(balance)));
            return true;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.toString());
        }
        return false;
    }
}
