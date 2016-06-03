package com.qc.githubrepos.repolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qc.githubrepos.R;
import com.qc.githubrepos.data.Repo;
import com.qc.githubrepos.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by qiongchen on 6/2/16.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Repo> repos;

    private Context mContext;

    public OnItemClickListener mOnItemClickListener;

    private View mFooterView;

    public RepoListAdapter(Context mContext) {
        this.mContext = mContext;
        repos = new ArrayList<>();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;

        TextView mScore;

        TextView mCreated;

        TextView mDescription;

        public RepoViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.list_item_title);

            mScore = (TextView) itemView.findViewById(R.id.list_item_score);

            mCreated = (TextView) itemView.findViewById(R.id.list_item_created);

            mDescription = (TextView) itemView.findViewById(R.id.list_item_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(itemView, repos.get(getAdapterPosition()));
            }
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repo, null);
            return new RepoViewHolder(view);
        } else if (viewType == Constants.TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mFooterView = view;
            return new FooterViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + ". Make sure your using types are correct.");
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return Constants.TYPE_FOOTER;
        } else {
            return Constants.TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepoViewHolder) {
            holder.setIsRecyclable(false);

            String title = repos.get(position).getFullName();
            String description = repos.get(position).getDescription();
            String created = repos.get(position).getCreatedAt();
            double score = repos.get(position).getStar();

            try {
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                Date date = input.parse(created);
                String formattedTime = output.format(date);
                ((RepoViewHolder) holder).mCreated.setText("Created: " + formattedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ((RepoViewHolder) holder).mTitle.setText(title);
            ((RepoViewHolder) holder).mScore.setText(String.valueOf((int) score));
            ((RepoViewHolder) holder).mDescription.setText(description);
        }
    }

    @Override
    public int getItemCount() {
        return repos.size() + 1;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, Repo repo);

    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setRepos(ArrayList<Repo> repos) {
        this.repos.clear();
        this.repos.addAll(repos);
        this.notifyDataSetChanged();
    }

    public void upDateRepos(ArrayList<Repo> repos) {
        this.repos.addAll(repos);
        this.notifyItemRangeInserted(this.repos.size(), repos.size());
    }

    public void setFooterVisible(boolean isVisible) {
        if (mFooterView != null) {
            mFooterView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
