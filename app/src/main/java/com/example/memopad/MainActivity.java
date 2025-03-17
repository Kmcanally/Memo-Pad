package com.example.memopad;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.memopad.databinding.ActivityMainBinding;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbstractView {

    private ActivityMainBinding binding;
    private DefaultController controller;
    private List<Memo> memoData;
    private int memoToDelete = -1;
    MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DefaultModel model = new DefaultModel(new DatabaseHandler(this,null,null,1));
        model.initDefault();
        controller = new DefaultController(model);
        controller.addView(this);
        controller.getAllMemos();

        ClickHandler click = new ClickHandler();
        binding.addButton.setOnClickListener(click);
        binding.deleteButton.setOnClickListener(click);
    }


    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        if ( propertyName.equals(DefaultController.ELEMENT_GETALLMEMOS_PROPERTY) ) {
            String oldPropertyValue = evt.getOldValue().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                memoData = (List<Memo>) evt.getNewValue();
                updateRecyclerView();
            }
        }
    }

    private void updateRecyclerView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, memoData);

        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);
    }


    private class ClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String tag = v.getTag().toString();

            if (tag.equals("addMemo")) {
                String newText = binding.editText.getText().toString();
                controller.bAdd(newText);
            }

            else if (tag.equals("deleteMemo")) {
                controller.bDelete(memoToDelete);
                memoToDelete = -1;
            }
        }
    }
    private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = binding.output.getChildLayoutPosition(v);

            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.output.getAdapter();

            if (adapter != null) {
                Memo memo = adapter.getMemo(position);
                int id = memo.getId();
                memoToDelete = id;
            }
        }
    }

    public MemoPadItemClickHandler getItemClick() { return itemClick; }
}