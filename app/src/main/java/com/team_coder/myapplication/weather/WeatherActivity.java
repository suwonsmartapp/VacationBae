package com.team_coder.myapplication.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team_coder.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private List<Weather> mWeatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        final ListView listView = (ListView) findViewById(R.id.list_view);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WeatherService.BASE_URL)
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        service.getWeatherList().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if (response.isSuccessful()) {
                    mWeatherList = response.body();
                    WeatherAdapter adapter = new WeatherAdapter(mWeatherList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static class WeatherAdapter extends BaseAdapter {
        private List<Weather> mData;

        public WeatherAdapter(List<Weather> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            if (mData == null) {
                return 0;
            }
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);

                ImageView weather = (ImageView) convertView.findViewById(R.id.weather_image);
                TextView country = (TextView) convertView.findViewById(R.id.country_text);
                TextView temperature = (TextView) convertView.findViewById(R.id.temp_text);

                viewHolder = new ViewHolder();
                viewHolder.weather = weather;
                viewHolder.country = country;
                viewHolder.temperature = temperature;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Weather weather = mData.get(position);

            viewHolder.country.setText(weather.getCountry());
            viewHolder.temperature.setText(weather.getTemperature());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView weather;
        TextView country;
        TextView temperature;
    }
}
