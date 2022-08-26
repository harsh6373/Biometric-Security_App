package com.example.biometric_security_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login_btn;
    TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.btn_login);
        tv_message = findViewById(R.id.tv_message);

        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                tv_message.setText("You can use biometric to login");
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                tv_message.setText("This device doesn't have biometric sensor");
                login_btn.setVisibility(View.GONE);
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                tv_message.setText("This device doesn't have fingerprint saved,"+"Plese check your Security Settings.");
                login_btn.setVisibility(View.GONE);
                break;

        }

        BiometricPrompt biometricPrompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        Toast.makeText(MainActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Home_activity.class));
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });


        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric")
                .setNegativeButtonText("Cancel")
                .build();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }
}