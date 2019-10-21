package com.yahyeet.boardbook.presenter.friends.addfriends;

import android.content.Context;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yahyeet.boardbook.activity.home.friends.IAddFriendActivity;
import com.yahyeet.boardbook.presenter.BoardbookSingleton;
import com.yahyeet.boardbook.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddFriendPresenter {

	private AddFriendsAdapter addFriendsAdapter;
	// TODO: Remove if never necessary
	private IAddFriendActivity addFriendActivity;

	final private List<User> userDatabase = new ArrayList<>();
	private List<User> all = new ArrayList<>();

	public AddFriendPresenter(IAddFriendActivity addFriendActivity) {
		this.addFriendActivity = addFriendActivity;
	}

	/**
	 * Makes recyclerView to repopulate its matches with current data
	 */
	public void repopulateFriends() {
		addFriendsAdapter.notifyDataSetChanged();
	}

	/**
	 * Creates the necessary structure for populating matches
	 *
	 * @param matchRecyclerView the RecyclerView that will be populated with matches
	 */
	public void enableAddFriendsList(RecyclerView matchRecyclerView, Context viewContext) {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewContext);
		matchRecyclerView.setLayoutManager(layoutManager);

		initiateAddFriendPresenter();

		addFriendsAdapter = new AddFriendsAdapter(userDatabase, addFriendActivity);
		matchRecyclerView.setAdapter(addFriendsAdapter);
	}

	private void initiateAddFriendPresenter() {

		addFriendActivity.disableViewInteraction();
		List<User> myFriends = BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser().getFriends();


		BoardbookSingleton.getInstance().getUserHandler().all().thenAccept(allUsers -> {
			if (allUsers != null && myFriends != null) {

				allUsers.remove(BoardbookSingleton.getInstance().getAuthHandler().getLoggedInUser());

				List<User> notMyFriends = allUsers
					.stream()
					.filter(user -> myFriends.stream().noneMatch(friend -> friend.getId().equals(user.getId())))
					.collect(Collectors.toList());

				userDatabase.addAll(notMyFriends);
				all.addAll(notMyFriends);


				new android.os.Handler(Looper.getMainLooper()).post(() -> {
					addFriendActivity.enableViewInteraction();
					repopulateFriends();
				});

			}
		}).exceptionally(e -> {
				new android.os.Handler(Looper.getMainLooper()).post(() -> {
					addFriendActivity.enableViewInteraction();
					addFriendActivity.displayLoadingFailed();
				});
				return null;
			}
		);
	}

	public void searchNonFriends(String query) {
		addFriendsAdapter.getFilter().filter(query);

	}

}