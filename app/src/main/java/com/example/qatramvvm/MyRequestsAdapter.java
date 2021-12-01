package com.example.qatramvvm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qatramvvm.pojo.CasesModel;

import java.util.List;

public class MyRequestsAdapter extends RecyclerView.Adapter<MyRequestsAdapter.ViewHolder> {
    private List<CasesModel> casesModelList;
    private Context context;

    public MyRequestsAdapter(List<CasesModel> casesModelList, Context context) {
        this.casesModelList = casesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitems, viewGroup, false);
        return new MyRequestsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CasesModel casesModel = casesModelList.get(i);
        viewHolder.txtBloodType.setText(casesModel.getBlood_type());
        viewHolder.txtName.setText(casesModel.getCase_name());
        viewHolder.txtEmail.setText(casesModel.getDescription());
        viewHolder.txtPhone.setText(casesModel.getPhone());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailsMyRequestActivity.class);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
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
