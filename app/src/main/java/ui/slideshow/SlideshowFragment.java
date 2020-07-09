package ui.slideshow;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.plataformafinallab3.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private ImageView iv;
    private VideoView vv;
    private MediaPlayer mediap;
    private int position;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        iv = root.findViewById(R.id.iv);
        vv = root.findViewById(R.id.vv);

        Uri uri = Uri.parse("android.resource://"
                + getContext().getPackageName()
                + "/"
                + R.raw.pilgrimdemo2

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


        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vv.setVisibility(v.VISIBLE);
                vv.start();
                return true;

            }

        });

       iv.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               if(event.getAction() == MotionEvent.ACTION_UP)
               {
                    vv.setVisibility(v.INVISIBLE);
                    vv.stopPlayback();
               }
               return false;
           }
       });
        return root;
    }



}
