package com.project.ogrenciasistanidenemeprojesi.yoklama.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.project.ogrenciasistanidenemeprojesi.R;
import com.project.ogrenciasistanidenemeprojesi.yoklama.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentRegisterActivity extends AppCompatActivity {

    private static final String TAG = "StudentRegisterActivity";

    private ImageView img_studentImage;

    private EditText et_name, et_surname, et_mail, et_number, et_password;

    private String imageURL = "";
    private StorageReference storageReference; //storage i??in reference
    public static final int IMAGE_REQUEST = 1;
    private Uri imageUri;//bir kaynaktan resim bilgisi alabilmek i??in Uri s??n??f??n?? kullanmam??z gerekiyor
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        init();

        img_studentImage.setOnClickListener(new View.OnClickListener() { //imageview'e t??kland??????nda
            @Override
            public void onClick(View v) {
                // ??zin verilmediyse kullan??c??dan kameran??n a????lmas?? i??in izin istiyoruz
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ // Baz?? android s??r??mlerinde izne gerek kalmad?????? i??in burada kontrol ediliyor,
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //??zin daha ??nceden verildiyse direkt methodu ba??lat??yoruz
                        openImage();
                    }
                }
                else {
                    //E??er android s??r??m?? < marshmallow ise izin istemeden kamera a????l??yor
                    openImage();
                }

                // Kameradan de??il de galeriden se??ilecekse izne gerek kalmadan method ??al??????yor
                //openImage();
            }
        });

        /* Kay??t butonuna t??kland??????nda ??al????acak */
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = et_name.getText().toString();
                final String surname = et_surname.getText().toString();
                final String email = et_mail.getText().toString();
                final String number = et_number.getText().toString();
                final String password = et_password.getText().toString();

                if(imageUri != null) {
                    imageURL = imageUri.toString();
                }

                /* Text alanlar??na girdi??in bilgileri kontrol ediyor, herhangi biri bo??sa, ??ifre 6 harften k??????kse ya da foto??raf se??ilmediyse kullan??c??ya uyar?? veriliyor */
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
                    Toast.makeText(StudentRegisterActivity.this, "T??m alanlar doldurulmal??.", Toast.LENGTH_SHORT).show();
                }
                else if(imageUri == null ) {
                    Toast.makeText(StudentRegisterActivity.this, "Foto??raf eklemelisiniz.", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 6) {
                    Toast.makeText(StudentRegisterActivity.this, "????renci numaras?? 6 karakterden k??????k olamaz.", Toast.LENGTH_SHORT).show();
                }
                else {
                    /* E??er her ??ey tamamsa kullan??c?? DatabaseReference ile firebase'ten bi ????renci referans?? (????renci tablosunu g??steren) al??yoruz */
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Student student = dataSnapshot.getValue(Student.class);

                                /* E??er bu ????renci referans??nda bu numara mevcutsa kullan??c??ya uyar?? veriliyor */
                                if(number.equals(student.getNumber())) {
                                    Toast.makeText(StudentRegisterActivity.this, "Bu numara kullan??l??yor.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            /* Ba??ka sorun yoksa register methoduna t??m bilgileri yollan??yor ve orada kullan??c?? kay??t ediliyor. */
                            register(name, surname, email, number, imageUri, password);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

    }

    private void register(final String name, final String surname, final String email, final String number, final Uri imageURL, final String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        /* Firebase Authentication'??n kendi "createUserWithEmailAndPassword" methoduyla e posta ve ??ifresini kullanarak authentication tablosuna kullan??c?? kaydoluyor */
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            assert firebaseUser != null;
                            String id = firebaseUser.getUid();

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students").child(id);

                            List<String> registeredCourses = new ArrayList<>();
                            registeredCourses.add("@null");


                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", id);
                            hashMap.put("name", name);
                            hashMap.put("surname", surname);
                            hashMap.put("email", email);
                            hashMap.put("number", number);
                            hashMap.put("imageURL", "default");
                            hashMap.put("password", password);
                            hashMap.put("registeredCourses", registeredCourses);

                            /* Ayr??ca authentication tablosu d??????nda kullan??c??n??n di??er t??m bilgileri de ????renci referans??n??n "reference.setValue" methodu ile ????renci tablosuna ekleniyor */
                            reference.setValue(hashMap);
                            uploadImage(id);

                            /* Daha sonra da ????renci StudentsMainActivity yani ????renci ana sayfas??na g??nderiliyor */
                            Intent intent = new Intent(StudentRegisterActivity.this, StudentsMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("imageUri", imageURL.toString());
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(StudentRegisterActivity.this, "Bu e posta kullan??l??yor.", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onComplete: " + task.getException().toString());
                        }
                    }
                });


    }


    private void openImage() {
        // Bu yorum i??erisindeki kodlar galeriden se??ilmesi i??in.
       /* Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);*/

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Yeni Foto??raf");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Kameradan");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        /* Foto??raf se??ildikten sonra foto??raf?? kaydedip g??stermek i??in i??in startActivityForResult'la kamera a????l??yor ve url g??nderiliyor,
         daha sonra onActivityResultta bu url'yi kaydedicez*/

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }


    //Burada firebaseStorage'den bi referans al??p url'yi kaydediyoruz
    private void uploadImage(final String id) {
        if(imageUri != null) { //kullan??c?? bir ??ey se??tiyse i??lem yap??ls??n
            storageReference = FirebaseStorage.getInstance().getReference("uploads");
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(StudentRegisterActivity.this, "Foto??raf kaydedildi.", Toast.LENGTH_SHORT).show();

                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        Log.d("TAG", "onComplete: " + mUri);

                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                        // URL'yi storage d??????nda Student tablosunda ????rencinin bilgilerine de daha sonra foto??raf??n ait oldu??u kullan??c??y?? tespit etmek i??in buradan kaydediyoruz,
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students").child(id);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        reference.updateChildren(map);

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StudentRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(StudentRegisterActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openImage();
                }
                else {
                    //permission from popup was denied
                    Toast.makeText(this, "??zin verilmedi.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            if(data != null && data.getData() != null) {
                imageUri = data.getData();

                Glide.with(this)
                        .load(imageUri)
                        .into(img_studentImage);

            }
        }*/

        if(resultCode == RESULT_OK) {

            Glide.with(this)
                    .load(imageUri)
                    .into(img_studentImage);
        }
    }

    private void init() {
        et_name = findViewById(R.id.et_name);
        et_surname = findViewById(R.id.et_surname);
        et_mail = findViewById(R.id.et_mail);
        et_number = findViewById(R.id.et_number);
        et_password = findViewById(R.id.et_password);

        img_studentImage = findViewById(R.id.img_student_picture);


    }
}