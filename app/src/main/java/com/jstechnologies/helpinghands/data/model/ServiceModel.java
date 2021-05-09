package com.jstechnologies.helpinghands.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "services_cache" )
public class ServiceModel {
    @NonNull
    @PrimaryKey
    String _id;

    String enterpriseName,phone;
    List<String> dealsIn;
    boolean isVerified=false;
    String email;
    long createdAt;
    List<Boolean> votes;
    String description;
    List<String> tags;
    @Nullable
    double score;
    @Nullable
    double distance;
    @Embedded
    Address address;


    public ServiceModel() {
        tags=new ArrayList<>();
        votes=new ArrayList<>();
        dealsIn=new ArrayList<>();
        createdAt=System.currentTimeMillis();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public List<Boolean> getVotes() {
        return votes;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getRating() {

        int count=getPositiveVoteCount();
        if(count<=0)
            return 0;
        return ((float) getPositiveVoteCount()*5/votes.size());
    }

    public int getPositiveVoteCount()
    {
        int count=0;
        if(votes!=null && votes.size()>0)
            for(boolean v:votes)
                if(v)
                    ++count;
        return count;
    }
    public int getNegativeVoteCount()
    {
        int count=0;
        if(votes!=null && votes.size()>0)
            for(boolean v:votes)
                if(!v)++count;
        return count;
    }

    public void setVotes(List<Boolean> votes) {
        this.votes = votes;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getDealsIn() {
        return dealsIn;
    }

    public void setDealsIn(List<String> dealsIn) {
        this.dealsIn = dealsIn;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }


    public List<String> getTags() {
        return tags;
    }
    public String getDealsInAsString()
    {
        String s="";
        for(String t:dealsIn)
        {
            if(t!=null && !t.isEmpty())
            {
                s+=t+", ";
            }

        }
        return s;
    }
    public String getTagsAsString()
    {
        String s="";
        for(String t:tags)
        {
            if(t!=null && !t.isEmpty())
            {
                if(t.charAt(0)==' ')
                    t=t.substring(1);
                if(t.charAt(0)!='#')
                    s+="#"+t;
                else
                    s+=t;
                s+=", ";
            }

        }
        return s;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceModel dealer = (ServiceModel) o;
        return _id.equals(dealer._id);
    }

}
