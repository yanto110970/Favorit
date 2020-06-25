package com.dicoding.favorit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.dicoding.favorit.adapter.ListFollowerAdapter;
import com.dicoding.favorit.entity.Follower;
import com.dicoding.favorit.entity.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FollowerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String EXTRA_USERPROFILE = "extra_userprofile";
    private ProgressBar progressBar2;
    private RecyclerView rvFollower;
    private ArrayList<Follower> listFollower = new ArrayList<>();
    final String APP_ID = "0f23ac603670cc556ae93b03f08fde2026294d39";
    String userid = "yanto110970";

    public FollowerFragment() {
    }

    public static FollowerFragment newInstance(int index) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_follower, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        final UserProfile UserProfile = requireActivity().getIntent().getParcelableExtra(EXTRA_USERPROFILE);
        assert UserProfile != null;
        String follower_url = UserProfile.getFollower();
        progressBar2 = view.findViewById(R.id.progressBar2);
        ArrayList<Follower> listFollower = new ArrayList<>();
        getListFollower(follower_url);


    }

    private void getListFollower(String url) {
        progressBar2.setVisibility(View.VISIBLE);
        AndroidNetworking.initialize(requireActivity().getApplicationContext());
        AndroidNetworking.get(url)
                .addPathParameter("username", userid)
                .addHeaders("Authorization", "token "+APP_ID)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject data = response.getJSONObject(i);
                                //adding the product to product list
                                String user = data.getString("login");
                                String avatar = data.getString("avatar_url");
                                String name = "";
                                Follower Follower = new Follower();
                                Follower.setUserid(user);
                                Follower.setName(name);
                                Follower.setAvatar(avatar);
                                listFollower.add(Follower);
                            }
                            RecyclerView rvFollower = getView().findViewById(R.id.rv_followers);
                            rvFollower.setHasFixedSize(true);
                            rvFollower.setLayoutManager(new LinearLayoutManager(getContext()));
                            DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.line)));
                            rvFollower.addItemDecoration(itemDecoration);
                            ListFollowerAdapter listFollowerAdapter = new ListFollowerAdapter(listFollower);
                            rvFollower.setAdapter(listFollowerAdapter);
                            progressBar2.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(getActivity(), "Data tidak Ada" , Toast.LENGTH_SHORT).show();
                    }
                });

    }


}