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
                    proyectoMutableLiveData.setValue(response.body());
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




}
