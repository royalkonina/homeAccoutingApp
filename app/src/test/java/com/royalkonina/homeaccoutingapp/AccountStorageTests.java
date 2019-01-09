package com.royalkonina.homeaccoutingapp;

import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.Operation;
import com.royalkonina.homeaccoutingapp.entities.OperationType;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AccountStorageTests {

    private AccountStorage accountStorage = AccountStorage.getInstance();
    private Account firstAcc = new Account(1, "first", "firstDesc", 100);
    private Account secondAcc = new Account(2, "second", "secondDesc", 200);
    private Account thirdAcc = new Account(3, "third", "thirdDesc", 300);

    @Before
    public void setup() {
        accountStorage.clear();
        accountStorage.addAccount(firstAcc);
        accountStorage.addAccount(secondAcc);
        accountStorage.addAccount(thirdAcc);
    }

    @Test
    public void operationAddTest() {
        Operation operation = new Operation(4,
                new Date(),
                OperationType.TRANSFER,
                50,
                1,
                2,
                "transfer 50");
        accountStorage.addOperation(operation);
        assertEquals(1, firstAcc.getOperations().size());
        assertEquals(1, secondAcc.getOperations().size());
        assertEquals(operation, firstAcc.getOperations().get(0));
        assertEquals(operation, secondAcc.getOperations().get(0));
        assertEquals(50, firstAcc.getBalance());
        assertEquals(250, secondAcc.getBalance());
    }

    @Test
    public void operationRemoveTest() {
        Operation operation = new Operation(4,
                new Date(),
                OperationType.TRANSFER,
                50,
                1,
                2,
                "transfer 50");
        accountStorage.addOperation(operation);
        accountStorage.removeOperation(operation);
        assertEquals(0, firstAcc.getOperations().size());
        assertEquals(0, secondAcc.getOperations().size());
        assertEquals(100, firstAcc.getBalance());
        assertEquals(200, secondAcc.getBalance());
    }

    @Test
    public void accountRemoveTest() {
        Operation operation = new Operation(4,
                new Date(),
                OperationType.TRANSFER,
                50,
                2,
                3,
                "transfer 50");
        accountStorage.addOperation(operation);
        accountStorage.removeAccount(thirdAcc.getId());
        assertEquals(2, accountStorage.getAccountsLiveData().getValue().size());
        assertEquals(1, firstAcc.getOperations().size());
        assertEquals(150, firstAcc.getBalance());
        assertEquals(150, secondAcc.getBalance());
    }
}