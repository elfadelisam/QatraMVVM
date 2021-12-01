package com.example.qatramvvm.ui.donors;

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

import com.example.qatramvvm.DetailsDonors;
import com.example.qatramvvm.R;
import com.example.qatramvvm.pojo.DonorsModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.ViewHolder> implements Filterable {
    private List<DonorsModel> donorsModelList;
    private List<DonorsModel> donorsListAll;
    private Context context;

    public DonorsAdapter(List<DonorsModel> donorsModelList, Context context) {
        this.donorsModelList = donorsModelList;
        donorsListAll = new ArrayList<>(donorsModelList);
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
        final DonorsModel donorsModel = donorsModelList.get(position);
        holder.txtBloodType.setText(donorsModel.getBlood_type());
        holder.txtName.setText(donorsModel.getDonor_name());
        holder.txtEmail.setText(donorsModel.getPhone());
        holder.txtPhone.setText(donorsModel.getLast_donation_date());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsDonors.class);
                intent.putExtra("blood_type",donorsModel.getBlood_type());
                intent.putExtra("donor_name",donorsModel.getDonor_name());
                intent.putExtra("donor_id",donorsModel.getDonor_id());
                intent.putExtra("email",donorsModel.getEmail());
                intent.putExtra("phone",donorsModel.getPhone());
                intent.putExtra("address",donorsModel.getAddress());
                intent.putExtra("age",donorsModel.getAge());
                intent.putExtra("gender",donorsModel.getGender());
                intent.putExtra("last_donation_date",donorsModel.getLast_donation_date());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donorsModelList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<DonorsModel> filteredList = new ArrayList<>();
            if (searchText.isEmpty() || searchText.length() == 0){
                filteredList.addAll(donorsListAll);
            }
            else {
                for (DonorsModel donor : donorsListAll){
                    if(donor.getDonor_name().toLowerCase().contains(searchText) || donor.getBlood_type().toLowerCase().contains(searchText)){
                        filteredList.add(donor);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            donorsModelList.clear();
            donorsModelList.addAll((Collection<? extends DonorsModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtBloodType, txtName, txtPhone, txtEmail;
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
