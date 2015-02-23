package com.example.murat.benimbebegim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


public class ActivityCreateBaby extends Activity implements OnClickListener {

    ImageView imgSelectBabyPicture;
    EditText edtNameCreateBaby, edtWeightCreateBaby, edtHeightCreateBaby;
    Button btnDatePicker, btnTimePicker, btnCancelCreateBaby, btnOkCreateBaby;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateForDB;
    TimePickerDialog.OnTimeSetListener timeForDB;

    String selectedDate, selectedTime, strTime, strDate, getBabyName,
            getBabyWeight, getBabyHeight, getUserId,
            selectedGendersForCreateBaby;

    private String[] genders = { "MALE", "FEMALE" };
    private Spinner spinnerSelectGender;
    private ArrayAdapter<String> dataAdapterForGender;

    // Baby Picture capture Variables
    Intent i;
    final static int cameraData = 0;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    public static Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_baby);
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        /****************************
         * INIT COMPONENTS
         */
        btnDatePicker = (Button) findViewById(R.id.btnDate_createBaby);
        btnTimePicker = (Button) findViewById(R.id.btnTime_createBaby);
        btnCancelCreateBaby = (Button) findViewById(R.id.btnCancel_createBaby);
        btnOkCreateBaby = (Button) findViewById(R.id.btnOk_createBaby);
        edtNameCreateBaby = (EditText) findViewById(R.id.etBabyName_createBaby);
        imgSelectBabyPicture = (ImageView) findViewById(R.id.ivBabyPicture_createBaby);
        spinnerSelectGender = (Spinner) findViewById(R.id.gender_spinner_createBaby);
        /****************************
         * Set Click Listener to Buttons
         */
        btnDatePicker.setOnClickListener((OnClickListener) this);
        btnTimePicker.setOnClickListener((OnClickListener) this);
        btnCancelCreateBaby.setOnClickListener((OnClickListener) this);
        btnOkCreateBaby.setOnClickListener((OnClickListener) this);
        imgSelectBabyPicture.setOnClickListener((OnClickListener) this);
        /****************************
         * Datas For Genders
         */
        dataAdapterForGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genders);
        dataAdapterForGender
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectGender.setAdapter(dataAdapterForGender);
        spinnerSelectGender
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {

                        if (parent.getSelectedItem().toString()
                                .equals(genders[0]))
                            selectedGendersForCreateBaby = "MALE";
                        else if (parent.getSelectedItem().toString()
                                .equals(genders[1]))
                            selectedGendersForCreateBaby = "FEMALE";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        /******************
         * Naming The Date Button
         */
        Calendar c_date = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
        strDate = date.format(c_date.getTime());
        btnDatePicker.setText(strDate);

        /******************
         * Naming The Time Button
         */
        Calendar c_time = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat time = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        strTime = time.format(c_time.getTime());
        btnTimePicker.setText(strTime);

        /******************
         * Getting The Selected Date Value
         */
        dateForDB = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/"
                        + year;
                btnDatePicker.setText(selectedDate);

            }
        };
        /******************
         * Get the Selected Time Value
         */
        timeForDB = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                selectedTime = myCalendar.getTime().toString()
                        .substring(11, 16);
                btnTimePicker.setText(selectedTime);
            }
        };
    }

    public void setGetBabyName(String getBabyName) {
        this.getBabyName = getBabyName;
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnDate_createBaby:
                /******************
                 * Show the Date Picker Dialog
                 */
                new DatePickerDialog(ActivityCreateBaby.this, dateForDB,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.btnTime_createBaby:
                /******************
                 * Show the Time Picker Dialog
                 */
                new TimePickerDialog(ActivityCreateBaby.this, timeForDB,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
                break;
            case R.id.btnCancel_createBaby:
                /******************
                 * Refress all areas
                 */
                edtNameCreateBaby.setText(null);
                edtWeightCreateBaby.setText(null);
                edtHeightCreateBaby.setText(null);
                Calendar c_date = Calendar.getInstance();
                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
                strDate = date.format(c_date.getTime());
                btnDatePicker.setText(strDate);
                Calendar c_time = Calendar.getInstance(TimeZone.getDefault());
                SimpleDateFormat time = new SimpleDateFormat("HH:mm a",
                        Locale.getDefault());
                strTime = time.format(c_time.getTime());
                btnTimePicker.setText(strTime);
                // Intent editBaby = new Intent(getApplicationContext(),
                // EditBabyInfoActivity.class);
                // startActivity(editBaby);
                break;

            case R.id.btnOk_createBaby:

                break;
            case R.id.ivBabyPicture_createBaby:
                imgSelectBabyPicture.showContextMenu();
                registerForContextMenu(imgSelectBabyPicture);
                openContextMenu(imgSelectBabyPicture);
                unregisterForContextMenu(imgSelectBabyPicture);
                Log.i("sdf", "bas");

                break;
            default:
                break;
        }
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        ImageView imgSelectPicture = (ImageView) v
                .findViewById(R.id.ivBabyPicture_createBaby);
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
                // Biraz arastırmamız gerekiyor
                // En son açılan resimler seçilemiyor !!!!!!.....
                Log.i("burdayim","buragya gelebiliyorum");
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
                Log.i("acilis_data",selectedImageUri.toString());
                selectedImagePath = getPath(selectedImageUri);
                Log.i("acilis_returnpath",selectedImagePath);
                imgSelectBabyPicture.setImageURI(selectedImageUri);
            } else {
                Bundle extras = data.getExtras();
                Bitmap bmp = (Bitmap) extras.get("data");
                imgSelectBabyPicture.setImageBitmap(bmp);
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
