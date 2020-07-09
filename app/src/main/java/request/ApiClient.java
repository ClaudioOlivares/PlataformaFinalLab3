package request;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import models.LoginView;
import models.Proyecto;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {

    private static final String PATH="http://192.168.0.104:45455/api/";
    private static  MyApiInterface myApiInteface;


    public static MyApiInterface getMyApiClient(){

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApiInteface=retrofit.create(MyApiInterface.class);
        Log.d("salida",retrofit.baseUrl().toString());
        return myApiInteface;
    }
    public interface MyApiInterface
    {
        //------------------------------------------------USUARIO------------------------------------------------------------------

            @POST("Usuario/login")
            Call <String> logear(@Body LoginView user);

        //------------------------------------------------PROYECTO------------------------------------------------------------------

            @GET("Proyecto")
            Call<List<Proyecto>> TraerProyectosUsuario(@Header("Authorization") String token);
    }

    public static String getPATH() {
        return PATH;
    }
}
