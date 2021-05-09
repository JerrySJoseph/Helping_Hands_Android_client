package com.jstechnologies.helpinghands.ui.views.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jstechnologies.helpinghands.R;
import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.adapters.ServiceAdapter;
import com.jstechnologies.helpinghands.ui.views.base.BaseActivity;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModel;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModelFactory;
import com.jstechnologies.helpinghands.ui.views.serviceDisplay.ServiceDisplayActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends BaseActivity<DashBoardViewModel> {

    RecyclerView dealersRecyclerView;
    ServiceAdapter serviceAdapter;
    TextView count;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Services");
        dealersRecyclerView=findViewById(R.id.searchRecycler);
        count=findViewById(R.id.count);
        serviceAdapter= new ServiceAdapter();
        dealersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dealersRecyclerView.setAdapter(serviceAdapter);
        serviceAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(ServiceModel dealer, int index) {
                startActivity(getSelectedIntentFor(dealer));
            }
        });
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
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void observe() {

        viewmodel.getIsLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    serviceAdapter.setModels(new ArrayList<>());
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
                count.setText(serviceModels.size()+" items found");
            }
        });

    }

    private Intent getSelectedIntentFor(ServiceModel model) {
        Intent intent= new Intent(this, ServiceDisplayActivity.class);
        intent.putExtra("service_data",new Gson().toJson(model));
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        SearchManager searchManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        }
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return true;

    }

    private void filter(String query)
    {
        query=query.toLowerCase();
        List<ServiceModel> newModels=new ArrayList<>();
        for(ServiceModel model:viewmodel.getServicesListLiveData().getValue())
        {
            if(model.getEnterpriseName().toLowerCase().contains(query)||model.getDealsIn().contains(query) || model.getTagsAsString().contains(query) || model.getPhone().toLowerCase().contains(query))
                newModels.add(model);

        }
        serviceAdapter.setModels(newModels);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            if (!searchView.isIconified()) {
                searchView.onActionViewCollapsed();
            } else {
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}