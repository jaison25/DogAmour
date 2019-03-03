package com.example.juanitobeltran.dog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Registro_Activity extends AppCompatActivity {

    private EditText userNomb, userTel,perroNomb, edadPerro, ciudadUser, descrip;
    private Spinner spTipoPerro, spRazaPerro;
    private RadioButton sexoF,sexoM;
    private ImageView img;
    public String uid;
    public String email;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog mProgressDialog;
    public boolean exitoImage = false;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_registro);

        sexoF = (RadioButton) findViewById(R.id.radioSexoF);
        sexoM = (RadioButton)findViewById(R.id.radioSexoM);
        userNomb = (EditText)findViewById(R.id.userNomb);//
        userTel = (EditText)findViewById(R.id.userTel);//
        perroNomb = (EditText) findViewById(R.id.nombPerro);

        sexoF = (RadioButton) findViewById(R.id.radioSexoF);
        sexoM = (RadioButton)findViewById(R.id.radioSexoM);
        userNomb = (EditText)findViewById(R.id.userNomb);//
        userTel = (EditText)findViewById(R.id.userTel);//
        perroNomb = (EditText) findViewById(R.id.nombPerro);//
        edadPerro = (EditText) findViewById(R.id.edadperro);//
        ciudadUser = (EditText) findViewById(R.id.ciudadUser);//
        spTipoPerro = (Spinner)findViewById(R.id.spTipoPerr);
        spRazaPerro = (Spinner)findViewById(R.id.spRaza);
        descrip = (EditText) findViewById(R.id.descrip);

        img = (ImageView) findViewById(R.id.imageView2);
        mProgressDialog = new ProgressDialog(this);

        sexoM.setChecked(true);
        sexoF.setChecked(false);

        spTipoPerro.setSelection(0);
        spRazaPerro.setSelection(0);

        getCurrentUser();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Usuario");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    public void seleccionar (View view){
        String nombreUser, nombreDog, city,Tdog,Rdog,tel,desc;
        int edadDog;
        boolean sex=true;

        if (exitoImage){
            if (sexoM.isChecked())
                sex=true;
            else{
                sex=false;
            }

            nombreUser = userNomb.getText().toString();
            tel = userTel.getText().toString();
            nombreDog = perroNomb.getText().toString();
            edadDog = Integer.parseInt(edadPerro.getText().toString());
            city = ciudadUser.getText().toString();
            desc = descrip.getText().toString();

            Tdog = spTipoPerro.getSelectedItem().toString();
            Rdog = spRazaPerro.getSelectedItem().toString();

            Usuario userIn = new Usuario(nombreUser,tel,Tdog,Rdog,sex,nombreDog,edadDog,city,desc,uid,email);

            String id = myRef.push().getKey();
            myRef.child(id).setValue(userIn);

            Toast.makeText(Registro_Activity.this, "Registro Exitoso!!",
                    Toast.LENGTH_SHORT).show();

            finish();
            Intent verUserActivity = new Intent(getApplicationContext(), verUsers_Activity.class);
            startActivity(verUserActivity);
        }else{
            Toast.makeText(Registro_Activity.this, "Seleccione una imagen de su mascota.",
                    Toast.LENGTH_SHORT).show();
        }



    }

    public void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            uid = user.getUid();
        }
    }

    public void RadioChecked (View view){
        switch (view.getId()){
            case R.id.radioSexoM:
                if (sexoM.isChecked()){
                    sexoF.setChecked(false);
                }
                break;

            case R.id.radioSexoF:
                if (sexoF.isChecked()){
                    sexoM.setChecked(false);
                }
        }

    }

    public void cargarImg (View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);


    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setTitle("Subiendo...");
            mProgressDialog.setMessage("Subiendo foto a Firebase");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            Uri uri = data.getData();

            getCurrentUser();
            StorageReference filepath = storageReference.child("fotos").child(uid);//.child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();

                    storageReference.child("fotos").child(uid).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Use the bytes to display the image
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            img.setImageBitmap(bmp);
                            img.setAdjustViewBounds(true);
                            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            Toast.makeText(Registro_Activity.this, "la foto se cargò exitosamente", Toast.LENGTH_SHORT).show();
                            exitoImage = true;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Registro_Activity.this, "Error!!", Toast.LENGTH_SHORT).show();
                            exitoImage = false;
                        }
                    });
                }
            });

        }

    }

}