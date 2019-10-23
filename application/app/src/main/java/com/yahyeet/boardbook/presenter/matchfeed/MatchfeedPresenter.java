package com.yahyeet.boardbook.presenter.matchfeed;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IFutureInteractable;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.MatchHandler;
import com.yahyeet.boardbook.model.handler.UserHandler;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.activity.home.matchfeed.IMatchfeedFragment;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.handler.MatchHandlerListener;
import com.yahyeet.boardbook.presenter.OneEntityPresenter;

import java.util.ArrayList;
import java.util.List;

public class MatchfeedPresenter extends OneEntityPresenter<User, UserHandler> {

	private MatchfeedAdapter matchfeedAdapter;
	private List<Match> matchDatabase;


	// TODO: Remove if never necessary
	private IMatchfeedFragment matchfeedFragment;

	public MatchfeedPresenter(IMatchfeedFragment matchfeedFragment) {
		super((IFutureInteractable) matchfeedFragment);
		this.matchfeedFragment = matchfeedFragment;

		matchDatabase = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getMatches();

	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void updateAdapter() {
		//findLoggedInUser();
		matchfeedAdapter.notifyDataSetChanged();
	}

	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param matchRecyclerView the RecyclerView that will be populated with matches
	 */
	public void enableMatchFeed(RecyclerView matchRecyclerView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		matchfeedAdapter = new MatchfeedAdapter(viewContext, matchDatabase);

		matchRecyclerView.setLayoutManager(layoutManager);
		matchRecyclerView.setAdapter(matchfeedAdapter);


	}

	private void findLoggedInUser(){
		findEntity(BoardbookSingleton.getInstance().getUserHandler(),
			BoardbookSingleton
				.getInstance()
				.getAuthHandler()
				.getLoggedInUser()
				.getId());
	}

	@Override
	protected void onEntityFound(User entity) {
		matchDatabase.clear();
		matchDatabase.addAll(entity.getMatches());
	}
}
