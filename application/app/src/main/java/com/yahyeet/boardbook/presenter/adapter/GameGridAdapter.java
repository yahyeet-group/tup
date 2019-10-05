package com.yahyeet.boardbook.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yahyeet.boardbook.R;
import com.yahyeet.boardbook.model.entity.Game;

import java.util.List;

public class GameGridAdapter extends GameAdapter {


    public GameGridAdapter(Context context, List<Game> gameList) {
        super(context, gameList);
    }

    @Override
    View getConvertView(ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.game_grid_element, parent, false);
    }

    @Override
    void setupViewElements(View convertView, Game currentItem) {
        TextView textViewName = convertView.findViewById(R.id.gameGridName);
        TextView textViewPlayers = convertView.findViewById(R.id.gameGridPlayers);

        textViewName.setText(currentItem.getName());
        // TODO: Add playercount to game
        textViewPlayers.setText("5 Players");
    }
}
