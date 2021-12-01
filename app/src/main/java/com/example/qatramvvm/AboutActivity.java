package com.example.qatramvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.about);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        //session.checkLogin();

    }
    public void facebook(View view) {
        Intent facebook= new Intent(Intent.ACTION_VIEW);
        facebook.setData(Uri.parse("https://www.facebook.com/elfadelisam"));
        if(facebook.resolveActivity(getPackageManager())!= null)
            startActivity(facebook);
    }

    public void message(View view) {
        try {
            Intent email= new Intent(Intent.ACTION_SEND);
            email.setData(Uri.parse("mailto:"));
            email.setType("message/rfc822");

            email.putExtra(Intent.EXTRA_EMAIL,"elfadelisam1991@gmail.com");
            email.putExtra(Intent.EXTRA_SUBJECT,"Message Address");
            email.putExtra(Intent.EXTRA_TEXT,"Message Subject");
            startActivity(email);
        }catch (Exception e)
        {
            Toast toast = new Toast(getApplicationContext());
            TextView tv=new TextView(AboutActivity.this);
            Typeface tf = Typeface.createFromAsset(this.getAssets(),"Cairo-Regular.ttf");
            tv.setTypeface(tf);
            tv.setText("عذرا لا يوجد تطبيق بريد");
            tv.setBackgroundColor(Color.rgb(196,40,63));
            tv.setPadding(20,10,20,10);
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(14);
            toast.setView(tv);
            View v=toast.getView();
            toast.show();
        }
    }

    public void call(View view) {
        Intent phone= new Intent(Intent.ACTION_DIAL);
        phone.setData(Uri.parse("tel:00249126311599"));
        if(phone.resolveActivity(getPackageManager())!= null)
            startActivity(phone);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}