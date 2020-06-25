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
import com.dicoding.favorit.adapter.ListFollowingAdapter;
import com.dicoding.favorit.entity.Following;
import com.dicoding.favorit.entity.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FollowingFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String EXTRA_USERPROFILE = "extra_userprofile";
    private ProgressBar progressBar3;
    private RecyclerView rvFollowing;
    private ArrayList<Following> listFollowing = new ArrayList<>();
    final String APP_ID = "0f23ac603670cc556ae93b03f08fde2026294d39";
    String userid = "yanto110970";
    public FollowingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        final UserProfile UserProfile = requireActivity().getIntent().getParcelableExtra(EXTRA_USERPROFILE);
        assert UserProfile != null;
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.INVISIBLE);
        String following_url = UserProfile.getFollowing();
        ArrayList<Following> listFollower = new ArrayList<>();
        getListFollowing(following_url);
    }

    private void getListFollowing(String url) {
        progressBar3.setVisibility(View.VISIBLE);
        String Newurl = url.replace("{/other_user}","");
        AndroidNetworking.initialize(requireActivity().getApplicationContext());
        AndroidNetworking.get(Newurl)
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
                                Following following = new Following();
                                following.setUserid(user);
                                following.setName(name);
                                following.setAvatar(avatar);
                                listFollowing.add(following);
                            }
                            RecyclerView rvFollowing = getView().findViewById(R.id.rv_following);
                            rvFollowing.setHasFixedSize(true);
                            rvFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
                            DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.line)));
                            rvFollowing.addItemDecoration(itemDecoration);
                            ListFollowingAdapter listFollowingAdapter = new ListFollowingAdapter(listFollowing);
                            rvFollowing.setAdapter(listFollowingAdapter);
                            progressBar3.setVisibility(View.INVISIBLE);
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