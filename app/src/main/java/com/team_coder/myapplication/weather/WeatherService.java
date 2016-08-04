package com.team_coder.myapplication.weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by junsuk on 16. 8. 4..
 */
public interface WeatherService {

    String BASE_URL = "http://suwonsmartapp.iptime.org/";

    @GET("test/junsuk/weather.json")
    Call<List<Weather>> getWeatherList();
}
