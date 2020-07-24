package ui.MiProyectos;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import models.Proyecto;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class MiProyectosViewModel extends ViewModel
{
    Sharedprefs sp;

    private MutableLiveData<List<Proyecto>> proyectosMutableLiveData;

    private MutableLiveData<Proyecto> proyectoBorradoMutableLiveData;


        public MiProyectosViewModel()
        {

        }

    public LiveData<List<Proyecto>> getProyectosMutableLiveData()
    {
        if(proyectosMutableLiveData == null)
        {
            proyectosMutableLiveData = new MutableLiveData<>();
        }
        return proyectosMutableLiveData;
    }

    public MutableLiveData<Proyecto> getProyectoBorradoMutableLiveData()
    {
        if(proyectoBorradoMutableLiveData == null)
        {
            proyectoBorradoMutableLiveData = new MutableLiveData<>();
        }
        return proyectoBorradoMutableLiveData;
    }

    public void TraerProyectos(final Context ctx)
     {
         String token = "Bearer " + sp.leerToken(ctx);

         final Call<List<Proyecto>> res = ApiClient.getMyApiClient().TraerProyectosUsuario(token);

        res.enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response)
            {
                if(response.isSuccessful())
                {
                    proyectosMutableLiveData.postValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

     }

    public void BorrarProyecto(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Call<Proyecto> res = ApiClient.getMyApiClient().BorrarProyecto(token,id);

        res.enqueue(new Callback<Proyecto>() {
            @Override
            public void onResponse(Call<Proyecto> call, Response<Proyecto> response) {

                if(response.isSuccessful())
                {
                    proyectoBorradoMutableLiveData.postValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Proyecto> call, Throwable t) {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}