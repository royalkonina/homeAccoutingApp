package com.royalkonina.homeaccoutingapp.ui.fragments.operations.add;

import android.util.Log;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.Operation;
import com.royalkonina.homeaccoutingapp.entities.OperationType;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;
import com.royalkonina.homeaccoutingapp.model.IdGenerator;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddOperationViewModel extends ViewModel {

    private LiveData<List<Account>> accountsLiveData = AccountStorage.getInstance().getAccountsLiveData();
    private MutableLiveData<OperationType[]> operationTypeLiveData = new MutableLiveData<>();

    public AddOperationViewModel() {
        operationTypeLiveData.setValue(OperationType.values());
    }


    public LiveData<List<Account>> getAccountsLiveData() {
        return accountsLiveData;
    }

    public LiveData<OperationType[]> getOperationTypeLiveData() {
        return operationTypeLiveData;
    }

    public boolean addOperation(OperationType operationType,
                                String value,
                                long sourceAccountId,
                                long destinationAccountId,
                                String description) {
        try {
            if (sourceAccountId == destinationAccountId) return false;
            Operation operation = new Operation(IdGenerator.generateId(),
                    new Date(),
                    operationType,
                    Long.parseLong(value),
                    sourceAccountId,
                    destinationAccountId,
                    description);
            AccountStorage.getInstance().addOperation(operation);
            return true;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.toString());
        }
        return false;
    }
}
