package com.jstechnologies.helpinghands.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jstechnologies.helpinghands.R;
import com.jstechnologies.helpinghands.data.model.ServiceModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    ArrayList<ServiceModel> models;
    int limit;

    public ServiceAdapter() {
        models= new ArrayList<>();
        this.limit=-1;
    }

    public ServiceAdapter(ArrayList<ServiceModel> models) {
        this.models = models;
        this.limit=models.size();
    }

    public ServiceAdapter(ArrayList<ServiceModel> models, int limit) {
        this.models = models;
        this.limit = limit;
    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<ServiceModel> getModels() {
        return models;
    }

    public int getLimit() {
        return limit;
    }

    public void setModels(List<ServiceModel> models) {
        this.models.clear();
        this.models.addAll(models);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dealer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        if(limit<0)
            return models.size();
        return models.size()<limit?models.size():limit;
    }


    protected class ServiceHolder extends RecyclerView.ViewHolder {
        TextView name,address,tags,verified,rating,pVotes,nVotes,desc,distance;
        CircleImageView profileImage;
        LinearLayout layout;
        Context context;
        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            layout=itemView.findViewById(R.id.layout);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.short_address);
            tags=itemView.findViewById(R.id.tags);
            verified=itemView.findViewById(R.id.verified);
            rating=itemView.findViewById(R.id.rating);
            pVotes=itemView.findViewById(R.id.positiveVotes);
            distance=itemView.findViewById(R.id.distance);
            nVotes=itemView.findViewById(R.id.negativeVotes);
            profileImage=itemView.findViewById(R.id.profile_image);
            desc=itemView.findViewById(R.id.about);
            context=itemView.getContext();
        }
        void bind(ServiceModel dealer)
        {
            int bgColor=dealer.isVerified()?R.color.colorLightGreen:R.color.colorBg;
            layout.setBackgroundColor(context.getResources().getColor(bgColor));
            name.setText(dealer.getEnterpriseName());
            String _address=dealer.getAddress()!=null?dealer.getAddress().getShortAddress():"Location not specified, ";
            distance.setText(((int)dealer.getDistance())+" km away");
            address.setText(_address);
            tags.setText("");
            for(String deal:dealer.getDealsIn())
                tags.append(deal+ ", ");
            tags.append("etc.");
            desc.setText(dealer.getDescription());
            verified.setVisibility(dealer.isVerified()?View.VISIBLE:View.GONE);
            rating.setText(String.valueOf(dealer.getRating()).substring(0,3));
            pVotes.setText(dealer.getPositiveVoteCount()+" votes");
            nVotes.setText(dealer.getNegativeVoteCount()+" dislikes");
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                    {
                        onItemClickListener.OnItemClick(dealer,getAdapterPosition());
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(ServiceModel dealer,int index);
    }
}
