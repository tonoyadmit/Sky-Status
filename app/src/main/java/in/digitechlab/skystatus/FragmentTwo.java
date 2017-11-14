package in.digitechlab.skystatus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String city_name = "London";
    TextView forecast_details;
    private RecyclerView wRecyclerView;
    private WeatherAdapter wAdapter;
    private Weather_Forecast_Model weather_forecast_model;
    private WeatherForecastAPI weatherForecastAPI;
    private ArrayList<Weather_Forecast_Model> ForecastList;



    public FragmentTwo() {
        // Required empty public constructor
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_two, container, false);

        if (getArguments() != null) {
            city_name = getArguments().getString("city");
            Toast.makeText(getActivity(), city_name, Toast.LENGTH_SHORT).show();
        }

        forecast_details = (TextView) view.findViewById(R.id.forecast_details);

        wRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_forecast);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        wRecyclerView.setLayoutManager(llm);
        wAdapter = new WeatherAdapter(getActivity());
        wRecyclerView.setAdapter(wAdapter);
        ForecastList = new ArrayList<Weather_Forecast_Model>();

/*        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));
        ForecastList.add(new Weather_Forecast_Model("23", "33", "78%","Very Coudy", "Cloud & Rain", 8, "88kph", "12-12-1990"));*/
        initializeNetworkLibrary();

        getWeatherResponse();

        return view;

    }

    private void getWeatherResponse() {

        String urlString = String.format("forecast?q=%s&units=metric&APPID=%s",city_name, BuildConfig.WEATHER_API_KEY);

        Call<Weather_Forecast_Model.WeatherResult> skyServiceAPICall = weatherForecastAPI.getWeatherReport(urlString);

        skyServiceAPICall.enqueue(new Callback<Weather_Forecast_Model.WeatherResult>() {
            @Override
            public void onResponse(Call<Weather_Forecast_Model.WeatherResult> call, Response<Weather_Forecast_Model.WeatherResult> response) {
                if(response.code()==200){
                    Weather_Forecast_Model.WeatherResult responses = response.body();

                    forecast_details.setText("FORCAST Report of "+city_name.toUpperCase()+" City" );

                    wAdapter.setForecastList(responses.getList());

                   // Toast.makeText(getActivity(), "Query Successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Query Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Weather_Forecast_Model.WeatherResult> call, Throwable t) {
                Toast.makeText(getActivity(), "Query Unsuccessful", Toast.LENGTH_SHORT).show();
                forecast_details.setText("FORCAST: "+t.getMessage() );
                Log.e("Fail",t.getMessage());
            }
        });

    }

    private void initializeNetworkLibrary() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        weatherForecastAPI = retrofit.create(WeatherForecastAPI.class);

    }

}
