package devlog;

import android.content.Context;
import android.widget.EditText;
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

    private MutableLiveData<String> respuestaCheckeoMutableLiveData;

    private  MutableLiveData<DevLog> devlogseleccionadoMutableLiveData;

    private  MutableLiveData<DevLog> devlogactualizadoMutableLiveData;

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

    public LiveData<String> getRespuestaCheckeoMutableLiveData()
    {
        if(respuestaCheckeoMutableLiveData == null)
        {
            respuestaCheckeoMutableLiveData = new MutableLiveData<>();
        }

        return respuestaCheckeoMutableLiveData;
    }

    public LiveData<DevLog> getDevlogseleccionadoMutableLiveData()
    {
        if(devlogseleccionadoMutableLiveData == null)
        {
            devlogseleccionadoMutableLiveData = new MutableLiveData<>();
        }

        return  devlogseleccionadoMutableLiveData;
    }

    public LiveData<DevLog> getDevlogactualizadoMutableLiveData()
    {
        if(devlogactualizadoMutableLiveData == null)
        {
            devlogactualizadoMutableLiveData = new MutableLiveData<>();
        }
        return devlogactualizadoMutableLiveData;
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

    public void checkearDevLogs(final Context ctx, int id)
    {

        String token = "Bearer " + sp.leerToken(ctx);

        Call<String> res = ApiClient.getMyApiClient().checkearDevLog(token,id);

        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    respuestaCheckeoMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void traerDevlogSeleccionado(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<DevLog> res = ApiClient.getMyApiClient().traerDevlogSeleccionado(token,id);

        res.enqueue(new Callback<DevLog>()
        {
            @Override
            public void onResponse(Call<DevLog> call, Response<DevLog> response)
            {
                if(response.isSuccessful())
                {
                    devlogseleccionadoMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<DevLog> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void actualizarDevlog(EditText titulo, EditText resumen, int id, final Context ctx)
    {
        DevLog dl = new DevLog();

        dl.setTitulo(titulo.getText().toString());

        dl.setResumen(resumen.getText().toString());

        dl.setIdDevlog(id);

        String token = "Bearer " + sp.leerToken(ctx);

        final Call<DevLog> res = ApiClient.getMyApiClient().actualizarDevlog(dl,token);

        res.enqueue(new Callback<DevLog>()
        {
            @Override
            public void onResponse(Call<DevLog> call, Response<DevLog> response)
            {
                if(response.isSuccessful())
                {
                   devlogactualizadoMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<DevLog> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
