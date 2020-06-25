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
import com.dicoding.favorit.helper.MappingHelper;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.AVATAR;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.CONTENT_URI;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.USERID;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.USERNAME;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.USERURL;

public class UpdDelFavoritActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_USERPROFILE = "extra_userprofile";
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;
    private Uri uriWithId;
    private ImageView imgPhoto;
    private EditText edtUserid, edtUsername, edtAvatar, edtUserurl;
    private Button btnSubmit;
    private boolean isEdit = false;
    private Favorit favorit;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_del_favorit);
        edtUserid = findViewById(R.id.edt_userid);
        edtUsername = findViewById(R.id.edt_username);
        imgPhoto = findViewById(R.id.edt_photo);
        edtUserurl = findViewById(R.id.edt_userurl);
        btnSubmit = findViewById(R.id.btn_submit);


        favorit = getIntent().getParcelableExtra(EXTRA_NOTE);

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        isEdit = true;

        String actionBarTitle;
        String btnTitle;

        uriWithId = Uri.parse(CONTENT_URI + "/" + favorit.getId());
        if (uriWithId != null) {
            Cursor cursor = getContentResolver().query(uriWithId, null, null, null, null);

            if (cursor != null) {
                favorit = MappingHelper.mapCursorToObject(cursor);
                cursor.close();
            }
        }

        actionBarTitle = getString(R.string.Ubah);
        btnTitle = getString(R.string.Update);

        edtUserid.setText(favorit.getUserid());
        edtUsername.setText(favorit.getUsername());
        edtUserurl.setText(favorit.getUserurl());
        Picasso.get().load(favorit.getAvatar()).into(imgPhoto);

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
            favorit = getIntent().getParcelableExtra(EXTRA_NOTE);
            String Avatar = favorit.getAvatar();
            String Userid = favorit.getUserid();
            String Userurl = favorit.getUserurl();

            String Username = edtUsername.getText().toString().trim();

            favorit.setUsername(Username);

            Intent intent = new Intent();
            intent.putExtra(EXTRA_NOTE, favorit);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();
            values.put(USERID, Userid);
            values.put(USERNAME, Username);
            values.put(AVATAR, Avatar);
            values.put(USERURL, Userurl);


            getContentResolver().update(uriWithId, values, null, null);
            Toast.makeText(UpdDelFavoritActivity.this, "Satu item berhasil diedit", Toast.LENGTH_SHORT).show();
            finish();

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
            dialogMessage = "Apakah anda ingin membatalkan perubahan Favorit?";
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
                            finish();
                        } else {
                            getContentResolver().delete(uriWithId, null, null);
                            Toast.makeText(UpdDelFavoritActivity.this, "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();
                            finish();
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