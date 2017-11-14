package in.digitechlab.skystatus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 10/11/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{

    private LayoutInflater wInflater;
    private List<Weather_Forecast_Model> wForecastList;
    private Context mContext;

    public WeatherAdapter(Context context)
    {
        this.mContext = context;
        this.wInflater = LayoutInflater.from(context);
        this.wForecastList = new ArrayList<>();
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = wInflater.inflate(R.layout.row_forecast, parent, false);
        final WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        String date_time = wForecastList.get(position).getDt_txt();
        String max_temp = wForecastList.get(position).getMain().getTempMax().toString();
        String min_temp = wForecastList.get(position).getMain().getTempMin().toString();
        String main_fc = wForecastList.get(position).getWeather().get(0).getMain().toUpperCase();
        String description = wForecastList.get(position).getWeather().get(0).getDescription();
        String humidity = wForecastList.get(position).getMain().getHumidity().toString();
        String speed = wForecastList.get(position).getWind().getSpeed().toString();

        holder.tv1.setText(date_time+"\n\n"+main_fc);
        holder.tv2.setText("Status: "+description+"\nHumidity: "+humidity+"%\nWind Speed: "+speed+"km\nMAX Temp: "+max_temp+"°C\nMIN Temp: "+min_temp+"°C");

        char ch = wForecastList.get(position).getWeather().get(0).getMain().charAt(2);

        if(ch=='i') {
           holder.imageView1.setImageResource(R.drawable.rain);
       }else if (ch=='e'){
           holder.imageView1.setImageResource(R.drawable.clear2);
       }else if (ch=='o'){
           holder.imageView1.setImageResource(R.drawable.clouds2);
       }else{
           holder.imageView1.setImageResource(R.drawable.weather_status);
       }
    }

    @Override
    public int getItemCount() {
        return (wForecastList == null) ? 0 : wForecastList.size();
    }

    public void setForecastList(List<Weather_Forecast_Model> ForecastList)
    {
        this.wForecastList.clear();
        this.wForecastList.addAll(ForecastList);
        notifyDataSetChanged();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView1;
        public TextView tv1, tv2, tv3;
        public WeatherViewHolder(View itemView)
        {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.weather_image);
            tv1 =  (TextView) itemView.findViewById(R.id.date_time);
            tv2 =  (TextView) itemView.findViewById(R.id.forecast_details);
            //tv3 =  (TextView) itemView.findViewById(R.id.max_min);
        }
    }


}
