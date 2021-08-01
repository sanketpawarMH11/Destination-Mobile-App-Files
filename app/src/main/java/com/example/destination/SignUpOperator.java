package com.example.destination;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.destination.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpOperator extends AppCompatActivity {


        private DatabaseReference mDatabase;
        private EditText inputEmail, inputPassword;
        private Button btnSignIn, btnSignUp, btnResetPassword;
        private ProgressBar progressBar;
        private FirebaseAuth auth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up_operator);
            ///getting the toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            //setting the title
            toolbar.setTitle("Sign Up");
           // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

            //placing toolbar in place of actionbar
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //Get Firebase auth instance
            auth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            btnSignIn = (Button) findViewById(R.id.sign_in_button);
            btnSignUp = (Button) findViewById(R.id.reg);
            inputEmail = (EditText) findViewById(R.id.mail);
            inputPassword = (EditText) findViewById(R.id.pass);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
            btnResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetPassword();
                    //startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
                }
            });
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignUpOperator.this, LoginOperator.class));
                    finish();
                }
            });
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!isValidEmail(email)) {
                        inputEmail.setError("Invalid Email");
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpOperator.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignUpOperator.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {

                                        Toast.makeText(SignUpOperator.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        onAuthSuccess(task.getResult().getUser());
                                        startActivity(new Intent(SignUpOperator.this, LoginOperator.class));
                                        finish();
                                    }
                                }
                            });

                }
            });
        }

    private void onAuthSuccess(FirebaseUser user) {
            String username=usernameFromEmail(user.getEmail());

            writeNewUser(user.getUid(),username,user.getEmail());
    }

    private void writeNewUser(String uid, String name, String email) {
         User user=new User(name,email);
         mDatabase.child("Operators").child(uid).setValue(user);

    }

    private String usernameFromEmail(String email) {
            if (email.contains("@"))
            {
                return email.split("@")[0];
            }else
            {
                return email;
            }
    }

    @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }
        private boolean isValidEmail(String email) {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        private void resetPassword() {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
            dialogBuilder.setView(dialogView);
            final EditText editEmail = (EditText) dialogView.findViewById(R.id.emailreset);
            final Button btnReset = (Button) dialogView.findViewById(R.id.btn_reset_password);
            final Button back=(Button)dialogView.findViewById(R.id.btn_back);
            final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
            //dialogBuilder.setTitle("Send Photos");
            final AlertDialog dialog = dialogBuilder.create();
            btnReset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String email = editEmail.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressBar1.setVisibility(View.VISIBLE);
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpOperator.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpOperator.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar1.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }
                            });
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(SignUpOperator.this,SignUpOperator.class));
                }
            });
            dialog.show();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginOperator.class));
                finish();

            case R.drawable.ic_arrow_back_black_24dp:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    }