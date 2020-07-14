package ui.miperfil;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import models.CheckPerfilView;
import models.Proyecto;
import models.Usuario;
import request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sharedprefs.Sharedprefs;

public class MiPerfilViewModel extends ViewModel {

    Sharedprefs sp;

    private MutableLiveData<Usuario> usuarioMutableLiveData;

    private MutableLiveData<String> stringMutableLiveData;


    public MiPerfilViewModel()
    {

    }

    public LiveData<Usuario> getUsuarioMutableLiveData()
    {
        if(usuarioMutableLiveData == null)
        {
           usuarioMutableLiveData = new MutableLiveData<>();
        }
        return usuarioMutableLiveData;
    }

    public LiveData<String> getStringMutableLiveData()
    {
        if(stringMutableLiveData == null)
        {
            stringMutableLiveData = new MutableLiveData<>();
        }

        return  stringMutableLiveData;
    }

    public void traerusuarioLogeado(final Context ctx)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        final Call<Usuario> res = ApiClient.getMyApiClient().traerUsuarioLogeado(token);

        res.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response)
            {
                if(response.isSuccessful())
                {
                    usuarioMutableLiveData.setValue(response.body());
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
            public void onFailure(Call<Usuario> call, Throwable t)
            {
                Toast.makeText(ctx, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void checkearperfil(final Context ctx,String email)
    {
        String token = "Bearer " + sp.leerToken(ctx);

        CheckPerfilView cv = new CheckPerfilView(email);

        Call<String> res = ApiClient.getMyApiClient().checkearPerfil(cv,token);

        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    stringMutableLiveData.postValue(response.body());
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