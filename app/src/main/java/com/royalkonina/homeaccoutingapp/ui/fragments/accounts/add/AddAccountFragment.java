package com.royalkonina.homeaccoutingapp.ui.fragments.accounts.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.royalkonina.homeaccoutingapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class AddAccountFragment extends Fragment {

    private AddAccountViewModel viewModel;
    private Button addButton;
    private EditText editName;
    private EditText editDescription;
    private EditText editBalance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_add_account, container, false);
        viewModel = ViewModelProviders.of(this).get(AddAccountViewModel.class);
        addButton = view.findViewById(R.id.btn_add);
        editName = view.findViewById(R.id.edit_name);
        editDescription = view.findViewById(R.id.edit_description);
        editBalance = view.findViewById(R.id.edit_balance);

        addButton.setOnClickListener(v -> {
            boolean success = viewModel.addAccount(editName.getText().toString(), editDescription.getText().toString(), editBalance.getText().toString());
            Toast.makeText(getContext(), success ? "Successful" : "Some error", Toast.LENGTH_SHORT).show();
            if (success) {
                findNavController(this).navigateUp();
            }
        });
        return view;
    }
}
