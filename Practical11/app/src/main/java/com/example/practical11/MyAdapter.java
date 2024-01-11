package com.example.practical11;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Note> noteList = new ArrayList<Note>();
    private View recyclerView;
    private View emptyView;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("notes");

    public MyAdapter() {
        // need to parse database reference to get access to objects
    }

    public MyAdapter(DatabaseReference myRef, View recyclerView, View emptyView){
        this.myRef = myRef;
        this.recyclerView = recyclerView;
        this.emptyView = emptyView;
        showEmptyView();
    }

    private void showEmptyView() {
        if (noteList.size() == 0){
            // no notes
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note currentNote = noteList.get(position);

        holder.getTitle_txt().setText(noteList.get(position).getTitle());
        holder.getContent_txt().setText(noteList.get(position).getContent());

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = currentNote.getKey();

                //Toast.makeText(recyclerView.getContext(), key, Toast.LENGTH_LONG).show();
                myRef.child(key).removeValue();

            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNote(List<Note> noteList) {
        this.noteList = noteList;
        showEmptyView();
        notifyDataSetChanged(); //important to have this
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_txt, content_txt;
        Button delete_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_txt = (TextView) itemView.findViewById(R.id.title_txt);
            content_txt = (TextView) itemView.findViewById(R.id.notes_txt);
            delete_btn = (Button) itemView.findViewById(R.id.delete_btn);
        }

        public TextView getTitle_txt() {
            return title_txt;
        }

        public TextView getContent_txt() {
            return content_txt;
        }
    }


}
