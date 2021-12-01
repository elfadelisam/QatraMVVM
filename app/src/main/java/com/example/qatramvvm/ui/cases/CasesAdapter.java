package com.example.qatramvvm.ui.cases;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qatramvvm.DetailsCases;
import com.example.qatramvvm.R;
import com.example.qatramvvm.pojo.CasesModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.ViewHolder> implements Filterable {
    private List<CasesModel> casesModelList;
    private List<CasesModel> casesListAll;
    private Context context;

    public CasesAdapter(List<CasesModel> casesModelList, Context context) {
        this.casesModelList = casesModelList;
        casesListAll = new ArrayList<>(casesModelList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitems, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CasesModel casesModel = casesModelList.get(position);
        holder.txtBloodType.setText(casesModel.getBlood_type());
        holder.txtName.setText(casesModel.getCase_name());
        holder.txtEmail.setText(casesModel.getDescription());
        holder.txtPhone.setText(casesModel.getPhone());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsCases.class);
                intent.putExtra("case_id",casesModel.getCase_id());
                intent.putExtra("blood_type",casesModel.getBlood_type());
                intent.putExtra("case_name",casesModel.getCase_name());
                intent.putExtra("description",casesModel.getDescription());
                intent.putExtra("phone",casesModel.getPhone());
                intent.putExtra("hospital",casesModel.getHospital());
                intent.putExtra("age",casesModel.getAge());
                intent.putExtra("gender",casesModel.getGender());
                intent.putExtra("date",casesModel.getDate());
                intent.putExtra("num",casesModel.getNum());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return casesModelList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<CasesModel> filteredList = new ArrayList<>();
            if (searchText.isEmpty() || searchText.length() == 0){
                filteredList.addAll(casesListAll);
            }
            else {
                for (CasesModel case_search : casesListAll){
                    if(case_search.getCase_name().toLowerCase().contains(searchText) || case_search.getBlood_type().toLowerCase().contains(searchText)){
                        filteredList.add(case_search);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            casesModelList.clear();
            casesModelList.addAll((Collection<? extends CasesModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtBloodType, txtName, txtEmail, txtPhone;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBloodType = itemView.findViewById(R.id.txtBloodType);
            txtName      = itemView.findViewById(R.id.txtname);
            txtEmail     = itemView.findViewById(R.id.txtemail);
            txtPhone     = itemView.findViewById(R.id.txtphone);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
