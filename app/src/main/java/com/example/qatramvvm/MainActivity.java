package com.example.qatramvvm;

import android.content.Intent;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Button exit, cancel;
    SessionManager session;
    long time;

    private AppBarConfiguration mAppBarConfiguration, appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext(), SessionManager.SESSION_USER_SESSION);
        session.checkLogin();

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        boolean ch = cic.isConnecting();
        if(!ch){
            //showCustomDialog();
        }


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.my_requests, R.id.update_profile, R.id.guidelines, R.id.nav_cases, R.id.nav_donors,
                R.id.nav_contact, R.id.about, R.id.add_case, R.id.add_donor, R.id.donor_details, R.id.login,
                R.id.registration, R.id.logout, R.id.nav_policy)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView bottom_nav_view = findViewById(R.id.bottom_nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cases, R.id.nav_donors, R.id.nav_contact)
                .build();
        NavController navCon = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navCon, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottom_nav_view, navCon);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        {
            int id = menuItem.getItemId();
            if (id == R.id.update_profile) {
                Intent intent=new Intent(this,Update_profileActivity.class);
                startActivity(intent);
            } else if (id == R.id.my_requests) {
                Intent intent=new Intent(this,MyRequestsActivity.class);
                startActivity(intent);
            } else if (id == R.id.add_case) {
                Intent intent=new Intent(this,Add_caseActivity.class);
                startActivity(intent);
            } else if (id == R.id.add_donor) {
                Intent intent=new Intent(this,Add_donorActivity.class);
                startActivity(intent);
            } else if (id == R.id.change_password) {
                Intent intent=new Intent(this,Change_passwordActivity.class);
                startActivity(intent);
            } else if (id == R.id.guidelines) {
                Intent intent=new Intent(this,EducateActivity.class);
                startActivity(intent);
            } else if (id == R.id.about) {
                Intent intent=new Intent(this,AboutActivity.class);
                startActivity(intent);
            } else if (id == R.id.logout) {
                logout();
            } else if (id == R.id.nav_share) {
                String title = "تطبيق قطرة دم" ;
                String link = "https://play.google.com/store/apps/details?id=com.example.qatra";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, title + "\n" + link);
                startActivity(Intent.createChooser(share,"Share Using"));
            } else if (id == R.id.nav_rate) {
                String rate = "market://details?id="+getApplicationContext().getPackageName();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(rate));
                startActivity(intent);
            }else if (id == R.id.nav_policy) {
                Intent intent=new Intent(this,PolicyActivity.class);
                startActivity(intent);
            }
            drawer.closeDrawers(); // close nav bar
            return true;
        }
    }

    public void logout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.activity_logout,null);
        exit    = view.findViewById(R.id.exit);
        cancel  = view.findViewById(R.id.cancel);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                //finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void showCustomDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.alert_connect,null);
        Button exit   = view.findViewById(R.id.exit);
        Button cancel = view.findViewById(R.id.cancel);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(time+2000>System.currentTimeMillis()) {
                super.onBackPressed();
            }
            else
            {
                Toast toast = new Toast(getApplicationContext());
                TextView tv=new TextView(MainActivity.this);
                tv.setText("أضغط مرة أخرى للخروج");
                tv.setBackgroundColor(Color.rgb(196,40,63));
                tv.setPadding(50,20,50,20);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(14);
                toast.setView(tv);
                toast.show();
            }
        }
        time=System.currentTimeMillis();
    }
}
