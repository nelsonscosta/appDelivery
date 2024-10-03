package com.nsc.appdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil_Ususario extends AppCompatActivity {

    private CircleImageView foto_perfil_usuario;
    private TextView nome_perfil_usuario, email_perfil_usuario;
    private Button bt_editarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ususario);

        iniciarComponentes();
    }

    public void iniciarComponentes() {
        foto_perfil_usuario = findViewById(R.id.foto_perfil_usuario);
        nome_perfil_usuario = findViewById(R.id.nome_perfil_usuario);
        email_perfil_usuario = findViewById(R.id.email_perfii_usuario);
        bt_editarPerfil = findViewById(R.id.bt_editarPerfil);
    }
}