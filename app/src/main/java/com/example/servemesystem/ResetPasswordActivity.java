package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity {


    EditText enterPassword;
    EditText confirmPassword;
    Button submitResetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        enterPassword = findViewById(R.id.enterPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        submitResetPassword = findViewById(R.id.submitResetPassword);
        submitResetPassword.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String enteredPasswordField = enterPassword.getText().toString();
                        String confirmPasswordField = confirmPassword.getText().toString();
                        if (enteredPasswordField.equals(confirmPasswordField)) {
                            //write code to update password in Firebase
                            Toast.makeText(ResetPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ResetPasswordActivity.this, "Password fields do not match", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );

    }
}
