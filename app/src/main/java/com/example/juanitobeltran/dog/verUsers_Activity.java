package com.example.juanitobeltran.dog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class verUsers_Activity extends AppCompatActivity {

    private TextView nameDog, nameDueno, raza, mail, tel;
    private ImageView img;
    private Button nextB, prevB;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseUser user;

    ArrayList<DataSnapshot> arrayIn;
    public String uid;
    public int page =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_usurios);

        nameDueno = (TextView) findViewById(R.id.nameDueno);//
        nameDog = (TextView) findViewById(R.id.nameDog);//
        raza = (TextView) findViewById(R.id.raza);
        mail = (TextView) findViewById(R.id.email);
        tel = (TextView) findViewById(R.id.tel);
        img = (ImageView) findViewById(R.id.imagePefil);
        nextB = (Button) findViewById(R.id.next);
        prevB = (Button) findViewById(R.id.Prev);

        mail.setVisibility(View.INVISIBLE);
        tel.setVisibility(View.INVISIBLE);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Usuario");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Query query = myRef.orderByChild("uid");//.equalTo("Ab0xCAJbxOTj2WwvzrJEhmSpzLF2");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    arrayIn = new ArrayList<>();
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot Usuario : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        arrayIn.add(Usuario);
                        //System.out.println(""+arrayIn.get(0).child("ciudad").getValue().toString());
                    }

                    inicio();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String mail = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            uid = user.getUid();
        }
    }

    public void inicio(){
        getCurrentUser();
        for (int i=0;i<arrayIn.size();i++){
            if(arrayIn.get(i).child("uid").getValue().toString().equals(uid.toString())){
                arrayIn.remove(i);
            }
        }

        nameDueno.setText("Nombre Dueño: "+arrayIn.get(page).child("nombreUser").getValue().toString());
        nameDog.setText("Nombre Perrito: "+arrayIn.get(page).child("nombrePero").getValue().toString());
        raza.setText("Raza: "+arrayIn.get(page).child("raza").getValue().toString());

        String uidTemp = arrayIn.get(page).child("uid").getValue().toString();

        storageReference.child("fotos").child(uidTemp).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img.setImageBitmap(bmp);
                img.setAdjustViewBounds(true);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(verUsers_Activity.this, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void wow(View view){
        mail.setVisibility(View.VISIBLE);
        tel.setVisibility(View.VISIBLE);

        mail.setText("Mail: "+arrayIn.get(page).child("email").getValue().toString());
        tel.setText("Telefóno: "+arrayIn.get(page).child("telefono").getValue().toString());
    }

    public void next(View view){
        mail.setVisibility(View.INVISIBLE);
        tel.setVisibility(View.INVISIBLE);

        page += 1;

        if(page>arrayIn.size()-1){
            page = arrayIn.size()-1;
            Toast.makeText(verUsers_Activity.this, "No tenemos más usuarios disponibles.", Toast.LENGTH_SHORT).show();
        }else{
            nameDueno.setText("Nombre Dueño: "+arrayIn.get(page).child("nombreUser").getValue().toString());
            nameDog.setText("Nombre Perrito: "+arrayIn.get(page).child("nombrePero").getValue().toString());
            raza.setText("Raza: "+arrayIn.get(page).child("raza").getValue().toString());

            String uidTemp = arrayIn.get(page).child("uid").getValue().toString();

            storageReference.child("fotos").child(uidTemp).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img.setImageBitmap(bmp);
                    img.setAdjustViewBounds(true);
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(verUsers_Activity.this, "Error!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void prev(View view){
        mail.setVisibility(View.INVISIBLE);
        tel.setVisibility(View.INVISIBLE);

        page -= 1;

        if(page<0){
            page =0;
            Toast.makeText(verUsers_Activity.this, "No tenemos más usuarios disponibles.", Toast.LENGTH_SHORT).show();
        }else{
            nameDueno.setText("Nombre Dueño: "+arrayIn.get(page).child("nombreUser").getValue().toString());
            nameDog.setText("Nombre Perrito: "+arrayIn.get(page).child("nombrePero").getValue().toString());
            raza.setText("Raza: "+arrayIn.get(page).child("raza").getValue().toString());

            String uidTemp = arrayIn.get(page).child("uid").getValue().toString();

            storageReference.child("fotos").child(uidTemp).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img.setImageBitmap(bmp);
                    img.setAdjustViewBounds(true);
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(verUsers_Activity.this, "Error!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

