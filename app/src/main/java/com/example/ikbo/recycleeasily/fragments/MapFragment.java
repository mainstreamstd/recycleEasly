package com.example.ikbo.recycleeasily.fragments;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ikbo.recycleeasily.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.location.MyLocationOverlay;
import ru.yandex.yandexmapkit.utils.GeoPoint;

public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";





    MapController mMapController;
    OverlayManager mOverlayManager;
    Overlay mOverlay;


    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        MapView mapView = (MapView) view.findViewById(R.id.map);

        mapView.showBuiltInScreenButtons(true);
        mMapController = mapView.getMapController();
        mOverlayManager = mMapController.getOverlayManager();
        mOverlayManager.getMyLocation().setEnabled(true);

        showObjects(view);
        return view;

    }



    public void showObjects(View v){



        Resources res = getResources();
        mOverlay = new Overlay(mMapController);
        InputStream is = this.getResources().openRawResource(R.raw.map);
        byte [] buffer = new byte[0];
        try {
            buffer = new byte[is.available()];
            while (is.read(buffer)!=-1);
            String jsoncontext = new String(buffer);

            JSONArray entires = null;
            try {
                entires = new JSONArray(jsoncontext);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i<entires.length();i++) {
                try {
                    JSONObject post = entires.getJSONObject(i);


                    final OverlayItem item = new OverlayItem(new GeoPoint(post.getJSONObject("fields").getDouble("position_x") , post.getJSONObject("fields").getDouble("position_y")), res.getDrawable(R.drawable.shop));
                    BalloonItem balloon = new BalloonItem(getContext(),item.getGeoPoint());
                    balloon.setText(
                            post.getJSONObject("fields").getString("name") +
                            "\nАдрес: " +
                            post.getJSONObject("fields").getString("address") +
                            "\nТелефон: " +  post.getJSONObject("fields").getString("telephone") +
                            "\n" + post.getJSONObject("fields").getString("description"));

                    item.setBalloonItem(balloon);
                    mOverlay.addOverlayItem(item);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        mOverlayManager.addOverlay(mOverlay);



    }






    @Override
    public void onDetach() {
        super.onDetach();

    }

}
