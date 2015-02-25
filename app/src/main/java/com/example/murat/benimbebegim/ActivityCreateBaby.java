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
        import android.content.SharedPreferences;
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


public class ActivityCreateBaby extends Activity implements OnClickListener {

    ImageView imgSelectBabyPicture;
    EditText edtNameCreateBaby, edtWeightCreateBaby, edtHeightCreateBaby;
    Button btnDatePicker, btnTimePicker, btnCancelCreateBaby, btnOkCreateBaby,btnTheme;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateForDB;
    TimePickerDialog.OnTimeSetListener timeForDB;
    private static final int DEMO_DIALOG = 1;
    final Context context = this;
    String[] color_list = { "Red", "Green", "Blue"};

    LinearLayout colorlayout;
    String selectedDate, selectedTime, strTime, strDate, getBabyName,
            getBabyWeight, getBabyHeight, getUserId,
            selectedGendersForCreateBaby;

    /*
    Variables for Database
     */
    InputStream is=null;
    String result=null;
    String line=null;
    int code;
    /*
    Variables For SharedPreferences
     */
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private String[] genders = {"MALE", "FEMALE"};
    private Spinner spinnerSelectGender;
    private ArrayAdapter<String> dataAdapterForGender;

    // Baby Picture capture Variables
    Intent i;
    final static int cameraData = 0;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    public static Uri selectedImageUri;
    String getUserIDBabyCreate;

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
        btnTheme=(Button)findViewById(R.id.btnTheme_createBaby);

        /****************************
         * Set Click Listener to Buttons
         */
        btnDatePicker.setOnClickListener((OnClickListener) this);
        btnTimePicker.setOnClickListener((OnClickListener) this);
        btnCancelCreateBaby.setOnClickListener((OnClickListener) this);
        btnOkCreateBaby.setOnClickListener((OnClickListener) this);
        imgSelectBabyPicture.setOnClickListener((OnClickListener) this);
        btnTheme.setOnClickListener((OnClickListener) this);
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
                Calendar c_date = Calendar.getInstance();
                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
                strDate = date.format(c_date.getTime());
                btnDatePicker.setText(strDate);
                Calendar c_time = Calendar.getInstance(TimeZone.getDefault());
                SimpleDateFormat time = new SimpleDateFormat("HH:mm",
                        Locale.getDefault());
                strTime = time.format(c_time.getTime());
                btnTimePicker.setText(strTime);
                break;

            case R.id.btnOk_createBaby:
                /******************
                 * Get the values on the screen
                 */
                getBabyName = edtNameCreateBaby.getText().toString();
                Bundle basket = getIntent().getExtras();
                getUserIDBabyCreate = basket.getString("userid");

                /******************
                 * Check Date and Time Picker Values null or not
                 */
                if (btnDatePicker.getText().equals(strDate)) {
                    selectedDate = btnDatePicker.getText().toString();
                }
                if (btnTimePicker.getText().equals(strTime)) {
                    selectedTime = btnTimePicker.getText().toString();
                }
                /******************
                 * Checked Empty Areas For All Records
                 */
                if (getBabyName.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Bebek İsmi Boş Bırakılamaz!!!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (selectedGendersForCreateBaby.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Cinsiyet Bilgisini Seçiniz!!!", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                else {
                    /******************
                     * Save the datas to Database
                     */
                    insert();
                }

                break;
            case R.id.ivBabyPicture_createBaby:
                imgSelectBabyPicture.showContextMenu();
                registerForContextMenu(imgSelectBabyPicture);
                openContextMenu(imgSelectBabyPicture);
                unregisterForContextMenu(imgSelectBabyPicture);
                Log.i("sdf", "bas");

                break;

            case R.id.btnTheme_createBaby:
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.select_theme);
                dialog.setTitle("Select Theme");

                // set the custom dialog components - text, image and button
                Button blue = (Button) dialog.findViewById(R.id.blue);
                Button green  = (Button) dialog.findViewById(R.id.green);
                Button yellow  = (Button) dialog.findViewById(R.id.yellow);
                Button black  = (Button) dialog.findViewById(R.id.black);
                Button white  = (Button) dialog.findViewById(R.id.white);
                Button gray  = (Button) dialog.findViewById(R.id.gray);
                Button orange  = (Button) dialog.findViewById(R.id.orange);
                Button pink  = (Button) dialog.findViewById(R.id.pink);
                Button purple  = (Button) dialog.findViewById(R.id.purple);
                Button cancel  = (Button) dialog.findViewById(R.id.cancel);

                blue.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Hepsini birden nasıl değiştiririz onu bul.
                        LinearLayout createBaby=(LinearLayout)findViewById(R.id.layout_Create_Baby);
                        Drawable drawable = getResources().getDrawable(R.drawable.bg_gradient_blue);
                        Drawable reddrawable=getResources().getDrawable(R.drawable.bg_gradient);
                        reddrawable=drawable;
                        createBaby.setBackgroundDrawable(drawable);
                        dialog.dismiss();

                    }
                });

               dialog.show();

            default:
                break;
        }
    }

    private void insert() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name", getBabyName));
        Log.e("name", getBabyName);
        nameValuePairs.add(new BasicNameValuePair("date", selectedDate));
        Log.e("date", selectedDate);
        nameValuePairs.add(new BasicNameValuePair("time", selectedTime));
        Log.e("time", selectedTime);
        nameValuePairs.add(new BasicNameValuePair("image", selectedImageUri.toString()));
        Log.e("image", selectedImageUri.toString());
        nameValuePairs.add(new BasicNameValuePair("UID",getUserIDBabyCreate));
        Log.e("uid", getUserIDBabyCreate);
        nameValuePairs.add(new BasicNameValuePair("gender",selectedGendersForCreateBaby));
        Log.e("gender",selectedGendersForCreateBaby);
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
                Log.i("burdayim", "buragya gelebiliyorum");
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
                if (data.getData() != null) {
                    selectedImageUri = data.getData();
                    imgSelectBabyPicture.setImageURI(selectedImageUri);
                    selectedImagePath = getPath(selectedImageUri);
                }else if( data.getData() == null) {
                    Log.i("data.getData()","abi burası null dönüyor");
                }
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
        String[] projection = {MediaStore.Images.Media.DATA};
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

    /**
     * Example of using Color Picker in Alert Dialog.
     */
    private void showColorPickerDialogDemo() {

        int initialColor = Color.WHITE;

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                showToast(color);
            }

        });
        colorPickerDialog.show();

    }

    /**
     * Displays Toast with RGB values of given color.
     *
     * @param color the color
     */
    private void showToast(int color) {
        String rgbString = "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color) +"Yazdı";
        Toast.makeText(this, rgbString, Toast.LENGTH_SHORT).show();
        ActivityCreateBaby.this.findViewById(android.R.id.content)
                .setBackgroundColor(color);

    }
}
