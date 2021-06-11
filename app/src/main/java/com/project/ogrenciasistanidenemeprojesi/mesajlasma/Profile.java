package com.project.ogrenciasistanidenemeprojesi.mesajlasma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.project.ogrenciasistanidenemeprojesi.R;

import java.io.IOException;
import java.util.UUID;

public class Profile extends AppCompatActivity {

    private Button btnLogOut, btnUpload;
    private ImageView imgProfile;

    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnUpload = findViewById(R.id.btnUploadImage);
        imgProfile = findViewById(R.id.profile_img);


        btnUpload.setOnClickListener(new View.OnClickListener() {//fotoğraf yükle butonuna tıklandığında
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {//çıkış yap butona tıkladığında Main'e dön
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,Main7Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {//fotoğrafa tıklandığında
            @Override
            public void onClick(View view) {
                //galeriye ulaşmak istediğimizi söylüyoruz
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                photoIntent.setType("image/*");
                startActivityForResult(photoIntent,1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data!=null){
            imagePath = data.getData();//fotoğrafı data'dan alıyoruz ve imagePath'e atıyoruz
            getImageInImageView();
        }
    }

    private void getImageInImageView() {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgProfile.setImageBitmap(bitmap);

    }

    private void uploadImage(){//firebase'e fotoğrafı yükleme

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                updateProfilePicture(task.getResult().toString());
                            }
                        }
                    });
                    Toast.makeText(Profile.this, "Fotoğraf başarıyla yüklendi!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Profile.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() { //fotoğraf yüklenme yüzdesi
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                progressDialog.setMessage(" Uploaded "+(int) progress + "%");
            }
        });
    }


    private void updateProfilePicture(String url){//database'deki boş string yerine fotoğrafımızın url'ini ekliyoruz
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profilePicture").setValue(url);
    }


}