package com.yahyeet.boardbook.model.handler.populator;

import com.yahyeet.boardbook.model.entity.Match;
import com.yahyeet.boardbook.model.entity.MatchPlayer;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IMatchPlayerRepository;
import com.yahyeet.boardbook.model.repository.IMatchRepository;
import com.yahyeet.boardbook.model.repository.IUserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class UserPopulator {
	IMatchPlayerRepository matchPlayerRepository;
	IMatchRepository matchRepository;
	IUserRepository userRepository;

	public UserPopulator(IMatchPlayerRepository matchPlayerRepository, IMatchRepository matchRepository, IUserRepository userRepository) {
		this.matchPlayerRepository = matchPlayerRepository;
		this.matchRepository = matchRepository;
		this.userRepository = userRepository;
	}

	public CompletableFuture<User> populate(User user) {
		User populatedUser = new User(user.getName());
		populatedUser.setId(user.getId());

		CompletableFuture<List<User>> friendsFuture = userRepository.findFriendsByUserId(populatedUser.getId());

		CompletableFuture<List<MatchPlayer>> futureMatchPlayers = matchPlayerRepository.findMatchPlayersByUserId(populatedUser.getId());

		CompletableFuture<List<Match>> futureMatches = futureMatchPlayers.thenCompose(matchPlayers -> {
			List<CompletableFuture<Match>> allMatchFutures =
				matchPlayers
					.stream()
					.map(matchPlayer -> matchRepository.find(matchPlayer.getMatch().getId()))
					.collect(Collectors.toList());

			CompletableFuture<Void> allOfFutureAllMatchFutures = CompletableFuture.allOf(allMatchFutures.toArray(new CompletableFuture[0]));
			return allOfFutureAllMatchFutures.thenApply(
				future ->
					allMatchFutures
						.stream()
						.map(CompletableFuture::join)
						.collect(Collectors.toList())
			);
		});

		return friendsFuture.thenCombine(futureMatches, (friends, matches) -> {
			friends.forEach(populatedUser::addFriend);
			matches.forEach(populatedUser::addMatch);
			return populatedUser;
		});
	}
}
