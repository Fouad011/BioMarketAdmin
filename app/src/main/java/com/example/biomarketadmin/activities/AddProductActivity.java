package com.example.biomarketadmin.activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;


import com.example.biomarketadmin.CategoryModel;
import com.example.biomarketadmin.Product;
import com.example.biomarketadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {


    TextView topTitle;
    ImageView quit;

    ImageView pic, picture;
    AppCompatButton add;
    EditText name, cat, price, des, classify;
    SwitchCompat instore, visibility;
    TextView imgText, qntStock;
    StorageReference storageReference;
    static final int PICK_IMAGE_REQUEST = 1;
    Uri uriPic;
    FirebaseFirestore baseStore;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
//        qt = findViewById(R.id.quit);
        pic = findViewById(R.id.pic);
        name = findViewById(R.id.name);
        cat = findViewById(R.id.cat);
        price = findViewById(R.id.prx);
        qntStock = findViewById(R.id.qntStock);
        classify = findViewById(R.id.clsf);
        des = findViewById(R.id.dx);
        imgText = findViewById(R.id.imgText);
        add = findViewById(R.id.btnAdd);
        instore = findViewById(R.id.ins);
        visibility = findViewById(R.id.vis);

        picture = findViewById(R.id.picture);


        actionBar("Add Product");


        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGal();
            }
        });

        imgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGal();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent i = new Intent(AddProductActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uriPic!=null){
                    productsData();
                }else {
                    Toast.makeText(AddProductActivity.this, "Please choose image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGal() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriPic = data.getData();
            pic.setImageURI(uriPic);
            picture.setImageURI(uriPic);
            picture.setVisibility(View.VISIBLE);
            String title = uriPic.getLastPathSegment()+"."+getFileExtension(uriPic);
            imgText.setText(title);
        }
    }

    private void productsData() {
        String nm = name.getText().toString();
        String category = cat.getText().toString();
        Double prx = Double.valueOf(price.getText().toString());
        int qnt = Integer.parseInt(qntStock.getText().toString());
        String description = des.getText().toString();
        String clsf = classify.getText().toString();
        Boolean visible = visibility.isChecked();
        Boolean inStore = instore.isChecked();
        storageReference = FirebaseStorage.getInstance().getReference("Products");
        baseStore = FirebaseFirestore.getInstance();
        storageReference.child(UUID.randomUUID().toString()+"."+getFileExtension(uriPic)).putFile(uriPic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();

                        Product product = new Product(nm, category, clsf, prx, description, imageUrl, visible, inStore, qnt);

                        baseStore.collection("Products").document("bioMarketStore").collection("products").add(product)

                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        name.setText("");
                                        cat.setText("");
                                        price.setText("");
                                        qntStock.setText("");
                                        des.setText("");
                                        classify.setText("");
                                        imgText.setText(R.string.insert_image);
                                        pic.setImageResource(R.drawable.baseline_attach_file_24);
                                        picture.setVisibility(View.GONE);
                                        visibility.setChecked(false);
                                        instore.setChecked(false);

//                                        CategoryModel categoryModel = new CategoryModel(category.toUpperCase(), category.toLowerCase().substring(0, category.length()-1), null);
//                                        baseStore.collection("HomeCategory").add(product);


                                        Toast.makeText(AddProductActivity.this, "data has been sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProductActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });



    }


    String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    public void actionBar(String title){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);

        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar);
        quit = customView.findViewById(R.id.quit);
        quit.setVisibility(View.GONE);
        topTitle.setText(title);
    }
}


