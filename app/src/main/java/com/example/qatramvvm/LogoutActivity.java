package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoutActivity extends AppCompatActivity {
    SessionManager session;
    Button exit, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_logout);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        session.checkLogin();

        AlertDialog.Builder alert = new AlertDialog.Builder(LogoutActivity.this);
        View view = getLayoutInflater().inflate(R.layout.activity_logout,null);
        exit = view.findViewById(R.id.exit);
        cancel = view.findViewById(R.id.cancel);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(LogoutActivity.this,MainActivity.class));
            }
        });
        alertDialog.show();

    }
}