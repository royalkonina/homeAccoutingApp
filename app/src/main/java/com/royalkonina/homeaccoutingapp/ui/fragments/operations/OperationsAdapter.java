package com.royalkonina.homeaccoutingapp.ui.fragments.operations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.royalkonina.homeaccoutingapp.R;
import com.royalkonina.homeaccoutingapp.entities.Account;
import com.royalkonina.homeaccoutingapp.entities.Operation;
import com.royalkonina.homeaccoutingapp.model.AccountStorage;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.OperationViewHolder> {

    private List<Operation> data = new ArrayList<>();

    @NonNull
    @Override
    public OperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operation, parent, false);
        return new OperationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class OperationViewHolder extends RecyclerView.ViewHolder {

        public OperationViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Operation operation) {
            TextView textDate = itemView.findViewById(R.id.tv_date);
            textDate.setText(operation.getDate().toString());

            TextView textType = itemView.findViewById(R.id.tv_type);
            textType.setText(String.valueOf(operation.getOperationType()));

            TextView textValueBalance = itemView.findViewById(R.id.tv_value_balance);
            textValueBalance.setText(String.valueOf(operation.getValue()));

            TextView textFromAccount = itemView.findViewById(R.id.tv_from_account);
            Account from = AccountStorage.getInstance().findAccountById(operation.getSourceAccountId());
            if (from != null) {
                textFromAccount.setText(from.getName());
            }

            TextView textToAccount = itemView.findViewById(R.id.tv_to_account);
            Account to = AccountStorage.getInstance().findAccountById(operation.getDestinationAccountId());
            if(to != null){
                textToAccount.setText(to.getName());
            }

            TextView textDescription = itemView.findViewById(R.id.tv_description);
            textDescription.setText(operation.getDescription());

            itemView.setOnLongClickListener(v -> {
                AccountStorage.getInstance().removeOperation(operation);
                return true;
            });

        }
    }

    public void setData(List<Operation> operations) {
        this.data = operations;
    }

}
