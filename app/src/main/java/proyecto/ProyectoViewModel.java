package proyecto;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import models.CheckPerfilView;
import models.ImagenProyecto;
import models.Proyecto;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class ProyectoViewModel extends ViewModel
{
    Sharedprefs sp;

    private MutableLiveData<List<ImagenProyecto>> proyectoMutableLiveData;

    private MutableLiveData<String> checkeoMutableLiveData;


    public ProyectoViewModel()
    {

    }

    public LiveData<List<ImagenProyecto>> getProyectoMutableLiveData()
    {
        if(proyectoMutableLiveData == null)
        {
            proyectoMutableLiveData = new MutableLiveData<>();
        }
        return proyectoMutableLiveData;
    }

    public LiveData<String> getCheckeoMutableLiveData()
    {
        if(checkeoMutableLiveData == null)
        {
            checkeoMutableLiveData = new MutableLiveData<>();
        }
        return checkeoMutableLiveData;
    }



    public void traerProyecto(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<List<ImagenProyecto>> res = ApiClient.getMyApiClient().TraerProyectoImagenes(token,id);

        res.enqueue(new Callback <List<ImagenProyecto>>()
        {
            @Override
            public void onResponse(Call<List<ImagenProyecto>> call, Response<List<ImagenProyecto>> response)
            {
                if(response.isSuccessful())
                {
                    if(response.body().size() != 0) 
                    {
                        proyectoMutableLiveData.setValue(response.body());
                    }
                    else
                    {
                        Toast.makeText(ctx, "El proyecto no contiene elementos", Toast.LENGTH_SHORT).show();
                    }
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
            public void onFailure(Call<List<ImagenProyecto>> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkearSiEsProyectoDelUsuario(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Call<String> res = ApiClient.getMyApiClient().checkearproyecto(token,id);

        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    checkeoMutableLiveData.postValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
