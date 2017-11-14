package in.digitechlab.skystatus;


import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.SEARCH_SERVICE;
import static java.lang.Integer.valueOf;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    String city_name = "London";
    TextView weather, status;
    private CurrentWeatherAPI currentWeatherAPI;
    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    ImageView image2;


    public FragmentOne() {
        // Required empty public constructor
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);



        if (getArguments() != null) {
            city_name = getArguments().getString("city");
           // Toast.makeText(getActivity(), city_name+" Weather Report", Toast.LENGTH_SHORT).show();
        }

        weather = (TextView) view.findViewById(R.id.details);

        status = (TextView) view.findViewById(R.id.current_details);

        image2 = (ImageView) view.findViewById(R.id.imageView2);

        initializeNetworkLibrary();

        getWeatherResponse();

        return view;
    }

    public static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


    private void getWeatherResponse() {

        String urlString = String.format("weather?q=%s&units=metric&APPID=%s",city_name, BuildConfig.WEATHER_API_KEY);

        Call<Current_Weather_Model> skyServiceAPICall = currentWeatherAPI.getWeatherReport(urlString);

        skyServiceAPICall.enqueue(new Callback<Current_Weather_Model>() {
            @Override
            public void onResponse(Call<Current_Weather_Model> call, Response<Current_Weather_Model> response) {
                if(response.code()==200){
                    Current_Weather_Model responses = response.body();

                    String mainW = responses.getWeather().get(0).getMain().toString().toUpperCase();
                    String humdW = responses.getMain().getHumidity().toString();
                    String windW = responses.getWind().getSpeed().toString();
                    String pressW = responses.getMain().getPressure().toString();
                    String riseW = formatTime(new Date(responses.getSys().getSunrise()));
                    String setW = formatTime(new Date(responses.getSys().getSunset()));
                    String countryW = responses.getSys().getCountry();
                    char ch = responses.getWeather().get(0).getMain().toString().charAt(2);

                    status.setText(responses.getName()+" City Current Status: "+mainW);

                    weather.setText("City Location: "+responses.getName()+", "+countryW+"\nDetails overview: "+responses.getWeather().get(0).getDescription()+"\nTemperature: "+responses.getMain().getTemp()+"°C\nHumidity: "+humdW+"% \nWind Speed:"+windW+" km\nSunrise Time: loading . . . \nSunset Time: loading . . .\nATM. Pressure: "+pressW+" unit");

                    if(ch=='i') {
                        image2.setImageResource(R.drawable.rain1);
                    }else if (ch=='e'){
                        image2.setImageResource(R.drawable.clear1);
                    }else if (ch=='o'){
                        image2.setImageResource(R.drawable.clouds1);
                    }else if (ch=='u'){
                        image2.setImageResource(R.drawable.thunder1);
                    }else if (ch=='s'){
                        image2.setImageResource(R.drawable.mist1);
                    }else if (ch=='z'){
                        image2.setImageResource(R.drawable.haze1);
                    }else{
                       image2.setImageResource(R.drawable.weather2);
                    }

                    //Toast.makeText(getActivity(), "Query Successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Query Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Current_Weather_Model> call, Throwable t) {
                Toast.makeText(getActivity(), "Forcast Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                weather.setText(t.getMessage());

            }
        });

    }

    private void initializeNetworkLibrary() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        currentWeatherAPI = retrofit.create(CurrentWeatherAPI.class);

    }


}
