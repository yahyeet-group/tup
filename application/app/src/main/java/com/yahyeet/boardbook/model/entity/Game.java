package com.yahyeet.boardbook.model.entity;

import java.util.ArrayList;
import java.util.List;


public class Game extends AbstractEntity {
	private String name;
	private String description;
	private int minPlayers;
	private int maxPlayers;
	private int difficulty;
	private List<GameTeam> teams;

	public Game(String name,
							String description,
							int difficulty,
							int minPlayers,
							int maxPlayers) {
		this.name = name;
		this.description = description;
		this.difficulty = difficulty;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.teams = new ArrayList<>();
	}

	public Game() {
	}


	//Nox has added this in purely for testing purposes
	public Game(String name, String description){
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<GameTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<GameTeam> teams) {
		this.teams = teams;
	}
}


