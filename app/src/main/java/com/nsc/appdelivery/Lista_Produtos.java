package com.nsc.appdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nsc.appdelivery.adapter.AdapterProduto;
import com.nsc.appdelivery.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class Lista_Produtos extends AppCompatActivity {

    private RecyclerView recyclerView_produto;
    private AdapterProduto adapterProduto;
    private List<Produto> produtoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        recyclerView_produto = findViewById(R.id.recyclerView_produtos);
        produtoList = new ArrayList<>();
        adapterProduto = new AdapterProduto(getApplicationContext(), produtoList);
        recyclerView_produto.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_produto.setHasFixedSize(true);
        recyclerView_produto.setAdapter(adapterProduto);

        Produto produto = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto);

        Produto produto8 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto8);

        Produto produto9 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto9);

        Produto produto10 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto10);

        Produto produto1 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto1);

        Produto produto2 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto2);

        Produto produto3 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto3);

        Produto produto4 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto4);

        Produto produto5 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto5);

        Produto produto6 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto6);

        Produto produto7 = new Produto(R.drawable.ic_launcher_background,"Produto 1", "R$ 50,00");
        produtoList.add(produto7);
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
            Intent intent = new Intent(Lista_Produtos.this, Perfil_Ususario.class);
            startActivity(intent);
            
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