package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alarmapp.Service.MessageSender;
import com.example.alarmapp.cypher.RC4;
import com.example.alarmapp.databinding.ActivityLoginBinding;
import com.example.alarmapp.databinding.ActivitySyncBinding;

import java.util.Objects;

public class SyncActivity extends AppCompatActivity {

    private ActivitySyncBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySyncBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSync.setOnClickListener(view -> {
            String address = getAddress();
            Integer port = getPort();
            String startHour = getStartHourRoutine();
            String endHour = getEndHourRoutine();

            String routineMsg = "DEFINIR_ROTINA_1_" + startHour + "_" + endHour;

//            MessageSender ms = new MessageSender(address, port);
//            ms.execute(routineMsg);

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            RC4 rc4 = new RC4();
            byte[] encrypt = rc4.encryptMessage(routineMsg, "Alexandre&Isaac");

            StringBuilder sb = new StringBuilder();

            for (byte b : encrypt) {
                sb.append(String.format("%02X ", b));
            }

            Toast toast = Toast.makeText(context, routineMsg + " / " + sb, duration);
            toast.show();
            System.out.println(sb);

            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private String getAddress() {
        return Objects.requireNonNull(binding.editTextAddress.getText()).toString();
    }

    private Integer getPort() {
        String value = Objects.requireNonNull(binding.editTextPort.getText()).toString();
        return Integer.parseInt(value);
    }

    private String getStartHourRoutine() {
        return Objects.requireNonNull(binding.editTextStartHour.getText()).toString();
    }

    private String getEndHourRoutine() {
        return Objects.requireNonNull(binding.editTextEndHour.getText()).toString();
    }
}