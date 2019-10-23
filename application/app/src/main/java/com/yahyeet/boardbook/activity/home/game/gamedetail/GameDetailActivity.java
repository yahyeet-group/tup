package com.yahyeet.boardbook.activity.home.game.gamedetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.presenter.game.gamedetail.GameDetailPresenter;

public class GameDetailActivity extends AppCompatActivity implements IGameDetailActivity, IFutureInteractable {


	private GameDetailPresenter gameDetailPresenter;
	private TextView gameName;
	private TextView gameDescription;
	private TextView gameRules;
	private ImageView gameImage;
	private ProgressBar gameDetailLoading;
	private RecyclerView teamRecyclerView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_detail);
		setAllIds();

		String gameID;
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				gameID = null;
			} else {
				gameID = extras.getString("Game");
			}
		} else {
			gameID = (String) savedInstanceState.getSerializable("Game");
		}

		gameDetailPresenter = new GameDetailPresenter(this, gameID);


	}

	/**
	 * Initiates activity and enables team list
	 */
	protected void onStart() {
		super.onStart();
		gameDetailPresenter.enableTeamList(teamRecyclerView, this);
	}

	public void setGameName(String name) {
		this.gameName.setText(name);
	}

	public void setGameDescription(String description) {
		this.gameDescription.setText(description);
	}

	public void setGameRules(String rules) {
		this.gameRules.setText(rules);
	}

	@Override
	public void enableViewInteraction() {
		gameName.setVisibility(View.VISIBLE);
		gameDescription.setVisibility(View.VISIBLE);
		gameRules.setVisibility(View.VISIBLE);

		gameDetailLoading.setVisibility(View.INVISIBLE);

	}

	@Override
	public void disableViewInteraction() {
		gameName.setVisibility(View.INVISIBLE);
		gameDescription.setVisibility(View.INVISIBLE);
		gameRules.setVisibility(View.INVISIBLE);

		gameDetailLoading.setVisibility(View.VISIBLE);

	}

	@Override
	public void displayLoadingFailed() {
		findViewById(R.id.gameDetailError).setVisibility(View.VISIBLE);
	}

	private void setAllIds(){
		gameName = findViewById(R.id.gameDetailName);
		gameDescription = findViewById(R.id.gameDetailDescription);
		gameRules = findViewById(R.id.gameDetailRules);
		gameDetailLoading = findViewById(R.id.gameDetailLoading);
		teamRecyclerView = findViewById(R.id.gameDetailRecyclerView);
	}
}
