package com.jstechnologies.helpinghands.ui.views.dashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.jstechnologies.helpinghands.R;
import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.adapters.ServiceAdapter;
import com.jstechnologies.helpinghands.ui.views.addService.AddServiceActivity;
import com.jstechnologies.helpinghands.ui.views.base.BaseActivity;
import com.jstechnologies.helpinghands.ui.views.search.SearchableActivity;
import com.jstechnologies.helpinghands.ui.views.serviceDisplay.ServiceDisplayActivity;
import com.jstechnologies.helpinghands.utils.AnimationUtils;

import java.util.ArrayList;
import java.util.List;


public class DashBoardActivity extends BaseActivity<DashBoardViewModel> {

    LinearLayout tagsView;
    TextView dealers_viewall,location;
    ExtendedFloatingActionButton fab;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView dealersRecyclerView;
    ServiceAdapter serviceAdapter;
    Toolbar toolbar;
    int h=0;

    private static int RC_ADD_SERVICE=101;
    private static int RC_SERVICE_DISPLAY=102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        registerViewComponents();
        setSupportActionBar(toolbar);
        viewmodel.fetchAddress(this);
        viewmodel.fetchData();
    }

    @NonNull
    @Override
    protected DashBoardViewModel createViewModel() {
        DashBoardViewModelFactory viewModelFactory= new DashBoardViewModelFactory(ServiceRepository.getInstance());
        return new ViewModelProvider(this,viewModelFactory).get(DashBoardViewModel.class);
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
                if(aBoolean)
                    serviceAdapter.setModels(new ArrayList<>());
                shimmerFrameLayout.setVisibility(aBoolean?View.VISIBLE:View.GONE);
            }
        });

        viewmodel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
               onMessage(s);
            }
        });
        viewmodel.getRepositoryMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                onMessage(s);
            }
        });
        viewmodel.getServicesListLiveData().observe(this, new Observer<List<ServiceModel>>() {
            @Override
            public void onChanged(List<ServiceModel> serviceModels) {
                serviceAdapter.setModels(serviceModels);
            }
        });
        viewmodel.getAddressLiveData().observe(this, new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                location.setText(address.getShortAddress());
            }
        });
    }

    void registerViewComponents() {
        location=findViewById(R.id.location);
        dealers_viewall = findViewById(R.id.dealers_viewall);
        tagsView = findViewById(R.id.tagView);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        fab = findViewById(R.id.fab);
        dealersRecyclerView = findViewById(R.id.recycler_dealers);
        serviceAdapter= new ServiceAdapter();
        dealersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealersRecyclerView.setAdapter(serviceAdapter);
        fab.shrink();
        toolbar=findViewById(R.id.toolbar);
        dealers_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, SearchableActivity.class));
            }
        });
        serviceAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ServiceModel dealer, int index) {
                startActivityForResult(getSelectedIntentFor(dealer),RC_SERVICE_DISPLAY);
            }
        });

    }

    public void onMenuClick(View view) {
    }

    public void onfabClick(View view) {
        startActivityForResult(getAddServiceIntent(),RC_ADD_SERVICE);
    }

    private Intent getAddServiceIntent()
    {
        return new Intent(this, AddServiceActivity.class);
    }
    private Intent getSelectedIntentFor(ServiceModel model)
    {
        Intent intent= new Intent(this,ServiceDisplayActivity.class);
        intent.putExtra("service_data",new Gson().toJson(model));
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
            viewmodel.fetchData();
    }


    public void onRefreshClick(View view) {
        viewmodel.fetchData();
    }
}