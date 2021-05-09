package com.jstechnologies.helpinghands.ui.views.addService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jstechnologies.helpinghands.R;
import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.databinding.ActivityAddServiceBinding;
import com.jstechnologies.helpinghands.ui.views.base.BaseActivity;

import java.util.Arrays;

public class AddServiceActivity extends BaseActivity<AddServiceViewModel> {

    ProgressDialog progressDialog;
    ActivityAddServiceBinding addServiceBinding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        addServiceBinding=ActivityAddServiceBinding.inflate(getLayoutInflater());
        setContentView(addServiceBinding.getRoot());
        getSupportActionBar().setTitle("Add Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait while we save this service to our servers");
        progressDialog.setCanceledOnTouchOutside(false);
        viewmodel.fetchCurrentAddress(this);
    }

    @NonNull
    @Override
    protected AddServiceViewModel createViewModel() {
        return new ViewModelProvider(this,new AddServiceViewModelFactory(ServiceRepository.getInstance())).get(AddServiceViewModel.class);
    }

    @Override
    protected void onMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void observe() {
       viewmodel.getIsSavingLiveData().observe(this, new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean aBoolean) {
               if(!aBoolean && progressDialog.isShowing())
                   progressDialog.dismiss();
               else progressDialog.show();

           }
       });
        viewmodel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
               onMessage(message);
            }
        });
        viewmodel.getIsSaveSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)
                    return;
                AddServiceActivity.this.setResult(RESULT_OK);
                finish();
            }
        });
        viewmodel.getAddressLiveData().observe(this, new Observer<Address>() {
            @Override
            public void onChanged(Address address) {
                addServiceBinding.city.setText(address.getCity());
                addServiceBinding.state.setText(address.getState());
            }
        });
    }

    public void onSaveClick(View view) {
        ServiceModel model=prepareModelFromView();
        if(model==null)
            return;
        viewmodel.saveService(model);
    }

    ServiceModel prepareModelFromView()
    {
        if(validate())
        {
            ServiceModel dealer= new ServiceModel();
            dealer.setEnterpriseName(addServiceBinding.fullname.getText().toString().trim());
            dealer.setEmail(addServiceBinding.email.getText().toString().trim());
            dealer.setPhone(addServiceBinding.phone.getText().toString().trim());
            dealer.setDealsIn(Arrays.asList(addServiceBinding.dealsIn.getText().toString().trim().split(",")));
            String[]_tags=addServiceBinding.tags.getText().toString().trim().split(",");
            dealer.setDescription(addServiceBinding.desc.getText().toString().trim());
            if(_tags.length>0)
                dealer.setTags(Arrays.asList(_tags));
            return dealer;
        }
        return null;

    }

    boolean validate()
    {
        return (checkEmpty(addServiceBinding.fullname)
                && checkEmpty(addServiceBinding.phone)
                && checkEmpty(addServiceBinding.dealsIn)
                && isValidEmail(addServiceBinding.email)
                && isValidPhone(addServiceBinding.phone));

    }
    boolean checkEmpty(EditText editText)
    {
        String _value=editText.getText().toString().trim();
        if(_value==null || _value.isEmpty())
        {
            editText.setError("This field is required");
            editText.requestFocus();
            return false;
        }
        return true;
    }
    boolean isValidEmail(EditText email)
    {

        String _value=email.getText().toString().trim();
        if(_value==null || _value.isEmpty())
            return true;
        if(!_value.matches(emailPattern))
            Toast.makeText(getApplicationContext(),"Not a valid email",Toast.LENGTH_SHORT).show();
        return _value.matches(emailPattern);
    }
    boolean isValidPhone(EditText phone)
    {
        String _value=phone.getText().toString().trim();
        if(_value.length()!=10)
            Toast.makeText(getApplicationContext(),"Not a valid phone",Toast.LENGTH_SHORT).show();
        return _value.length()==10;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}