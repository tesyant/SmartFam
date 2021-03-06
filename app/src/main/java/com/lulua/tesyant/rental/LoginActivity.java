package com.lulua.tesyant.rental;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnForgot, btnCreate;
    private FirebaseAuth auth;

    public String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnForgot = findViewById(R.id.btn_forgotPass);
        btnCreate = findViewById(R.id.btn_createAccount);

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPassActivity.class));
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), R.string.enter_email, Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), R.string.enter_password, Toast.LENGTH_SHORT).show();
                }

                if (password.length() < 8) {
                    edtPassword.setError(getString(R.string.password_short));
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    }
                                    catch (FirebaseAuthInvalidUserException invalidEmail) {
                                        Toast.makeText(LoginActivity.this, R.string.email_not_registered,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    catch (FirebaseAuthInvalidCredentialsException invalidPassword) {
                                        Toast.makeText(LoginActivity.this, R.string.password_wrong,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception e) {
                                        Log.e(TAG, "error" + e);
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }
}
