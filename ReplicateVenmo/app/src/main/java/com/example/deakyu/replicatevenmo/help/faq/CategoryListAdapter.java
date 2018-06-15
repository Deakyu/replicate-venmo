package com.example.deakyu.replicatevenmo.help.faq;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deakyu.replicatevenmo.R;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout row;
        public TextView category;

        public ViewHolder(View iv) {
            super(iv);

            row = iv.findViewById(R.id.category_row);
            category = iv.findViewById(R.id.category_text);
        }
    }

    private final LayoutInflater inflater;
    private List<Category> categories;
    private FAQContract.Presenter presenter;

    public CategoryListAdapter(Context context) { this.inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int pos) {
        Category currentCategory = categories.get(pos);

        // TODO: Not a great solution
        if(currentCategory.getTopics().get(0).getTopic().equals("empty")) {
            vh.category.setTextSize(22.0f);
            vh.category.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            vh.category.setText(currentCategory.getCategory());
        } else {
            vh.row.setOnClickListener(v -> {
                presenter.onTopicItemClicked(currentCategory.getTopics().get(0).getTopic(), currentCategory.getTopics().get(0).getDescription());
            });
            vh.category.setText(currentCategory.getTopics().get(0).getTopic());
        }
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        this.notifyDataSetChanged();
    }

    public void setPresenter(FAQContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
}
