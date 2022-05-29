package com.example.alarmapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alarmapp.cypher.RC4;
import com.example.alarmapp.databinding.ActivityAlarmBinding;
import com.example.alarmapp.databinding.ActivitySyncBinding;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class AlarmActivity extends AppCompatActivity {
    private ActivityAlarmBinding binding;

    HashMap<Integer,String> unityList;
    EditText txt;
    String encrypt;
    String decrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        unityList = getUnityHashMap();

        binding.btnNotification.setOnClickListener(view -> {
            dialog();
        });
    }

    private void dialog() {
        AlertDialog.Builder alertName = new AlertDialog.Builder(this);
        final EditText editTextName1 = new EditText(this);

        alertName.setTitle("Insira a mensagem encriptada");
        alertName.setView(editTextName1);
        LinearLayout layoutName = new LinearLayout(this);
        layoutName.setOrientation(LinearLayout.VERTICAL);
        layoutName.addView(editTextName1); // displays the user input bar
        alertName.setView(layoutName);

        alertName.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                txt = editTextName1; // variable to collect user input
                collectInput(); // analyze input (txt) in this method
            }
        });

        alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel(); // closes dialog
                alertName.show();// display the dialog
            }
        });

        alertName.show();
    }

    public void collectInput(){
        // convert edit text to string
        String getInput = txt.getText().toString();

        // ensure that user input bar is not empty
        if (getInput ==null || getInput.trim().equals("")){
            Toast.makeText(getBaseContext(), "Insira uma mensagem válida", Toast.LENGTH_LONG).show();
        }
        // add input into an data collection arraylist
        else {
            encrypt = getInput;
            decryptRC4();
        }
    }

    private void decryptRC4() {
        RC4 rc4 = new RC4();
        byte[] hex = hexStringToByteArray(encrypt);
        decrypt = rc4.decryptMessage(hex, "Alexandre&Isaac");

        setDetails();
    }

    private void setDetails() {
        String[] splitted = decrypt.split("_");
        String unity = splitted[2];
        Integer unityNumber = Integer.parseInt(unity);
        String unidadeIOT = unityList.get(unityNumber);

        binding.alarmTitle.setText("Aviso! - Possível invasão!");

        binding.tvHour.setText("Presença detectada às " + splitted[3]);
        binding.tvHour.setVisibility(View.VISIBLE);

        binding.tvHour.setText("Presença detectada às " + splitted[3] + " na unidade - " + unidadeIOT);
        binding.tvHour.setVisibility(View.VISIBLE);

        binding.imageView.setVisibility(View.VISIBLE);
    }

    private static byte[] hexStringToByteArray(String msg) {
        String s = msg.replaceAll("\\s", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private HashMap getUnityHashMap() {
        HashMap<Integer,String> matrix = new HashMap<Integer,String>();
        matrix.put(1, "Loja Pituaçu");
        matrix.put(2, "Loja Pituba");
        matrix.put(3, "Loja Salvador Shopping");
        matrix.put(4, "Loja Paralela");

        return matrix;
    }

}