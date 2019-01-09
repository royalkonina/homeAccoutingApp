package com.royalkonina.homeaccoutingapp.ui.fragments.operations.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.royalkonina.homeaccoutingapp.R;
import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.OperationType;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AddOperationFragment extends Fragment {

    private AddOperationViewModel viewModel;
    private Button addButton;
    private Spinner spinnerOperation;
    private Spinner spinnerSource;
    private Spinner spinnerDestination;
    private View layoutSource;
    private View layoutDestination;
    private EditText editDescription;
    private EditText editBalance;

    private OperationType operationType = null;
    private long sourceAccountId = -1;
    private long destinationAccountId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_add_operation, container, false);
        viewModel = ViewModelProviders.of(this).get(AddOperationViewModel.class);
        addButton = view.findViewById(R.id.btn_add);

        spinnerOperation = view.findViewById(R.id.spinner_operation_type);
        spinnerSource = view.findViewById(R.id.spinner_source_account);
        spinnerDestination = view.findViewById(R.id.spinner_destination_account);

        layoutDestination = view.findViewById(R.id.layout_destination);
        layoutSource = view.findViewById(R.id.layout_source);

        editDescription = view.findViewById(R.id.edit_description);
        editBalance = view.findViewById(R.id.edit_balance);

        viewModel.getOperationTypeLiveData().observe(this, operationTypes -> {
            ArrayAdapter<OperationType> operationTypeArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(AddOperationFragment.this.getContext()),
                    android.R.layout.simple_spinner_item,
                    operationTypes);
            spinnerOperation.setAdapter(operationTypeArrayAdapter);
            spinnerOperation.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    operationType = operationTypes[position];
                    switch (operationType) {
                        case TRANSFER:
                            layoutDestination.setVisibility(View.VISIBLE);
                            layoutSource.setVisibility(View.VISIBLE);
                            break;
                        case CONSUMPTION:
                            layoutSource.setVisibility(View.VISIBLE);
                            layoutDestination.setVisibility(View.GONE);
                            break;
                        case INCOMING:
                            layoutSource.setVisibility(View.GONE);
                            layoutDestination.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // do nothing
                }
            });
        });

        viewModel.getAccountsLiveData().observe(this, accountList -> {
            String[] accountNames = getAccountNames(accountList);
            ArrayAdapter<String> sourceAccountArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(AddOperationFragment.this.getContext()),
                    android.R.layout.simple_spinner_item,
                    accountNames);
            spinnerSource.setAdapter(sourceAccountArrayAdapter);
            spinnerSource.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sourceAccountId = accountList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
                }
            });

            ArrayAdapter<String> destinationAccountArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(AddOperationFragment.this.getContext()),
                    android.R.layout.simple_spinner_item,
                    accountNames);
            spinnerDestination.setAdapter(destinationAccountArrayAdapter);
            spinnerDestination.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    destinationAccountId = accountList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //do nothing
                }
            });
        });

        addButton.setOnClickListener(v -> {
            String value = editBalance.getText().toString();
            String description = editDescription.getText().toString();
            switch (operationType) {
                case CONSUMPTION:
                    destinationAccountId = -1;
                    break;
                case INCOMING:
                    sourceAccountId = -1;
                    break;
            }
            boolean success = viewModel.addOperation(operationType, value, sourceAccountId, destinationAccountId, description);
            Toast.makeText(getContext(), success ? "Successful" : "Some error", Toast.LENGTH_SHORT).show();
            if (success) {
                findNavController(this).navigateUp();
            }
        });
        return view;
    }

    private String[] getAccountNames(List<Account> accountList) {
        String[] accountNames = new String[accountList.size()];
        int index = 0;
        for (Account account : accountList) {
            accountNames[index++] = account.getName();
        }
        return accountNames;
    }
}
