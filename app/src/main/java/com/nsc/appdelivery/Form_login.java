package com.nsc.appdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Form_login extends AppCompatActivity {

    private TextView txt_criar_comta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        iniciarComponentes();

        txt_criar_comta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Form_login.this, Form_Cadastro.class);
                startActivity(intent);
            }
        });
    }

    public void iniciarComponentes(){
        txt_criar_comta = findViewById(R.id.txt_criar_conta);
    }
}