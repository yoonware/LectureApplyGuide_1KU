package com.onekus.onekus;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Lecture> lectures;
    static ArrayList<Lecture> favorites;
    static long               backPressedTime;
    int howMany;

    BottomNavigationView bottomNavigationView;
    FragmentManager      fragmentManager;

    Fragment_1 fragment_1;
    Fragment_2 fragment_2;
    Fragment_3 fragment_3;
    Fragment_4 fragment_4;
    Fragment_5 fragment_5;

    static SharedPreferences spNumber;
    static SharedPreferences spCode;
    static SharedPreferences spTitle;
    static SharedPreferences spProf;
    static SharedPreferences spTime;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && intervalTime <= 2000) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        favorites = new ArrayList<>();

        spNumber = getSharedPreferences("spNumber", MODE_PRIVATE);
        spCode = getSharedPreferences("spCode", MODE_PRIVATE);
        spTitle = getSharedPreferences("spTitle", MODE_PRIVATE);
        spProf = getSharedPreferences("spProf", MODE_PRIVATE);
        spTime = getSharedPreferences("spTime", MODE_PRIVATE);

        howMany = spNumber.getInt("howMany", 0);
        if (howMany > 0) {
            for (int i = 0; i < howMany; i++) {
                String code = spCode.getString(i+"", "");
                String title = spTitle.getString(i+"", "");
                String prof = spProf.getString(i+"", "");
                String time = spTime.getString(i+"", "");
                favorites.add(new Lecture(code, title, prof, time));
            }
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragment_1           = new Fragment_1();
        fragmentManager      = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment_1).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        if(fragment_1 == null) {
                            fragment_1 = new Fragment_1();
                            fragmentManager.beginTransaction().add(R.id.frameLayout, fragment_1).commit();
                        }
                        if(fragment_1 != null) fragmentManager.beginTransaction().show(fragment_1).commit();
                        if(fragment_2 != null) fragmentManager.beginTransaction().hide(fragment_2).commit();
                        if(fragment_3 != null) fragmentManager.beginTransaction().hide(fragment_3).commit();
                        if(fragment_4 != null) fragmentManager.beginTransaction().hide(fragment_4).commit();
                        if(fragment_5 != null) fragmentManager.beginTransaction().hide(fragment_5).commit();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        if(fragment_2 == null) {
                            fragment_2 = new Fragment_2();
                            fragmentManager.beginTransaction().add(R.id.frameLayout, fragment_2).commit();
                        }
                        if(fragment_1 != null) fragmentManager.beginTransaction().hide(fragment_1).commit();
                        if(fragment_2 != null) fragmentManager.beginTransaction().show(fragment_2).commit();
                        if(fragment_3 != null) fragmentManager.beginTransaction().hide(fragment_3).commit();
                        if(fragment_4 != null) fragmentManager.beginTransaction().hide(fragment_4).commit();
                        if(fragment_5 != null) fragmentManager.beginTransaction().hide(fragment_5).commit();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        if(fragment_3 == null) {
                            fragment_3 = new Fragment_3();
                            fragmentManager.beginTransaction().add(R.id.frameLayout, fragment_3).commit();
                        }
                        if(fragment_1 != null) fragmentManager.beginTransaction().hide(fragment_1).commit();
                        if(fragment_2 != null) fragmentManager.beginTransaction().hide(fragment_2).commit();
                        if(fragment_3 != null) fragmentManager.beginTransaction().show(fragment_3).commit();
                        if(fragment_4 != null) fragmentManager.beginTransaction().hide(fragment_4).commit();
                        if(fragment_5 != null) fragmentManager.beginTransaction().hide(fragment_5).commit();
                        break;
                    }
                    case R.id.navigation_menu4: {
                        if(fragment_4 == null) {
                            fragment_4 = new Fragment_4();
                            fragmentManager.beginTransaction().add(R.id.frameLayout, fragment_4).commit();
                        }
                        if(fragment_1 != null) fragmentManager.beginTransaction().hide(fragment_1).commit();
                        if(fragment_2 != null) fragmentManager.beginTransaction().hide(fragment_2).commit();
                        if(fragment_3 != null) fragmentManager.beginTransaction().hide(fragment_3).commit();
                        if(fragment_4 != null) fragmentManager.beginTransaction().show(fragment_4).commit();
                        if(fragment_5 != null) fragmentManager.beginTransaction().hide(fragment_5).commit();
                        break;
                    }
                    case R.id.navigation_menu5: {
                        if(fragment_5 == null) {
                            fragment_5 = new Fragment_5();
                            fragmentManager.beginTransaction().add(R.id.frameLayout, fragment_5).commit();
                        }
                        if(fragment_1 != null) fragmentManager.beginTransaction().hide(fragment_1).commit();
                        if(fragment_2 != null) fragmentManager.beginTransaction().hide(fragment_2).commit();
                        if(fragment_3 != null) fragmentManager.beginTransaction().hide(fragment_3).commit();
                        if(fragment_4 != null) fragmentManager.beginTransaction().hide(fragment_4).commit();
                        if(fragment_5 != null) fragmentManager.beginTransaction().show(fragment_5).commit();
                        break;
                    }
                }
                return true;
            }
        });
    }

}