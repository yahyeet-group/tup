package com.yahyeet.boardbook.activity.CreateingMatches;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.CreateingMatches.ConfigureTeams.ConfigureTeamsFragment;

public class CreateMatchActivity extends AppCompatActivity implements ICreateMatchActivity {

    private CreateMatchDataHandler cmdh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);
        cmdh = new CreateMatchDataHandler(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfigureTeamsFragment()).commit();
    }

    public void finalizeMatch(){
        //TODO send this either to a presenter or directly to handlers
        cmdh.finalizeMatch();
    }

    public CreateMatchDataHandler getCMDH(){
        return cmdh;
    }

}
