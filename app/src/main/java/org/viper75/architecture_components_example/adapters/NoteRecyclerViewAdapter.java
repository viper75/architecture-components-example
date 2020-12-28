package org.viper75.architecture_components_example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.viper75.architecture_components_example.databinding.NoteItemLayoutBinding;
import org.viper75.architecture_components_example.models.Note;

public class NoteRecyclerViewAdapter extends ListAdapter<Note, NoteRecyclerViewAdapter.NoteItemViewHolder> {

    public static class NoteItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteTitleTV;
        private final TextView noteDescTV;
        private final TextView notePriorityTV;
        private final CardView root;

        public NoteItemViewHolder(@NonNull NoteItemLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());

            noteTitleTV = layoutBinding.noteTitle;
            noteDescTV = layoutBinding.noteDesc;
            notePriorityTV = layoutBinding.notePriority;
            root = layoutBinding.getRoot();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    public NoteRecyclerViewAdapter(@NonNull Context context) {
        super(DIFF_CALLBACK);

        mLayoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteItemViewHolder(NoteItemLayoutBinding.inflate(mLayoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        Note note = getItem(position);

        holder.noteTitleTV.setText(note.getTitle());
        holder.noteDescTV.setText(note.getDescription());
        holder.notePriorityTV.setText(String.valueOf(note.getPriority()));

        holder.root.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getItem(position));
            }
        });
    }

    public Note getNoteAtPosition(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
