package com.dicoding.favorit;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.favorit.entity.Favorit;
import com.dicoding.favorit.entity.UserProfile;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.AVATAR;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.CONTENT_URI;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.DATE;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.USERID;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.USERNAME;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.USERURL;

public class AddFavoritActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_USERPROFILE = "extra_userprofile";
    public static final String EXTRA_NOTE = "extra_note";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;
    private Uri uriWithId;
    private EditText edtUserid, edtUsername, edtAvatar, edtUserurl;
    private ImageView imgPhoto;
    private Button btnSubmit;
    private boolean isEdit = false;
    private Favorit favorit;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorit);



        edtUserid = findViewById(R.id.add_userid);
        edtUsername = findViewById(R.id.add_username);
        edtUserurl = findViewById(R.id.add_userurl);
        imgPhoto =  findViewById(R.id.add_photo);
        btnSubmit = findViewById(R.id.btn_submit);

        favorit = new Favorit();

        String actionBarTitle;
        String btnTitle;

        actionBarTitle = getString(R.string.tambah);
        btnTitle = getString(R.string.simpan);

        UserProfile UserProfile = getIntent().getParcelableExtra(EXTRA_USERPROFILE);
        String userid = UserProfile.getLogin();
        String avatar   = UserProfile.getAvatar();
        String userurl  = UserProfile.getUserurl();

        uriWithId = Uri.parse(CONTENT_URI + "/userid/" + userid);

        if (uriWithId != null) {
            Cursor cursor = getContentResolver().query(uriWithId, null, null, null, null);

            if (cursor != null) {
                if(cursor.getCount()>0) {
                    Toast.makeText(AddFavoritActivity.this, "User telah dipilih favorit", Toast.LENGTH_SHORT).show();
                    Intent moveIntent = new Intent(AddFavoritActivity.this, FavoritActivity.class);
                    startActivity(moveIntent);
                }

            }
        }

        edtUserid.setText(userid);
        edtUsername.setText(userid);
        edtUserurl.setText(userurl);

        Picasso.get().load(avatar).into(imgPhoto);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setText(btnTitle);

        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            UserProfile UserProfile = getIntent().getParcelableExtra(EXTRA_USERPROFILE);
            String avatar   = UserProfile.getAvatar();

            String userid = edtUserid.getText().toString().trim();
            String username = edtUsername.getText().toString().trim();
            String userurl  = edtUsername.getText().toString().trim();

            ContentValues values = new ContentValues();
            values.put(USERID, userid);
            values.put(USERNAME, username);
            values.put(AVATAR, avatar);
            values.put(USERURL, userurl);
            favorit.setDate(getCurrentDate());
            values.put(DATE, getCurrentDate());

            uriWithId = Uri.parse(CONTENT_URI + "/userid/" + userid);

            if (uriWithId != null) {
                Cursor cursor = getContentResolver().query(uriWithId, null, null, null, null);

                if (cursor != null) {
                    if(cursor.getCount()>0) {
                        Toast.makeText(AddFavoritActivity.this, "User telah dipilih favorit", Toast.LENGTH_SHORT).show();
                    }else{
                        getContentResolver().insert(CONTENT_URI, values);
                        Toast.makeText(AddFavoritActivity.this, "User telah ditambahkan favorit", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent moveIntent = new Intent(AddFavoritActivity.this, FavoritActivity.class);
                startActivity(moveIntent);
            }

        }
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan untuk memilih user ini sebagai favorit?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Note";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            Intent moveIntent = new Intent(AddFavoritActivity.this, FavoritActivity.class);
                            startActivity(moveIntent);
                        } else {
                            getContentResolver().delete(uriWithId, null, null);
                            Toast.makeText(AddFavoritActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                            Intent moveIntent = new Intent(AddFavoritActivity.this, FavoritActivity.class);
                            startActivity(moveIntent);
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}