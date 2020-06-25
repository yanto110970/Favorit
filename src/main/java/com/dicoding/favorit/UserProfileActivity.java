package com.dicoding.favorit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dicoding.favorit.adapter.ListFollowerAdapter;
import com.dicoding.favorit.adapter.SectionsPagerAdapter;
import com.dicoding.favorit.entity.Follower;
import com.dicoding.favorit.entity.UserProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    public static final String EXTRA_USERPROFILE = "extra_userprofile";
    final String APP_ID = "0f23ac603670cc556ae93b03f08fde2026294d39";
    String userid = "yanto110970";
    private ArrayList<Follower> listFollower;
    private ListFollowerAdapter adapterfollower;
    private FloatingActionButton favadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("User Profile");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        UserProfile UserProfile = getIntent().getParcelableExtra(EXTRA_USERPROFILE);
        final String userid   = UserProfile.getLogin();
        final String avatar   = UserProfile.getAvatar();
        final String userurl  = UserProfile.getUserurl();
        favadd = findViewById(R.id.fav_add);
        favadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile UserProfile = new UserProfile();
                UserProfile.setLogin(userid);
                UserProfile.setAvatar(avatar);
                UserProfile.setUserurl(userurl);
                Intent moveWithObjectIntent = new Intent(UserProfileActivity.this, AddFavoritActivity.class);
                moveWithObjectIntent.putExtra(FavoritActivity.EXTRA_USERPROFILE,UserProfile);
                startActivity(moveWithObjectIntent);
            }
        });
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        getLogin(userid);
        getSupportActionBar().setElevation(0);
    }

    public void getLogin(String login) {
        AndroidNetworking.initialize(getApplicationContext());
        String url = "https://api.github.com/users/"+ login ;
        AndroidNetworking.get(url)
                .addPathParameter("username", userid)
                .addHeaders("Authorization", "token "+APP_ID)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String user = response.getString("login");
                            String avatar = response.getString("avatar_url");
                            String follower = response.getString("followers");
                            String following = response.getString("following");
                            String location = response.getString("location");
                            String company = response.getString("company");
                            String followerurl = response.getString("followers_url");
                            String followingurl = response.getString("following_url");


                            listFollower = new ArrayList<>();
                            getFollower(followerurl);


                            ImageView imgPhoto = findViewById(R.id.photo);
                            TextView tv_name = findViewById(R.id.tv_name);
                            TextView tv_location = findViewById(R.id.tv_location);
                            TextView tv_company = findViewById(R.id.tv_company);
                            TextView tv_description = findViewById(R.id.tv_description);
                            Picasso.get().load(avatar).into(imgPhoto);
                            tv_name.setText(user);
                            if (location == "null") {
                                tv_location.setText("");
                            }else{
                                tv_location.setText(location);
                            }
                            if (company == "null") {
                                tv_company.setText("");
                            }else{
                                tv_company.setText(company);
                            }
                            String description = "Follower : " + follower + " Following : " + following;
                            tv_description.setText(description);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public void getFollower(String url) {
        AndroidNetworking.initialize(getApplicationContext());
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
                                String name  = "";
                                Follower follower = new Follower();
                                follower.setUserid(user);
                                follower.setName(name);
                                follower.setAvatar(avatar);
                                listFollower.add(follower);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(UserProfileActivity.this, "Data tidak Ada" , Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void getFollowing(String url) {

    }
}