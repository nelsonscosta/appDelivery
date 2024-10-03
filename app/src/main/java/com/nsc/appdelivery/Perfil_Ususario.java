package com.nsc.appdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil_Ususario extends AppCompatActivity {

    private CircleImageView foto_usuario;
    private TextView nome_usuario, email_usuario;
    private Button bt_editarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ususario);
    }
}