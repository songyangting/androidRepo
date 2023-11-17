package com.example.practical10;

import android.content.Context;
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
    NoteDatabase noteDatabase;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MyAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.notes_item, parent);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (noteList.isEmpty()) {
            holder.titleTxt.setText("Notes Title");
            holder.contentTxt.setText("Set up your notes content!");
        } else {
            holder.getTitleTxt().setText(noteList.get(position).getContent());
            holder.getContentTxt().setText(noteList.get(position).getTitle());

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    int noteId = noteList.get(adapterPosition).id;
                    executorService.execute(new DeleteNoteRunnable(noteId));

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
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
            Note note = noteDatabase.noteDao().getNoteById(noteId);
            noteDatabase.noteDao().delete(note);
        }
    }

//    private class DisplayNoteRunnable implements Runnable {
//        private final int noteId;
//
//        DisplayNoteRunnable(int noteId) {
//            this.noteId = noteId;
//        }
//
//        @Override
//        public void run() {
//            Note note = noteDatabase.noteDao().getNoteById(noteId);
//            if (note != null) {
//                // display note
//
//            } else {
//                // set default values for title and content
//            }
//        }
//    }
}
