package com.dicoding.favorit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.favorit.adapter.ListGithubuserAdapter;
import com.dicoding.favorit.entity.Githubuser;
import com.dicoding.favorit.entity.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private ProgressBar progressBar;
    private ArrayList<Githubuser> arrayList;
    private ListGithubuserAdapter adapter;
    private String APP_ID = "APP_ID";
    String userid = "userid";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_frag1 = inflater.inflate(R.layout.fragment_home, container, false);
        String USER = getResources().getString(R.string.key_githubuser);
        String TOKEN = getResources().getString(R.string.key_token);
        Context MainActivity = getActivity();
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(MainActivity);
        String DEFAULT_VALUE = "Tidak Ada";
        userid = sh.getString(USER, DEFAULT_VALUE);
        APP_ID = sh.getString(TOKEN, DEFAULT_VALUE);
        setHasOptionsMenu(true);
        AndroidNetworking.initialize(requireActivity().getApplicationContext());
        progressBar = view_frag1.findViewById(R.id.progressBar);

        getListGithubuser();
        return view_frag1;

    }


    public void getListGithubuser() {
        arrayList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.get("https://api.github.com/users")
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
                                String follower = data.getString("followers_url");
                                String following = data.getString("following_url");
                                Githubuser githubusers = new Githubuser();
                                githubusers.setUserid(user);
                                githubusers.setName(name);
                                githubusers.setAvatar(avatar);
                                githubusers.setFollower(follower);
                                githubusers.setFollowing(following);
                                arrayList.add(githubusers);
                            }
                            RecyclerView recyclerView = requireView().findViewById(R.id.rv_githubusers);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.line)));
                            recyclerView.addItemDecoration(itemDecoration);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            adapter = new ListGithubuserAdapter(arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new  ListGithubuserAdapter.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(Githubuser data) {
                                    showSelectedlogin(data);
                                }
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Data tidak Ada" , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getFindGithubuser(String search) {
        arrayList = new ArrayList<>();
        String url = "https://api.github.com/search/users?q="+ search;
        Toast.makeText(getActivity(), url , Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        AndroidNetworking.get(url)
                .addPathParameter("username", userid)
                .addHeaders("Authorization", "token "+APP_ID)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {

                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject data = items.getJSONObject(i);
                                String user = data.getString("login");
                                String avatar = data.getString("avatar_url");
                                String userurl = data.getString("url");
                                String name = "";
                                String follower = data.getString("followers_url");
                                String following = data.getString("following_url");
                                Githubuser githubusers = new Githubuser();
                                githubusers.setUserid(user);
                                githubusers.setName(name);
                                githubusers.setUserurl(userurl);
                                githubusers.setAvatar(avatar);
                                githubusers.setFollower(follower);
                                githubusers.setFollowing(following);
                                arrayList.add(githubusers);
                            }
                            RecyclerView recyclerView = requireView().findViewById(R.id.rv_githubusers);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                            itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.drawable.line)));
                            recyclerView.addItemDecoration(itemDecoration);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            adapter = new ListGithubuserAdapter(arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickCallback(new  ListGithubuserAdapter.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(Githubuser data) {
                                    showSelectedlogin(data);
                                }
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Data  tidak Ada 2" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void showSelectedlogin(Githubuser githubuser) {
        UserProfile UserProfile = new UserProfile();
        UserProfile.setLogin(githubuser.getUserid());
        UserProfile.setAvatar(githubuser.getAvatar());
        UserProfile.setFollower(githubuser.getFollower());
        UserProfile.setFollowing(githubuser.getfollowing());
        Intent moveWithObjectIntent = new Intent(getActivity(), UserProfileActivity.class);
        moveWithObjectIntent.putExtra(UserProfileActivity.EXTRA_USERPROFILE,UserProfile);
        startActivity(moveWithObjectIntent);
    }


    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem searchIem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchIem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFindGithubuser(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                progressBar.setVisibility(View.VISIBLE);
                nextText = nextText.toLowerCase();
                ArrayList<Githubuser> dataFilter = new ArrayList<>();
                for(Githubuser data : arrayList){
                    String user = data.getUserid().toLowerCase();
                    if(user.contains(nextText)){
                        dataFilter.add(data);
                    }
                }
                adapter.setFilter(dataFilter);
                progressBar.setVisibility(View.INVISIBLE);
                return true;
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settingapp) {
            Intent moveIntent = new Intent(getActivity(), SettingActivity.class);
            startActivity(moveIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}