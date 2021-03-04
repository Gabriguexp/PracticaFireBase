package com.example.practicafirebase.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practicafirebase.R;
import com.example.practicafirebase.model.Repository;
import com.example.practicafirebase.viewmodel.ViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;


public class LoginFragment extends Fragment {

    ViewModel viewModel;
    NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        viewModel.logOut();
        navController = Navigation.findNavController(getView());
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final Button loginButton = view.findViewById(R.id.login);
        final Button registerButton = view.findViewById(R.id.registerbt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                login(email, pass);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                if(!checkData(email,pass)){
                    Toast.makeText(getContext(), "Alguno de los campos esta vacio.", Toast.LENGTH_LONG).show();
                } else{
                    viewModel.registro(email,pass);
                }
            }
        });
    }

    private void login(String email, String pass) {

        if(!checkData(email,pass)){
            Toast.makeText(getContext(), "Alguno de los campos esta vacio.", Toast.LENGTH_LONG).show();
        } else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    viewModel.login(email, pass, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult result) {
                            navController.navigate(R.id.action_loginFragment_to_listadoFragment);
                            viewModel.setCurrentuser(result.getUser());
                            ViewModel.currentUser = result.getUser();
                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Login fallido", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).start();
        }
    }

    public boolean checkData(String email, String pass){
        if(email.isEmpty()){
            return false;
        } else if(pass.isEmpty()){
            return false;
        }

        return true;
    }
}