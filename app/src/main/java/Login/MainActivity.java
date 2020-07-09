package Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.plataformafinallab3.R;

import landing.MenuMain;

public class MainActivity extends AppCompatActivity {
VideoView vv;

MediaPlayer mediap;

private EditText email;

private EditText pass;

private LoginViewModel vm;

int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        vm = ViewModelProviders.of(this).get(LoginViewModel.class);

        generarVideo();

        obtenerElementosVista();



    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        mediap.release();

        mediap = null;
    }

    @Override
    protected void onResume() {

        super.onResume();

        //mediap.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        position =  mediap.getCurrentPosition();

        mediap.pause();
    }

    public  void  logear(View v)
    {
        Intent i = new Intent(this , MenuMain.class);

       vm.validar(email,pass,i,this);
    }

    public void obtenerElementosVista()
    {
        email = findViewById(R.id.etEmailLogin2);

        pass = findViewById(R.id.etPassLogin2);

    }

    public void generarVideo()
    {
        setContentView(R.layout.activity_login);

        vv = findViewById(R.id.vvBgvid);

        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.fondoapptest2

        );

        vv.setVideoURI(uri);

        vv.start();

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediap = mp;

                mediap.setLooping(true);

                if(position != 0)
                {
                    mediap.seekTo(position);

                    mediap.start();
                }
            }
        });
    }
}
