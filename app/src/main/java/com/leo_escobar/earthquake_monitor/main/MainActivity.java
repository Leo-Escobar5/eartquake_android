package com.leo_escobar.earthquake_monitor.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leo_escobar.earthquake_monitor.api.RequestStatus;
import com.leo_escobar.earthquake_monitor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // añadir el binding de la vista
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);



        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));


        EqAdapter adapter = new EqAdapter();
        adapter.setOnItemClickListener(earthquake -> {
            //toast
            Toast.makeText(MainActivity.this, earthquake.getPlace(), Toast.LENGTH_SHORT).show();
        });


        binding.eqRecycler.setAdapter(adapter);


        viewModel.getEqList().observe(this, eqList -> {

            adapter.submitList(eqList);


            if (eqList.isEmpty()) {
                binding.eqRecycler.setVisibility(android.view.View.GONE);
                binding.emptyView.setVisibility(android.view.View.VISIBLE);
            } else {
                binding.eqRecycler.setVisibility(android.view.View.VISIBLE);
                binding.emptyView.setVisibility(android.view.View.GONE);
            }
        });
        viewModel.getStatusMutableLiveData().observe(this,statusWithDescription -> {
            if(statusWithDescription.getStatus()== RequestStatus.LOADING){
                //ruedita de carga
                binding.loadingWheel.setVisibility(View.VISIBLE);

            }else{
                //ocultar ruedita de carga
                binding.loadingWheel.setVisibility(View.GONE);
            }
            if(statusWithDescription.getStatus()== RequestStatus.ERROR) {
                //mostrar toast con el error
                Toast.makeText(this, statusWithDescription.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.downloadEarthquakes();
    }
}