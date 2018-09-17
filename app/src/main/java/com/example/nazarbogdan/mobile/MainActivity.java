package com.example.nazarbogdan.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Button btSayMain;
    EditText etName;
    TextView tvPrivet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello World");
        etName = findViewById(R.id.etName);
        btSayMain = findViewById(R.id.btSayHello);
        tvPrivet = findViewById(R.id.tvHello);
    }

    public void clearMessage(View view){
        etName.getText().clear();
    }

    public void showMessage(View view){
        tvPrivet.setText("Hello" + " " + etName.getText().toString());
        etName.getText().clear();
    }
}
