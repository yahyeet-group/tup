package com.yahyeet.boardbook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;



import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.FriendsActivity.FriendsFragment;
import com.yahyeet.boardbook.activity.GameActivity.GamesFragment;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
        Fragment selectedFragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_game:
                selectedFragment = new GamesFragment();
                break;
            case R.id.nav_friends:
                selectedFragment = new FriendsFragment();
                break;
            case R.id.nav_chat:
                selectedFragment = new ChatFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        ImageButton createButton = findViewById(R.id.btn_create);
        createButton.setOnClickListener((n) -> {
            Intent intent = new Intent(this, CreateMatchActivity.class);
            startActivity(intent);
            System.out.println("yeet");
        });
    }

}
