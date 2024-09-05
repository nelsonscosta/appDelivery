package com.nsc.appdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Lista_Produtos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if(itemID == R.id.perfil){
            
        } else if (itemID == R.id.pedidos) {
            
        } else if (itemID == R.id.deslogar) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Lista_Produtos.this, "Usuário deslogado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Lista_Produtos.this, Form_login.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}