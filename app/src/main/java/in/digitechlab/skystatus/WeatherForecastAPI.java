package in.digitechlab.skystatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by DELL on 10/12/2017.
 */

public interface WeatherForecastAPI {

    @GET()
    Call<Weather_Forecast_Model.WeatherResult> getWeatherReport(@Url String urlString);

}
