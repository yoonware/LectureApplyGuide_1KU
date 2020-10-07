package com.onekus.onekus;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Lecture> items;

    RecyclerAdapter(ArrayList<Lecture> itemList) {
        items = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Lecture item = items.get(i);
        int count = item.getPresent();
        int limit = item.getLimit();
        viewHolder.tvCode.setText(item.getCode());
        viewHolder.tvTitle.setText(item.getTitle());
        if (item.getProfessor().contains(",")) {
            viewHolder.tvProf.setText(" (" + item.getProfessor().substring(0, item.getProfessor().indexOf(",")) + ")");
        }
        else {
            viewHolder.tvProf.setText(" (" + item.getProfessor() + ")");
        }
        viewHolder.tvTime.setText(item.getSchedule());
        if (count >= limit) {
            SpannableString msg =  new SpannableString("인원초과");
            msg.setSpan(new ForegroundColorSpan(Color.parseColor("#FFBA131E")), 0, msg.length(), 0);
            viewHolder.tvEmpty.setText(msg);
        }
        else {
            viewHolder.tvEmpty.setText(limit - count + " 자리");
        }
        viewHolder.tvCount.setText(count + " / ");
        viewHolder.tvLimit.setText(limit + "");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;
        TextView tvTitle;
        TextView tvProf;
        TextView tvTime;
        TextView tvEmpty;
        TextView tvCount;
        TextView tvLimit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode  = itemView.findViewById(R.id.tvCode);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvProf  = itemView.findViewById(R.id.tvProf);
            tvTime  = itemView.findViewById(R.id.tvTime);
            tvEmpty = itemView.findViewById(R.id.tvEmpty);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvLimit = itemView.findViewById(R.id.tvLimit);
        }

    }

}