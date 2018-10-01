package com.kurotkin.recyclerviewtest.mock;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kurotkin.recyclerviewtest.ContactsAdapter;
import com.kurotkin.recyclerviewtest.R;

public class MockHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView value;
    private String mId;


    public MockHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        value = itemView.findViewById(R.id.value);
    }

    public void bind(Mock mock) {
        name.setText(mock.getName());
        value.setText(mock.getValue());
        mId = mock.getValue();
    }

    public void setListener(final ContactsAdapter.OnClickListerner listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mId);
            }
        });
    }
}
