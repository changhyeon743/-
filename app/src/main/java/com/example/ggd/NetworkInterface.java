package com.example.ggd;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkInterface {

    @Headers("Authorization: KakaoAK d5d3f3f6ed5b58598311b2e4fb534188")
    @GET("/v2/local/geo/transcoord.json?input_coord=WGS84&output_coord=TM")
    Call<CoordinateTM>GetCoordinate(@Query("x") double x,@Query("y") double y);


    @GET("/api/subway/61485a66707368693131344d7a734549/json/nearBy/{min}/{max}/{x}/{y}")
    Call<StationInfo>GetStationInfo(@Path("min") int min, @Path("max") int max, @Path("x") double x, @Path("y") double y);

}
