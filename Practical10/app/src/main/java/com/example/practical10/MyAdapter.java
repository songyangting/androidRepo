package com.example.practical10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    List<Note> noteList = new ArrayList<Note>();
    Context context;
    NoteDao noteDao;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private View recyclerView;
    private View emptyView;

    public MyAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }


    // constructor that takes in the noteDao meaning we have access to the db
    public MyAdapter(NoteDao noteDao, View recyclerView, View emptyView) {
        this.noteDao = noteDao;
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
//        View view = View.inflate(parent.getContext(), R.layout.notes_item, parent);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note currentNote = noteList.get(position);

        holder.getTitleTxt().setText(currentNote.getTitle());
        holder.getContentTxt().setText(currentNote.getContent());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noteId = currentNote.id;
                Log.i("Note ID", ""+noteId);
                executorService.execute(new DeleteNoteRunnable(noteId));

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
        // to hold reference to your views
        TextView titleTxt, contentTxt;
        Button deleteBtn;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.titleTxt = (TextView) view.findViewById(R.id.title_txt);
            this.contentTxt = (TextView) view.findViewById(R.id.notes_txt);
            this.deleteBtn = (Button) view.findViewById(R.id.delete_btn);
        }

        public TextView getTitleTxt() {
            return titleTxt;
        }

        public TextView getContentTxt() {
            return contentTxt;
        }
    }

    private class DeleteNoteRunnable implements Runnable {
        private final int noteId;

        DeleteNoteRunnable(int noteId) {
            this.noteId = noteId;
        }

        @Override
        public void run() {
            //Log.i("Note ID", ""+noteId);
            Note note = noteDao.getNoteById(noteId);
            //Log.i("Note", "Note title: "+ note.getTitle()+", Note content: "+ note.getContent());
            noteDao.delete(note);
        }
    }

}
