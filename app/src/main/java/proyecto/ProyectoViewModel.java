package proyecto;

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

public class ProyectoViewModel extends ViewModel
{
    Sharedprefs sp;

    private MutableLiveData<Proyecto> proyectoMutableLiveData;

    public ProyectoViewModel()
    {

    }

    public LiveData<Proyecto> getProyectoMutableLiveData()
    {
        if(proyectoMutableLiveData == null)
        {
            proyectoMutableLiveData = new MutableLiveData<>();
        }
        return proyectoMutableLiveData;
    }

    public void traerProyecto(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<List<Proyecto>> res = ApiClient.getMyApiClient().TraerProyecto(token,id);

        res.enqueue(new Callback <List<Proyecto>>()
        {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response)
            {
                if(response.isSuccessful())
                {
                    List<Proyecto> p = response.body();
                    proyectoMutableLiveData.postValue(p.get(0));
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();;
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
}
