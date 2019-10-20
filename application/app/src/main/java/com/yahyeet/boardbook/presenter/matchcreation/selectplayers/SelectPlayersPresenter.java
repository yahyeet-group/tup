package com.yahyeet.boardbook.presenter.matchcreation.selectplayers;

import android.content.Context;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.matchcreation.selectplayers.SelectPlayersFragment;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.presenter.matchcreation.CMMasterPresenter;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayersPresenter {

	private CMMasterPresenter masterPresenter;
	private SelectPlayersFragment spf;
	private PlayerAdapter playerAdapter;
	private List<User> database = new ArrayList<>();

	public SelectPlayersPresenter(SelectPlayersFragment spf, CMMasterPresenter cma) {
		this.masterPresenter = cma;
		this.spf = spf;


	}

	public void repopulateMatches() {
		playerAdapter.notifyDataSetChanged();
	}

	public void enableGameFeed(RecyclerView gameRecycleView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		gameRecycleView.setLayoutManager(layoutManager);
/*
		List<User> testSet = new LinkedList<>();
		User testUser = new User();
		testUser.setName("Jaan Karm");
		testSet.add(testUser);
		User testUser2 = new User();
		testUser2.setName("Broberg Bror");
		testSet.add(testUser2);
		User testUser3 = new User();
		testUser3.setName("Rolf the Kid");
		testSet.add(testUser3);
		User testUser4 = new User();
		testUser4.setName("Daniel the Man");
		testSet.add(testUser4);*/

		gameRecycleView.setAdapter(playerAdapter);
	}

	public CMMasterPresenter getMasterPresenter() {
		return masterPresenter;
	}

	public void enableSearchBar(SearchView searchView){

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				playerAdapter.getFilter().filter(newText);
				return false;
			}
		});
	}
}
