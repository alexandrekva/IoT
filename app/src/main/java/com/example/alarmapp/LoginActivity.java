package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.alarmapp.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            String user = getUser();
            String password = getPassword();

            if (authenticateUser(user, password)) {
                navigate();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Usuário/Senha inválida!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

    }

    private String getUser() {
        return Objects.requireNonNull(binding.editTextLogin.getText()).toString();
    }

    private String getPassword() {
        return Objects.requireNonNull(binding.editTextSenha.getText()).toString();
    }

    private boolean authenticateUser(String user, String password) {
        return user.equals("admin") && password.equals("1234");
    }

    private void navigate() {
        Intent intent = new Intent(this, SyncActivity.class);
        startActivity(intent);
        this.finish();
    }
}