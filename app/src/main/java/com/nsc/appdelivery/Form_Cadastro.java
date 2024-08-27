package com.nsc.appdelivery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Cadastro extends AppCompatActivity {

    private CircleImageView fotoUsuario;
    private Button bt_selecionarUsuario, btCadastrar;
    private EditText edit_nome, edit_email, edit_senha;
    private TextView txtMensagemErro;
    private String usuarioID;
    private Uri mSelecionarUri;


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
        bt_selecionarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarFotoGaleria();
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
                    SalvarDadosUsusario();
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
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "coloque uma senha com no mínimo 6 caracteres";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "e-mail inválido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Esta conta já foi cadastrada";
                    }catch (FirebaseNetworkException e) {
                        erro = "Sem conexão com a internet";
                    }catch (Exception e) {
                        erro = "erro ao cadastrar usuário";
                    }
                    txtMensagemErro.setText(erro);
                }
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        mSelecionarUri = data.getData();

                        try {
                            fotoUsuario.setImageURI(mSelecionarUri);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public void SelecionarFotoGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    public void SalvarDadosUsusario(){

        String nomeArquivo = UUID.randomUUID().toString();

        final StorageReference reference = FirebaseStorage.getInstance().getReference("/imagens/"+nomeArquivo);
        reference.putFile(mSelecionarUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String foto = uri.toString();

                                //iniciar o bd do Firestone
                                String nome = edit_nome.getText().toString();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                Map<String, Object> usuarios = new HashMap<>();
                                usuarios.put("nome",nome);
                                usuarios.put("foto",foto);

                                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                DocumentReference documentReference = db.collection("usuarios").document(usuarioID);
                                documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                            Log.i("db", "Sucesso ao salvar os dados");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("db_error", "Erro ao salvar os dados"+e.toString());
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

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