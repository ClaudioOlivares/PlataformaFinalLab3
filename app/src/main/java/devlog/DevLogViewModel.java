package devlog;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import models.DevLog;
import models.ImagenProyecto;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class DevLogViewModel extends ViewModel
{
    Sharedprefs sp;

    private MutableLiveData<List<DevLog>> devlogMutableLiveData;

    public DevLogViewModel()
    {

    }

    public LiveData<List<DevLog>> getDevlogMutableLiveData()
    {
        if(devlogMutableLiveData == null)
        {
            devlogMutableLiveData = new MutableLiveData<>();
        }
        return devlogMutableLiveData;
    }

    public void traerDevLogs(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<List<DevLog>> res = ApiClient.getMyApiClient().TraerDevlogs(token,id);

        res.enqueue(new Callback<List<DevLog>>()
        {
            @Override
            public void onResponse(Call<List<DevLog>> call, Response<List<DevLog>> response)
            {
                if(response.isSuccessful())
                {
                    devlogMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<List<DevLog>> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
