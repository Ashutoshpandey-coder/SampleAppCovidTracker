package com.example.covid360;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class CovidAdapter extends RecyclerView.Adapter<CovidAdapter.ViewHolder> implements Filterable {
    private final ArrayList<Model> list;
    private final ArrayList<Model> searchList;
    private final Context context;

    public CovidAdapter( Context context,ArrayList<Model> list) {
        this.list = list;
        this.context = context;
        this.searchList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public CovidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidAdapter.ViewHolder holder, int position) {
        Model items = list.get(position);

        holder.state.setText(items.getState());
        holder.active.setText(items.getActive());
        holder.deaths.setText(items.getDeaths());
        holder.confirmed.setText(items.getConfirmed());
        holder.recovered.setText(items.getRecovered());

            holder.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Model> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(searchList);
            }else{
                for(Model list : searchList){
                    if (list.getState().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(list);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends Model>) results.values);
            notifyDataSetChanged();

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView state;
        private TextView active;
        private TextView deaths;
        private TextView recovered;
        private TextView confirmed;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.tv_state);
            active = itemView.findViewById(R.id.tv_active);
            deaths = itemView.findViewById(R.id.tv_deaths);
            recovered = itemView.findViewById(R.id.tv_recovered);
            confirmed = itemView.findViewById(R.id.tv_confirmed);
            linearLayout = itemView.findViewById(R.id.ll_number_cases_per_state);
        }
    }
}
