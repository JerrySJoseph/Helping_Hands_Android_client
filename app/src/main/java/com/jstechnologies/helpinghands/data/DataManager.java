package com.jstechnologies.helpinghands.data;

import com.jstechnologies.helpinghands.data.api.reviews.LocalReviewsApi;
import com.jstechnologies.helpinghands.data.api.reviews.RemoteReviewsApi;
import com.jstechnologies.helpinghands.data.api.service.LocalServiceApi;
import com.jstechnologies.helpinghands.data.api.service.RemoteServiceApi;
import com.jstechnologies.helpinghands.data.repository.reviews.ReviewsRepository;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;

public class DataManager {

    //Singleton Instance
    private static DataManager mInstance;

    //Repository Instances
    ServiceRepository serviceRepository;
    ReviewsRepository reviewsRepository;

    //API instances
    LocalReviewsApi localReviewsApi;
    RemoteReviewsApi remoteReviewsApi;
    LocalServiceApi localServiceApi;
    RemoteServiceApi remoteServiceApi;

    private static synchronized DataManager getInstance(){
        if(mInstance==null)
            mInstance=new DataManager();
        return mInstance;
    }

    private DataManager()
    {
        //This constructor should not be accessed from outside this class;
    }



}
