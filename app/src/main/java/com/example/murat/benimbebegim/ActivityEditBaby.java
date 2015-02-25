package com.example.murat.benimbebegim;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 * Created by aytunc on 16.2.2015.
 */
public class ActivityEditBaby extends Activity implements OnClickListener{
    ImageView imgSelectedPicture_BabyEdit;
    Button btnDatePicker_BabyEdit, btnTimePicker_BabyEdit, btnCancel_BabyEdit,
            btnDelete_BabyEdit, btnOk_BabyEdit,btnTheme_BabyEdit;
    EditText edtGetBabyName_BabyEdit;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateForDB_BabyEdit;
    TimePickerDialog.OnTimeSetListener timeForDB_BabyEdit;

    InputStream is=null;
    String result=null;
    String line=null;
    int code;

    String getUserId_BabyEdit;
    String selectedDate_BabyEdit, selectedTime_BabyEdit, strTime_BabyEdit,
            strDate_BabyEdit, selectedGendersForEditBaby,gettingImage;

    private String[] genders_EditBaby = { "MALE", "FEMALE" };
    private Spinner spinnerSelectGender_EditBaby;
    private ArrayAdapter<String> dataAdapterForGender_EditBaby;


    // Baby Picture capture Variables
    Intent i;
    final static int cameraData = 0;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_baby);
        InitUI();
    }// End OnCreate

    private void InitUI() {
        // TODO Auto-generated method stub
        /****************************
         * INIT COMPONENTS
         */
        imgSelectedPicture_BabyEdit = (ImageView) findViewById(R.id.ivBabyPicture_BabyEdit);
        btnDatePicker_BabyEdit = (Button) findViewById(R.id.btnDate_BabyEdit);
        btnTimePicker_BabyEdit = (Button) findViewById(R.id.btnTime_BabyEdit);
        btnCancel_BabyEdit = (Button) findViewById(R.id.btnCancel_BabyEdit);
        btnDelete_BabyEdit = (Button) findViewById(R.id.btnDelete_BabyEdit);
        btnOk_BabyEdit = (Button) findViewById(R.id.btnOk_BabyEdit);
        edtGetBabyName_BabyEdit = (EditText) findViewById(R.id.edtBabyName_BabyEdit);
        spinnerSelectGender_EditBaby = (Spinner) findViewById(R.id.gender_spinner_EditBaby);
        btnTheme_BabyEdit=(Button)findViewById(R.id.btnTheme_BabyEdit);
        /****************************
         * Set Click Listener to Buttons
         */
        btnDatePicker_BabyEdit.setOnClickListener((OnClickListener) this);
        btnTimePicker_BabyEdit.setOnClickListener((OnClickListener) this);
        btnCancel_BabyEdit.setOnClickListener((OnClickListener) this);
        btnDelete_BabyEdit.setOnClickListener((OnClickListener) this);
        btnOk_BabyEdit.setOnClickListener((OnClickListener) this);
        imgSelectedPicture_BabyEdit.setOnClickListener((OnClickListener) this);
        btnTheme_BabyEdit.setOnClickListener((OnClickListener)this);

        getValuesFromDatabases();
        /****************************
         * Datas For Genders
         */
        dataAdapterForGender_EditBaby = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genders_EditBaby);
        dataAdapterForGender_EditBaby
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectGender_EditBaby.setAdapter(dataAdapterForGender_EditBaby);
        spinnerSelectGender_EditBaby
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // Hangi il seçilmişse onun ilçeleri adapter'e
                        // ekleniyor.
                        if (parent.getSelectedItem().toString()
                                .equals(genders_EditBaby[0]))
                            selectedGendersForEditBaby = "MALE";
                        else if (parent.getSelectedItem().toString()
                                .equals(genders_EditBaby[1]))
                            selectedGendersForEditBaby = "FEMALE";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        /******************
         * Getting The Selected Date Value
         */
        dateForDB_BabyEdit = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate_BabyEdit = dayOfMonth + "/" + (monthOfYear + 1)
                        + "/" + year;
                btnDatePicker_BabyEdit.setText(selectedDate_BabyEdit);

            }
        };
        /******************
         * Get the Selected Time Value
         */
        timeForDB_BabyEdit = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                selectedTime_BabyEdit = myCalendar.getTime().toString()
                        .substring(11, 16);
                btnTimePicker_BabyEdit.setText(selectedTime_BabyEdit);
            }
        };

    }

    private void getValuesFromDatabases() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name", getBabyName));
        nameValuePairs.add(new BasicNameValuePair("date", selectedDate));
        nameValuePairs.add(new BasicNameValuePair("time", selectedTime));
        nameValuePairs.add(new BasicNameValuePair("image", selectedImageUri.toString()));
        nameValuePairs.add(new BasicNameValuePair("UID",getUserIDBabyCreate));
        nameValuePairs.add(new BasicNameValuePair("gender",selectedGendersForCreateBaby));;
        nameValuePairs.add(new BasicNameValuePair("theme","Şimdilik Boş"));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://176.58.88.85/~murat/insert_create_baby.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("KontrolÖnceCreateBaby", result);
        } catch (Exception e) {
            Log.e("CreateBabyFail2", e.toString());
        }

        try {
            JSONObject json_data = new JSONObject(result);
            code = json_data.getInt("code");
            Log.e("CreateBabyCode", (String.valueOf(code)));
            /******************
             *  Checked record is inserted or not
             */
            if (code == 1) {
                Toast.makeText(getBaseContext(), "kayıt başarıyla eklendi.",
                        Toast.LENGTH_SHORT).show();
            }
            /******************"
             *  Chech userName is exist or not
             */
            else if (code == 2) {
                Toast.makeText(getBaseContext(), "Bu kullanıcıya ait" + getBabyName + "     isimli bir kayıt mevcut!!!",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getBaseContext(), "Sorry, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("CreateBabyFail3", e.toString());
        } finally {
            Log.e("CreateBabyFinally", (String.valueOf(code)));
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnDate_BabyEdit:
                /******************
                 * Show the Date Picker Dialog
                 */
                new DatePickerDialog(ActivityEditBaby.this, dateForDB_BabyEdit,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btnTime_BabyEdit:
                /******************
                 * Show the Time Picker Dialog
                 */
                new TimePickerDialog(ActivityEditBaby.this, timeForDB_BabyEdit,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
                break;
            case R.id.btnCancel_BabyEdit:

                break;
            case R.id.btnDelete_BabyEdit:

                break;
            case R.id.btnOk_BabyEdit:

                break;
            case R.id.ivBabyPicture_BabyEdit:
                imgSelectedPicture_BabyEdit.showContextMenu();
                registerForContextMenu(imgSelectedPicture_BabyEdit);
                openContextMenu(imgSelectedPicture_BabyEdit);
                unregisterForContextMenu(imgSelectedPicture_BabyEdit);
                Log.i("sdf", "bas");
            default:
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        ImageView imgSelectPicture = (ImageView) v
                .findViewById(R.id.ivBabyPicture_BabyEdit);
        menu.setHeaderTitle("ADD PHOTO");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_opening, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case R.id.itemTakePicture:
                i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cameraData);
                break;

            case R.id.itemChooseFromGallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        SELECT_PICTURE);
                break;
            case R.id.itemCancel:
                // Biraz arastırmamız gerekiyor
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                imgSelectedPicture_BabyEdit.setImageURI(selectedImageUri);
            } else {
                Bundle extras = data.getExtras();
                Bitmap bmp = (Bitmap) extras.get("data");
                imgSelectedPicture_BabyEdit.setImageBitmap(bmp);
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

}
