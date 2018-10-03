package com.example.nazarbogdan.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {
    private TextView name, surname, email, phone;
    private Button show;
    private User user;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_apactiviti);
        init();
    }

    private void init(){
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        show = findViewById(R.id.btn_show);

        Gson gson = new Gson();
        mPrefs = getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = mPrefs.getString("user", "");
        user = gson.fromJson(json, User.class);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText(user.getName());
                surname.setText(user.getSurname());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
            }
        });
    }
}
