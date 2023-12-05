//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

package com.jason.druh.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class ApiClient {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    private static Retrofit retrofit;

    //----------------------------------------------------------------------------------------------

    /*RETROFIT API CONNECTION*/

    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.32:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    } // END GETCLIENT

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------