package com.jstechnologies.helpinghands.ui.views.serviceDisplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jstechnologies.helpinghands.R;
import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.views.base.BaseActivity;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModel;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModelFactory;
import com.jstechnologies.helpinghands.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

public class ServiceDisplayActivity extends BaseActivity<ServiceDisplayViewModel> {

    Toolbar toolbar;
    TextView shortAddress,distance,rating,pVotes,nVotes,verified,email,phone,desc,dealsIn,address,tags,likeNow,dislikeNow;
    int result=RESULT_CANCELED;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_display);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerViewComponents();
        alert=new AlertDialog.Builder(this);
    }

    @NonNull
    @Override
    protected ServiceDisplayViewModel createViewModel() {
        String data=getIntent().getStringExtra("service_data");
        ServiceDisplayViewModelFactory viewModelFactory= new ServiceDisplayViewModelFactory(new Gson().fromJson(data,ServiceModel.class));
        return new ViewModelProvider(this,viewModelFactory).get(ServiceDisplayViewModel.class);
    }

    @Override
    protected void onMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void observe() {
        viewmodel.getIsLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        viewmodel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                onMessage(s);
            }
        });

       viewmodel.getServicesLiveData().observe(this, new Observer<ServiceModel>() {
           @Override
           public void onChanged(ServiceModel serviceModel) {
               populate(serviceModel);
           }
       });
        viewmodel.getHasLiked().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    likeNow.setVisibility(View.GONE);
                    dislikeNow.setVisibility(View.GONE);
                }

            }
        });
    }

    private void populate(ServiceModel dealer) {
        getSupportActionBar().setTitle(dealer.getEnterpriseName());
        String _address=dealer.getAddress()!=null?dealer.getAddress().getShortAddress():"Location not specified, ";
        distance.setText(((int)dealer.getDistance())+" km away");
        String _email=dealer.getEmail()!=null && !dealer.getEmail().isEmpty()?dealer.getEmail():"No Email specified";
        email.setText(_email);
        phone.setText("+91-"+dealer.getPhone());
        address.setText(_address);
        tags.setText(dealer.getTagsAsString());
        dealsIn.setText("");
        for(String deal:dealer.getDealsIn())
            dealsIn.append(deal+ ", ");
        dealsIn.append("etc.");
        desc.setText(dealer.getDescription());
        verified.setVisibility(dealer.isVerified()?View.VISIBLE:View.GONE);
        rating.setText(String.valueOf(dealer.getRating()).substring(0,3));
        pVotes.setText(dealer.getPositiveVoteCount()+" votes");
        nVotes.setText(dealer.getNegativeVoteCount()+" dislikes");
    }

    private void registerViewComponents() {

        distance=findViewById(R.id.distance);
        shortAddress=findViewById(R.id.short_address);
        rating=findViewById(R.id.rating);
        pVotes=findViewById(R.id.positiveVotes);
        nVotes=findViewById(R.id.negativeVotes);
        verified=findViewById(R.id.verified);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        desc=findViewById(R.id.desc);
        dealsIn=findViewById(R.id.dealsIn);
        address=findViewById(R.id.address);
        tags=findViewById(R.id.tags);
        likeNow=findViewById(R.id.likeNow);
        dislikeNow=findViewById(R.id.dislikeNow);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            setResult(result);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCallClick(View view) {
        IntentUtils.startCallIntent(this,viewmodel.getServicesLiveData().getValue().getPhone());
    }

    public void onLikeToggle(View view) {
        alert.setTitle("Are you sure?");
        alert.setMessage("You are about to vote for this service. Please make sure you are voting for the correct service. You cannot retry this later");
        alert.setCancelable(true);
        alert.setPositiveButton("Like Me", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewmodel.toggleLike();
                result=RESULT_OK;
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }

    @Override
    public void onBackPressed() {
        setResult(result);
        super.onBackPressed();
    }

    public void onDislikeToggle(View view) {
        alert.setTitle("Are you sure?");
        alert.setMessage("You are about to down vote for this service. Please make sure you are down voting for the correct service. You cannot retry this later");
        alert.setCancelable(true);
        alert.setPositiveButton("Dislike Me", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewmodel.toggleDislike();
                result=RESULT_OK;
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}