package DevlogItem;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import models.DevLogItem;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class DevlogItemViewModel extends ViewModel
{

    private Sharedprefs sp;

    private MutableLiveData<List<DevLogItem>> devlogitemMutableLiveData;

    public DevlogItemViewModel()
    {

    }

    public LiveData<List<DevLogItem>> getDevlogitemMutableLiveData()
    {
        if(devlogitemMutableLiveData == null)
        {
            devlogitemMutableLiveData = new MutableLiveData<>();
        }
        return devlogitemMutableLiveData;
    }

    public void traerDevlogsitems(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Call<List<DevLogItem>> res = ApiClient.getMyApiClient().traerDevlogsItems(token,id);

        res.enqueue(new Callback<List<DevLogItem>>() {
            @Override
            public void onResponse(Call<List<DevLogItem>> call, Response<List<DevLogItem>> response)
            {
                if(response.isSuccessful())
                {
                    devlogitemMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<List<DevLogItem>> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
