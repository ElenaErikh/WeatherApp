package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.WeatherContainer;

import java.util.Objects;

public class WeatherFragment extends Fragment {
    static WeatherFragment create(com.example.weatherapp.WeatherContainer container) {
        WeatherFragment fragment = new WeatherFragment();

        Bundle args = new Bundle();
        args.putSerializable("index", container);
        fragment.setArguments(args);
        return fragment;
    }

    int getIndex() {
        WeatherContainer weatherContainer = (WeatherContainer)
                (Objects.requireNonNull(getArguments()).getSerializable("index"));
        try {
            return weatherContainer.position;
        } catch (Exception e) {
            return 0;
        }
    }

    String getCityName() {
        WeatherContainer weatherContainer = (WeatherContainer) (Objects.requireNonNull(getArguments())
                .getSerializable("index"));

        try {
            return weatherContainer.cityName;
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    @SuppressLint("Recycle")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView cityNameTextView = new TextView(getContext());
        String cityName = getCityName();
        cityNameTextView.setText(cityName);

        TextView temperatureTextView = new TextView(getContext());
        String t = "+25";
        temperatureTextView.setText(t);

        TextView windTextView = new TextView(getContext());
        String windStr = "Wind: 3 m/s";
        windTextView.setText(windStr);

        TextView pressureTextView = new TextView(getContext());
        String p = "Pressure: 1019 hPa";
        pressureTextView.setText(p);

        ImageView cityImage = new ImageView(getActivity());

        TypedArray images = getResources().obtainTypedArray(R.array.cities_imgs);
        cityImage.setImageResource(images.getResourceId(getIndex(), -1));

        layout.addView(cityNameTextView);
        layout.addView(temperatureTextView);
        layout.addView(windTextView);
        layout.addView(pressureTextView);
        layout.addView(cityImage);

        return layout;
    }
}
