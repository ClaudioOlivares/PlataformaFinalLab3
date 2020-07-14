package proyecto;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import models.CheckPerfilView;
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

        final Call<Proyecto> res = ApiClient.getMyApiClient().TraerProyecto(token,id);

        res.enqueue(new Callback <Proyecto>()
        {
            @Override
            public void onResponse(Call<Proyecto> call, Response<Proyecto> response)
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
            public void onFailure(Call<Proyecto> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




}
