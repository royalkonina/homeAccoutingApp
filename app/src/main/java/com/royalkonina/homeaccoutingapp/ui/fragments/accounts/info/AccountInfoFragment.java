package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.info;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.royalkonina.homeaccoutingapp.R;
import com.royalkonina.homeaccoutingapp.ui.fragments.operations.OperationsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AccountInfoFragment extends Fragment {

    public final static String ACCOUNT_KEY = "ACCOUNT_KEY";

    private AccountInfoViewModel viewModel;
    private TextView textName;
    private TextView textDescription;
    private TextView textBalance;
    private OperationsAdapter adapter;
    private Button buttonAddOperation;
    private Button buttonRemoveAccount;
    private EditText editSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_info_account, container, false);
        viewModel = ViewModelProviders.of(this).get(AccountInfoViewModel.class);
        textName = view.findViewById(R.id.tv_name);
        textDescription = view.findViewById(R.id.tv_description);
        textBalance = view.findViewById(R.id.tv_balance);
        buttonAddOperation = view.findViewById(R.id.btn_add_operation);
        buttonRemoveAccount = view.findViewById(R.id.btn_remove_account);
        editSearch = view.findViewById(R.id.edit_search);

        long accountId = getArguments().getLong(ACCOUNT_KEY);
        viewModel.setAccountId(accountId);

        adapter = new OperationsAdapter();
        RecyclerView recyclerViewOperations = view.findViewById(R.id.rv_operations);
        recyclerViewOperations.setAdapter(adapter);
        recyclerViewOperations.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAccountLiveData().observe(this, account -> {
            textName.setText(account.getName());
            textDescription.setText(account.getDescription());
            textBalance.setText(String.valueOf(account.getBalance()));
        });

        viewModel.getOperationsLiveData().observe(this, operations -> {
            adapter.setData(operations);
            adapter.notifyDataSetChanged();
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setFilter(s.toString());
            }
        });

        buttonAddOperation.setOnClickListener(v -> {
            findNavController(this).navigate(R.id.addOperationFragment);
        });
        buttonRemoveAccount.setOnClickListener(v -> {
            findNavController(this).navigateUp();
            viewModel.removeAccount();
        });

        return view;
    }
}
