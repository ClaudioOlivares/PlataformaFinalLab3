package DevlogItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.CalendarContract;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.ByteArrayOutputStream;
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

    private MutableLiveData<String> checkeodevlogitemMutableLiveData;

    private MutableLiveData<DevLogItem> devlogitemseleccionadoMutableLiveData;

    private MutableLiveData<DevLogItem> devlogitemactualizadoMutableLiveData;

    private MutableLiveData<DevLogItem> devlogitemcreadoMutableLiveData;

    private MutableLiveData<DevLogItem> devlogitemborradoMutableLiveData;

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

    public LiveData<String> getCheckeodevlogitemMutableLiveData()
    {
        if(checkeodevlogitemMutableLiveData == null)
        {
            checkeodevlogitemMutableLiveData = new MutableLiveData<>();
        }
        return checkeodevlogitemMutableLiveData;
    }

    public LiveData<DevLogItem> getdevlogitemseleccionadoMutableLiveData()
    {
        if(devlogitemseleccionadoMutableLiveData == null)
        {
            devlogitemseleccionadoMutableLiveData = new MutableLiveData<>();
        }
        return devlogitemseleccionadoMutableLiveData;
    }

    public LiveData<DevLogItem> getdevlogitemactualizadoMutableLiveData()
    {
        if(devlogitemactualizadoMutableLiveData == null)
        {
            devlogitemactualizadoMutableLiveData = new MutableLiveData<>();
        }
        return devlogitemactualizadoMutableLiveData;
    }


    public LiveData<DevLogItem> getdevlogitemcreadoMutableLiveData()
    {
        if(devlogitemcreadoMutableLiveData == null)
        {
            devlogitemcreadoMutableLiveData = new MutableLiveData<>();
        }
        return devlogitemcreadoMutableLiveData;
    }

    public LiveData<DevLogItem> getdevlogitemborradoMutableLiveData()
    {
        if(devlogitemborradoMutableLiveData == null)
        {
            devlogitemborradoMutableLiveData = new MutableLiveData<>();
        }
        return devlogitemborradoMutableLiveData;
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


    public void checkearDevLogsItems(final Context ctx, int id)
    {

        String token = "Bearer " + sp.leerToken(ctx);

        Call<String> res = ApiClient.getMyApiClient().checkearDevLogItem(token,id);

        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    checkeodevlogitemMutableLiveData.setValue(response.body());
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

    public void traerDevlogsitemSeleccionado(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Call<DevLogItem> res = ApiClient.getMyApiClient().traerDevlogItemSeleccionado(token,id);

        res.enqueue(new Callback<DevLogItem>() {
            @Override
            public void onResponse(Call<DevLogItem> call, Response<DevLogItem> response)
            {
                if(response.isSuccessful())
                {
                    devlogitemseleccionadoMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<DevLogItem> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void actualizarDevlogsitemSeleccionado(final Context ctx, int id, EditText texto, EditText titulo, String img64)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        DevLogItem dli = new DevLogItem();

        dli.setIdDevlogItem(id);

        dli.setTexto(texto.getText().toString());

        dli.setTitulo(titulo.getText().toString());

        dli.setMultimedia(img64);

        Call<DevLogItem> res = ApiClient.getMyApiClient().actualizarDevlogItem(dli,token);

        res.enqueue(new Callback<DevLogItem>() {
            @Override
            public void onResponse(Call<DevLogItem> call, Response<DevLogItem> response)
            {
                if(response.isSuccessful())
                {
                    devlogitemactualizadoMutableLiveData.setValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string() + "ASD3", Toast.LENGTH_SHORT).show();
                    } catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage() + "ASD 2", Toast.LENGTH_SHORT).show();;
                    }
                }
            }

            @Override
            public void onFailure(Call<DevLogItem> call, Throwable t)
            {
                Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void crearDevLogItem(final Context ctx, int id, EditText texto, EditText titulo, String img64)
    {

        String token = "Bearer " + sp.leerToken(ctx);

        DevLogItem dli = new DevLogItem();

        dli.setIdDevlog(id);

        dli.setTexto(texto.getText().toString());

        dli.setTitulo(titulo.getText().toString());

        dli.setMultimedia(img64);

        Call<DevLogItem> res = ApiClient.getMyApiClient().crearDevlogItem(token,dli);

        res.enqueue(new Callback<DevLogItem>() {
            @Override
            public void onResponse(Call<DevLogItem> call, Response<DevLogItem> response)
            {
                if(response.isSuccessful())
                {
                    devlogitemcreadoMutableLiveData.setValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string() + "ASD3", Toast.LENGTH_SHORT).show();
                    } catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage() + "ASD 2", Toast.LENGTH_SHORT).show();;
                    }
                }

            }

            @Override
            public void onFailure(Call<DevLogItem> call, Throwable t)
            {

                Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void borrarDevLogItem(final Context ctx, int id)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        Call<DevLogItem> res = ApiClient.getMyApiClient().BorrarDevlogItem(token,id);

        res.enqueue(new Callback<DevLogItem>() {
            @Override
            public void onResponse(Call<DevLogItem> call, Response<DevLogItem> response)
            {
                if(response.isSuccessful())
                {
                    devlogitemborradoMutableLiveData.setValue(response.body());
                }
                else
                {
                    try
                    {
                        Toast.makeText(ctx, response.errorBody().string() + "ASD3", Toast.LENGTH_SHORT).show();
                    } catch (IOException e)
                    {
                        Toast.makeText(ctx, e.getMessage() + "ASD 2", Toast.LENGTH_SHORT).show();;
                    }
                }
            }

            @Override
            public void onFailure(Call<DevLogItem> call, Throwable t) {
                Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    public String imgto64(Bitmap bmp)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);

        byte[] imgBytes = byteArrayOutputStream.toByteArray();

        return (Base64.encodeToString(imgBytes,Base64.DEFAULT));
    }

}
