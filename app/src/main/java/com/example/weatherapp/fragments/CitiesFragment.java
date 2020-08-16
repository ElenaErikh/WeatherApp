package com.example.weatherapp.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.RecyclerDataAdapter;
import com.example.weatherapp.WeatherActivity;
import com.example.weatherapp.WeatherContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CitiesFragment extends Fragment {
    private TextView emptyTextView;
    private CheckBox windBox;
    private CheckBox pressureBox;
    private RecyclerView recyclerView;
    private RecyclerDataAdapter adapter;

    private boolean isWeatherExist;
    private String windData;
    private String pressureData;
    private int currentPosition = 0;
    private ArrayList<String> citiesData = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities))) ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_rv_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        initViews(view);
        setupRecyclerView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isWeatherExist = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }

        if (isWeatherExist) {
            //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeather();
        }
    }

    private void checkSelection(){
        if (windBox.isChecked()) {
            windData = "Wind speed: 3 m/s";
        }

        if (pressureBox.isChecked()){
            pressureData = "Pressure: 1019 hPa";
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("CurrentCity", currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
        windBox = view.findViewById(R.id.wind_box);
        pressureBox = view.findViewById(R.id.pressure_box);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerDataAdapter(citiesData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showWeather() {
        checkSelection();

        if (isWeatherExist) {
//            listView.setItemChecked(currentPosition, true);

            WeatherFragment detail = (WeatherFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.cityImg);

            if (detail == null || detail.getIndex() != currentPosition) {

                detail = WeatherFragment.create(getWeatherContainer());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.cityImg, detail);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherActivity.class);
            intent.putExtra("index", getWeatherContainer());
            startActivity(intent);
        }
    }

    private WeatherContainer getWeatherContainer() {
        String[] cities = getResources().getStringArray(R.array.cities);
        String[] temperature = getResources().getStringArray(R.array.temperature);
        WeatherContainer container = new WeatherContainer();
        container.position = currentPosition;
        container.cityName = cities[currentPosition];
        container.temperature = temperature[currentPosition];
        container.wind = windData;
        container.pressure = pressureData;
        return container;
    }
}
