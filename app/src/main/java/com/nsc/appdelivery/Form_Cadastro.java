package com.nsc.appdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Cadastro extends AppCompatActivity {

    private CircleImageView fotoUsuario;
    private Button bt_selecionarUsuario, btCadastrar;
    private EditText edit_nome, edit_email, edit_senha;
    private TextView txtMensagemErro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        iniciarComponentes();
        edit_nome.addTextChangedListener(cadastroTextWhacher);
        edit_email.addTextChangedListener(cadastroTextWhacher);
        edit_senha.addTextChangedListener(cadastroTextWhacher);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario(v);
            }
        });
    }

    public void cadastrarUsuario(View view){
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Snackbar snackbar = Snackbar.
                            make(view,"Cadastro realizado com sucesso",
                                    Snackbar.LENGTH_INDEFINITE)
                            .setAction("ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                         snackbar.show();
                } else{
                    txtMensagemErro.setText("não foi possível o cadastro");
                }
            }
        });
    }

   public  void iniciarComponentes() {
        fotoUsuario = findViewById(R.id.fotoUsuario);
        bt_selecionarUsuario = findViewById(R.id.bt_selecionarFoto);
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        txtMensagemErro = findViewById(R.id.txt_mensagemErro);
        btCadastrar = findViewById(R.id.bt_cadastrar);
   }


    TextWatcher cadastroTextWhacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String nome = edit_nome.getText().toString();
            String email = edit_email.getText().toString();
            String senha = edit_senha.getText().toString();

            if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty() ){
                    btCadastrar.setEnabled(true);
                    btCadastrar.setBackgroundColor(getResources().getColor(R.color.dark_red));
            } else {
                btCadastrar.setEnabled(false);
                btCadastrar.setBackgroundColor(getResources().getColor(R.color.gray));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}