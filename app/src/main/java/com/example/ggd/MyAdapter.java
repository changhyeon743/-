package com.example.ggd;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        String text = list.get(position);
        holder.button.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button button;
        MyViewHolder(View view){
            super(view);
            button = (Button)view.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, SeekActivity.class);
                        intent.putExtra("station", list.get(pos));
                        context.startActivity(intent);
                    }
                }
            });
        }


    }

}
