package com.dicoding.favorit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.favorit.R;
import com.dicoding.favorit.adapter.FavoritAdapter;
import com.dicoding.favorit.db.DatabaseContract;
import com.dicoding.favorit.entity.Favorit;
import com.dicoding.favorit.helper.MappingHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoritActivity extends AppCompatActivity implements LoadFavoritsCallback {
    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private FavoritAdapter adapter;
    public static final String EXTRA_USERPROFILE = "extra_userprofile";

    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Notes");

        progressBar = findViewById(R.id.progressbar);
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        rvNotes.setHasFixedSize(true);
        adapter = new FavoritAdapter(this);
        rvNotes.setAdapter(adapter);

        FloatingActionButton fabExit = findViewById(R.id.fav_ex);
        fabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(FavoritActivity.this, MainActivity.class);
                startActivity(moveIntent);
            }
        });

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.FavorColumns.CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadFavoritAsync(this, this).execute();
        } else {
            ArrayList<Favorit> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListFavorits(list);
                Toast.makeText(FavoritActivity.this,  "lewat" , Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListFavorits());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Favorit> favorits) {
        progressBar.setVisibility(View.INVISIBLE);
        if (favorits.size() > 0) {
            adapter.setListFavorits(favorits);
        } else {
            adapter.setListFavorits(new ArrayList<Favorit>());
            showSnackbarMessage();
        }
    }

    private static class LoadFavoritAsync extends AsyncTask<Void, Void, ArrayList<Favorit>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoritsCallback> weakCallback;

        private LoadFavoritAsync(Context context, LoadFavoritsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Favorit> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(DatabaseContract.FavorColumns.CONTENT_URI, null, null, null, null);
            assert dataCursor != null;
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Favorit> favorits) {
            super.onPostExecute(favorits);
            weakCallback.get().postExecute(favorits);
        }
    }

    private void showSnackbarMessage() {
        Snackbar.make(rvNotes, "Tidak ada data saat ini", Snackbar.LENGTH_SHORT).show();
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadFavoritAsync(context, (LoadFavoritsCallback) context).execute();

        }
    }
}

interface LoadFavoritsCallback {
    void preExecute();
    void postExecute(ArrayList<Favorit> favorits);
}