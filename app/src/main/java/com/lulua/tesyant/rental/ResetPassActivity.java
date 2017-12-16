package com.lulua.tesyant.rental;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnReset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edt_email);
        btnReset = findViewById(R.id.btn_resetPass);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                auth.sendPasswordResetEmail(email);
                Toast.makeText(getApplicationContext(), "The instruction has been sent to your email. Go check for it now", Toast.LENGTH_SHORT).show();
                edtEmail.setText(null);
            }
        });

    }
}
