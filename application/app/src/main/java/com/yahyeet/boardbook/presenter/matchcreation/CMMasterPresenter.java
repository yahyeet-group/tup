package com.yahyeet.boardbook.presenter.matchcreation;

import androidx.fragment.app.FragmentManager;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.activity.matchcreation.configureteams.ConfigureTeamsFragment;
import com.yahyeet.boardbook.activity.matchcreation.CreateMatchActivity;
import com.yahyeet.boardbook.activity.matchcreation.selectgame.SelectGameFragment;
import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;

public class CMMasterPresenter {

	private MatchCreationDataObject cmdh;
	private CreateMatchActivity activity;
	private FragmentManager fm;

	public CMMasterPresenter(CreateMatchActivity activity) {
		this.activity = activity;
		cmdh = new MatchCreationDataObject(activity);
		fm = activity.getSupportFragmentManager();
	}

	public void goToConfigureTeams() {
		fm.beginTransaction().replace(R.id.fragment_container, new ConfigureTeamsFragment()).commit();
	}

	public void goToSelectGame(){
		fm.beginTransaction().replace(R.id.fragment_container, new SelectGameFragment()).commit();
	}

	public void goToSelectPlayers(){
		fm.beginTransaction().replace(R.id.fragment_container, new SelectPlayersFragment()).commit();
	}

	public MatchCreationDataObject getCmdh(){
		return cmdh;
	}

	public void finalizeMatch(){
		Match finalMatch = new Match(cmdh.getGame());
		for (MatchPlayer mp : cmdh.getPlayers()){
			finalMatch.addMatchPlayer(mp);
		}

		//TODO here do big save to FireBase Desu
	}
}
