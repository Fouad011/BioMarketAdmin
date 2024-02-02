package com.example.biomarketadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biomarketadmin.Address;
import com.example.biomarketadmin.DeliveryModel;
import com.example.biomarketadmin.R;
import com.example.biomarketadmin.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class AddDeliveryActivity extends AppCompatActivity {
    TextView topTitle;
    ImageView quit;

    EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterCNI,
            editTextRegisterMobile, editTextRegisterPwd;

    TextView loginLink;

    RelativeLayout progressBar;
    Button registerBtn;
    String deliveryId;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference myRef =  FirebaseDatabase.getInstance().getReference();

    static final String Tag = "RegisterActivity";

    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);


        actionBar("Add Delivery");



        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterCNI = findViewById(R.id.editText_register_cni);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);



        progressBar = findViewById(R.id.progressBar);

        registerBtn = findViewById(R.id.register_button);

//        mAuth = FirebaseAuth.getInstance();








        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fullName = editTextRegisterFullName.getText().toString().trim();
                String email = editTextRegisterEmail.getText().toString().trim();
                String mobile = editTextRegisterMobile.getText().toString().trim();
                String pwd = editTextRegisterPwd.getText().toString().trim();
                String cni = editTextRegisterCNI.getText().toString().trim();




                if(fullName.isEmpty()) {
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                } else if (email.isEmpty()) {
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (cni.length() != 8) {
                    editTextRegisterCNI.setError("CNI is required");
                    editTextRegisterEmail.requestFocus();
                } else if (mobile.isEmpty()) {
                    editTextRegisterMobile.setError("Mobile no. is required");
                    editTextRegisterMobile.requestFocus();
                } else if (mobile.length() != 10) {
                    editTextRegisterMobile.setError("Mobile no. not equal 10 digits");
                    editTextRegisterMobile.requestFocus();
                } else if (pwd.isEmpty()) {
                    editTextRegisterPwd.setError("Password name is required");
                    editTextRegisterPwd.requestFocus();
                } else {
                    setUser(fullName, email, cni, mobile, pwd);
                }
            }
        });




    }



    private void setUser(String fullName, String email, String cni, String mobile, String pwd) {

        //Create User Profile

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isComplete()) {
                    // Sign in success, update UI with the signed-in user's information
//
                    deliveryId = Objects.requireNonNull(task.getResult().getUser()).getUid();

                    //Enter User Data into the Firebase Realtime Database. (ReadWriteUserDetails is my class)
                    Address address = new Address();

                    DeliveryModel deliveryModel = new DeliveryModel(fullName, cni, mobile, "empty", true);

                    //Extracting User reference from Database for "Registered Users"



                    myRef.child("Deliverers").child(deliveryId).setValue(deliveryModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                //Send Verification Email
//                                firebaseUser.sendEmailVerification();

                                Toast.makeText(getApplicationContext(), "Reagistration Successful, Please verify your email",
                                        Toast.LENGTH_SHORT).show();
//
                                editTextRegisterFullName.setText("");
                                editTextRegisterEmail.setText("");
                                editTextRegisterMobile.setText("");
                                editTextRegisterPwd.setText("");
                                editTextRegisterCNI.setText("");

                                //Open User Profile after successful registration
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                //To Prevent User from returning back to Register Activity on pressing back button after registration
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                finish(); //To close Register Activity

                            }else {

                                Toast.makeText(getApplicationContext(), "Reagistration Field. Please try again",
                                        Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                } else {
                    // If sign in fails, display a message to the user.

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your password is too weak.");
                        editTextRegisterPwd.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterEmail.setError("Your email is invalid or already in use. kindly re-enter.");
                        editTextRegisterEmail.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterEmail.setError("User is already registered with this email. Use another email.");
                        editTextRegisterEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(Tag, e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Reagistration Faild", Toast.LENGTH_SHORT).show();
                }

            }
        });
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