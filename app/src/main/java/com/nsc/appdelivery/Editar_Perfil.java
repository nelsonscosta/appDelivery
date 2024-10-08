package com.nsc.appdelivery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Editar_Perfil extends AppCompatActivity {

    private CircleImageView foto_editar_usuario;
    private EditText editar_nome;
    private Button bt_atualizarDados, bt_selecionarFoto;
    private Uri mSelecionarUri;
    private  String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        iniciarComponenntes();

        bt_selecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarFotoGaleria();
            }
        });

        bt_atualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editar_nome.getText().toString();

                if(nome.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, "Preencha os campos", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    AtualizarDadosPerfil(v);
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
                            foto_editar_usuario.setImageURI(mSelecionarUri);
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

    public void AtualizarDadosPerfil(View view) {

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
                                String nome = editar_nome.getText().toString();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                Map<String, Object> usuarios = new HashMap<>();
                                usuarios.put("nome",nome);
                                usuarios.put("foto",foto);

                                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                db.collection("usuarios").document(usuarioID)
                                        .update("nome",nome,"foto",foto)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                               Snackbar snackbar = Snackbar.make(view, "Dados atualizados com sucesso", Snackbar.LENGTH_INDEFINITE)
                                                       .setAction("OK", new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               finish();
                                                           }
                                                       });
                                               snackbar.show();
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
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void iniciarComponenntes(){
        foto_editar_usuario = findViewById(R.id.foto_editar_usuario);
        editar_nome = findViewById(R.id.editar_nome);
        bt_atualizarDados = findViewById(R.id.bt_atualizarDados);
        bt_selecionarFoto = findViewById(R.id.bt_selecionarFoto);
    }
}