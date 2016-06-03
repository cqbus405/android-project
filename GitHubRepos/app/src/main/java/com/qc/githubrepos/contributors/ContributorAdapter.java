package com.qc.githubrepos.contributors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc.githubrepos.R;
import com.qc.githubrepos.data.Owner;
import com.qc.githubrepos.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by qiongchen on 6/3/16.
 */
public class ContributorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Owner> owners;

    private Context mContext;

    private View mFooterView;

    public ContributorAdapter(Context mContext) {
        this.mContext = mContext;
        owners = new ArrayList<>();
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder {

        ImageView mAvatar;

        TextView mName;

        TextView mLink;

        public ContributorViewHolder(View itemView) {
            super(itemView);

            mAvatar = (ImageView) itemView.findViewById(R.id.list_item_avatar);

            mName = (TextView) itemView.findViewById(R.id.list_item_name);

            mLink = (TextView) itemView.findViewById(R.id.list_item_link);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contributor, null);

            return new ContributorViewHolder(view);

        } else if (viewType == Constants.TYPE_FOOTER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, null);

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            mFooterView = view;

            return new FooterViewHolder(view);
        }

        throw new RuntimeException("There is no type that matches the type " + viewType + ". Make sure your using types are correct.");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContributorViewHolder) {
            holder.setIsRecyclable(false);

            String avatarUrl = owners.get(position).getAvatarUrl();
            String name = owners.get(position).getLogin();
            String link = owners.get(position).getHomePage();

            Picasso.with(mContext)
                    .load(avatarUrl)
                    .into(((ContributorViewHolder) holder).mAvatar);

            ((ContributorViewHolder) holder).mName.setText(name);
            ((ContributorViewHolder) holder).mLink.setText(link);
        }
    }

    @Override
    public int getItemCount() {
        return owners.size() + 1;
    }

    public void setFooterVisible(boolean isVisible) {
        if (mFooterView != null) {
            mFooterView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setContributors(ArrayList<Owner> owners) {
        this.owners.clear();
        this.owners.addAll(owners);
        this.notifyDataSetChanged();
    }

    public void updateContributors(ArrayList<Owner> owners) {
        this.owners.addAll(owners);
        this.notifyItemRangeInserted(this.owners.size(), owners.size());
    }
}
