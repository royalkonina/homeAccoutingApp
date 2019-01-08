package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.royalkonina.homeaccoutingapp.R;
import com.royalkonina.homeaccoutingapp.entities.Account;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.navigation.Navigation.findNavController;
import static com.royalkonina.homeaccoutingapp.ui.fragments.accounts.info.AccountInfoFragment.ACCOUNT_KEY;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {

    private List<Account> data = new ArrayList<>();

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Account account) {
            TextView textName = itemView.findViewById(R.id.tv_name);
            textName.setText(account.getName());
            TextView textDescription = itemView.findViewById(R.id.tv_description);
            textDescription.setText(account.getDescription());
            TextView textBalance = itemView.findViewById(R.id.tv_balance);
            textBalance.setText(String.valueOf(account.getBalance()));
            itemView.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();
                        bundle.putLong(ACCOUNT_KEY, account.getId());
                        findNavController(itemView).navigate(R.id.accountInfoFragment, bundle);
                    }
            );
        }
    }

    public void setData(List<Account> accounts) {
        this.data = accounts;
    }

}
