package com.kurotkin.recyclerviewtest.mock;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurotkin.recyclerviewtest.R;

import java.util.ArrayList;
import java.util.List;

public class MockAdapter extends RecyclerView.Adapter<MockHolder> {

    private final List<Mock> mMockList = new ArrayList<>();

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ls_item_mock, parent, false);
        return new MockHolder(view);
    }

    @Override
    public void onBindViewHolder(MockHolder holder, int position) {
        holder.bind(mMockList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMockList.size();
    }

    public void addDate(List<Mock> mocks, boolean refresh){
        if(refresh){
            mMockList.clear();
        }
        mMockList.addAll(mocks);
        notifyDataSetChanged(); // обновить
    }
}
