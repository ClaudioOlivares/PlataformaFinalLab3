package ui.slideshow;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;


import com.example.plataformafinallab3.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private ImageView iv;
    private VideoView vv;
    private MediaPlayer mediap;
    private FrameLayout fl;
    private int position;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        fl = root.findViewById(R.id.frameLayout2);
        getActivity().getCurrentFocus().clearFocus();
        fl.setFocusable(false);


        return root;
    }



}
