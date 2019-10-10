package com.yahyeet.boardbook.presenter;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.IHomeFragment;
import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.GameRole;
import com.yahyeet.boardbook.model.entity.GameTeam;
import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.handler.MatchHandlerListener;
import com.yahyeet.boardbook.presenter.adapter.MatchAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements MatchHandlerListener {

    private MatchAdapter matchAdapter;
    private List<Match> matchDatabase;


    // TODO: Remove if never necessary
    private IHomeFragment homeFragment;

    public HomePresenter(IHomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    /**
     * Makes recyclerView to repopulate its matches with current data
     */
    public void updateMatchAdapter() {
        matchAdapter.notifyDataSetChanged();
    }

    /**
     * Creates the necessary structure for populating matches
     *
     * @param matchRecyclerView the RecyclerView that will be populated with matches
     */
    public void enableMatchFeed(RecyclerView matchRecyclerView, Context viewContext) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
        matchRecyclerView.setLayoutManager(layoutManager);
        //TODO: Replace with matches from user
        matchDatabase = new ArrayList<>();
        for(int i = 0; i < 20; i++)
            matchDatabase.add(new Match());
        matchAdapter = new MatchAdapter(matchDatabase);
        matchRecyclerView.setAdapter(matchAdapter);
    }

    @Override
    public void onAddMatch(Match match) {
        matchDatabase.add(match);
        updateMatchAdapter();
    }

    @Override
    public void onUpdateMatch(Match match) {
        for (int i = 0; i < matchDatabase.size(); i++) {
            if (matchDatabase.get(i).getId().equals(match.getId())) {
                matchDatabase.set(i, match);
            }
        }
        updateMatchAdapter();
    }

    @Override
    public void onRemoveMatch(Match match) {
        for (int i = 0; i < matchDatabase.size(); i++) {
            if (matchDatabase.get(i).getId().equals(match.getId())) {
                matchDatabase.remove(i);
                break;
            }
        }
        updateMatchAdapter();
    }
}
