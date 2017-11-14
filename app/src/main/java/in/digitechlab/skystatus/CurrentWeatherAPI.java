package in.digitechlab.skystatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by DELL on 10/11/2017.
 */

public interface CurrentWeatherAPI {

    @GET()
    Call<Current_Weather_Model> getWeatherReport(@Url String urlString);

}
