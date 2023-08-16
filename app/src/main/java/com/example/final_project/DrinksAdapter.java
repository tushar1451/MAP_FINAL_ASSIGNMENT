package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.Model.Drink;

import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.TasksViewHolder> {

    interface drinkClickListener {
        public void drinkClicked(Drink selectedDrink);
    }

    private Context mCtx;
    public List<Drink> drinkList;
    private final drinkClickListener listener;

    public DrinksAdapter(Context mCtx, List<Drink> drinkList, drinkClickListener listenerFromActivity) {
        this.mCtx = mCtx;
        this.drinkList = drinkList;
        listener = listenerFromActivity;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_drinks, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Drink t = drinkList.get(position);
        holder.drinkTextView.setText(t.getMealName() + ", " + t.getArea());
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }


    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView drinkTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);

            drinkTextView = itemView.findViewById(R.id.drinkText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Drink drink = drinkList.get(getAdapterPosition());
            listener.drinkClicked(drink);

        }
    }

}
