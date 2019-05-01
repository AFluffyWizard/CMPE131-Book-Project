package cmpe131.cmpebookproject.activity.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import cmpe131.cmpebookproject.R;
import cmpe131.cmpebookproject.RecyclerViewListableAdapter;
import cmpe131.cmpebookproject.Util;
import cmpe131.cmpebookproject.activity.UserListsActivity;
import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.user.User;

public class ListTabFragment extends Fragment {

    User activeUser;
    LayoutInflater inflater;
    ArrayList<Book> recommendedBooks;

    // newInstance constructor for creating fragment with arguments
    public static ListTabFragment newInstance(User user) {
        ListTabFragment tabBaseFragment = new ListTabFragment();
        Bundle args = new Bundle();
        args.putParcelable(Util.KEY_DATA_ACTIVEUSER, user);
        tabBaseFragment.setArguments(args);
        return tabBaseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeUser = getArguments().getParcelable(Util.KEY_DATA_ACTIVEUSER);
        recommendedBooks = activeUser.getRecommendedList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_main_tab_list, container, false);

        RecyclerView recommendedList = view.findViewById(R.id.tab_list_layout_listrecyclerview);
        recommendedList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewListableAdapter recyclerViewAdapter = new RecyclerViewListableAdapter(recommendedBooks, R.layout.listitem_book/*recommendedBooks.get(0).getListViewLayoutRes()*/, true);
        recommendedList.setAdapter(recyclerViewAdapter);


        Button customListsButton = view.findViewById(R.id.tab_list_button_customlists);
        customListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userListsIntent = new Intent(getContext(), UserListsActivity.class);
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_LISTTITLE, "Custom Lists");
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_LAYOUTRES, R.layout.listitem_booklist);
                userListsIntent.putExtra(Util.INTENT_DATA_LIST_DATASET, activeUser.getCustomLists());
                userListsIntent.putExtra(Util.INTENT_DATA_USER, (Parcelable)activeUser);
                startActivity(userListsIntent);

                /*
                Recommender r = new Recommender(activeUser, DbHelper.getInstance(getContext()).getAllBooks(),10);
                r.makeRecommendedBookList();
                System.out.println("SIZE OF RECOMMENDED BOOK LIST: " + activeUser.getRecommendedList().size());
                populateRecommendedList(recommendedList);
                */
            }
        });

        return view;
    }

    /*
    private void populateRecommendedList(LinearLayout recommendedList) {
        ArrayList<Book> testBooks = new ArrayList<>();
        ArrayList<Book> allBooks = DbHelper.getInstance(getContext()).getAllBooks();
        testBooks.add(allBooks.get(0));
        testBooks.add(allBooks.get(1));
        testBooks.add(allBooks.get(2));
        testBooks.add(allBooks.get(3));
        testBooks.add(allBooks.get(4));
        testBooks.add(allBooks.get(5));

        for (Book b : testBooks)
        //for (Book b : activeUser.getRecommendedList())
            recommendedList.addView(b.getListView(inflater));
    }
    */
}
