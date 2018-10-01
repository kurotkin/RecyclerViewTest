package com.kurotkin.recyclerviewtest;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurotkin.recyclerviewtest.mock.MockGenerator;

import java.util.Random;

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecycle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ContactsAdapter mContactsAdapter = new ContactsAdapter();
    private View mErrorView;
    private Random mRandom = new Random();

    private ContactsAdapter.OnClickListerner mListerner;

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ContactsAdapter.OnClickListerner)
            mListerner = (ContactsAdapter.OnClickListerner) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecycle = view.findViewById(R.id.recycle);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.errorview);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycle.setAdapter(mContactsAdapter);
        mContactsAdapter.setListerner(mListerner);
    }

    @Override
    public void onRefresh() {
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int count = mRandom.nextInt(4);
//                if(count == 0) {
//                    showError();
//                } else {
//                    showData(count);
//                }
//
//                if(mSwipeRefreshLayout.isRefreshing())
//                    mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);

        getLoaderManager().restartLoader(0, null, this);
    }

//    private void showData(int count) {
//        mMockAdapter.addDate(MockGenerator.generote(count), true);
//        mErrorView.setVisibility(View.GONE);
//        mRecycle.setVisibility(View.VISIBLE);
//    }
//
//    private void showError() {
//       mErrorView.setVisibility(View.VISIBLE);
//       mRecycle.setVisibility(View.GONE);
//    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null, null, ContactsContract.Contacts._ID
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mContactsAdapter.swapCursor(data);

        if(mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    @Override
    public void onDetach() {
        mListerner = null;
        super.onDetach();
    }
}
