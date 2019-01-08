package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.royalkonina.homeaccoutingapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AccountsFragment extends Fragment {

    private AccountsViewModel viewModel;
    private AccountsAdapter adapter;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_accounts, container, false);
        viewModel = ViewModelProviders.of(this).get(AccountsViewModel.class);
        adapter = new AccountsAdapter();
        RecyclerView recyclerViewAccounts = view.findViewById(R.id.rv_accounts);
        recyclerViewAccounts.setAdapter(adapter);
        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getAccountsLiveData().observe(this, accounts -> {
            adapter.setData(accounts);
            adapter.notifyDataSetChanged();
        });
        fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> findNavController(this).navigate(R.id.addAccountFragment));
        return view;
    }
}
