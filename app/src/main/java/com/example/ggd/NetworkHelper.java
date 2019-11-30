package com.example.ggd;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    final static String url = "https://dapi.kakao.com";
    final static String url2 = "http://swopenapi.seoul.go.kr";
    //http://swopenapi.seoul.go.kr/api/subway/61485a66707368693131344d7a734549/xml/nearBy/0/5/203385.80656072023/454056.33962922497

    private static Retrofit retrofit;
    private static Retrofit retrofit2;


    public static NetworkInterface getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(NetworkInterface.class);
    }

    public static NetworkInterface getSeoulInstance(){
        if(retrofit2 == null){
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(url2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit2.create(NetworkInterface.class);
    }

}