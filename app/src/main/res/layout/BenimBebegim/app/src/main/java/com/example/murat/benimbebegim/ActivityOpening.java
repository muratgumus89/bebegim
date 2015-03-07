package layout.BenimBebegim.app.src.main.java.com.example.murat.benimbebegim;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murat.benimbebegim.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by aytunc on 16.2.2015.
 */
public class ActivityOpening extends Activity implements View.OnClickListener {
    /*
     Variables For Xml Components
    */
    TextView txtSign;
    EditText etUserName, etPassword;
    Button btnLogin;
    String userNameforLogin, passwordforLogin;
    CheckBox cbRememberMe;
     /*
     Variables For MySql Connections
     */
    InputStream is = null;
    String result = null;
    String strUserIDOpening;
    String line = null;
    int code;
     /*
     Variables For SharedPreferences
     */
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //For RememberMe
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_activity_opening);
        initUI();
    }

    private void initUI() {
         /*
         Initialize Xml Components
         */
        txtSign = (TextView) findViewById(R.id.txtSign_Opening);
        txtSign.setOnClickListener(this);
        etUserName = (EditText) findViewById(R.id.edtEmail_Opening);
        etPassword = (EditText) findViewById(R.id.edtPassword_Opening);
        btnLogin = (Button) findViewById(R.id.btnLogin_Opening);
        btnLogin.setOnClickListener(this);
        cbRememberMe = (CheckBox) findViewById(R.id.check_Remember_Me_Opening);
        cbRememberMe.setOnClickListener(this);
        /*
         Save Remember User or Not
         */
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Log.i("info", "pref cekildi");
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);
        boolean b = pref.getBoolean("remembers", false);
        if (b == true) {
            etUserName.setText(username);
            etPassword.setText(password);
            cbRememberMe.setChecked(true);
            Log.i("info", "editTextleri Ben doldurdum");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSign_Opening:
                /*****************************
                 * Signup Button
                 */
                Intent intentSignUP = new Intent(getApplicationContext(),
                        com.example.murat.benimbebegim.ActivitySignUp.class);
                startActivity(intentSignUP);
                break;
            case R.id.btnLogin_Opening:
                userNameforLogin = etUserName.getText().toString();
                passwordforLogin = etPassword.getText().toString();
                if (userNameforLogin.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.valid_User_name, Toast.LENGTH_LONG).show();
                    return;
                }
                else if(passwordforLogin.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.valid_Password, Toast.LENGTH_LONG).show();
                    return;
                }else
                {
                    loginControl();
                }
                break;


            default:
                break;
        }
    }

    private void loginControl() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user_name", userNameforLogin));
        nameValuePairs.add(new BasicNameValuePair("password", passwordforLogin));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://176.58.88.85/~murat/login.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("LoginButtonCon", "connection success ");
        } catch (Exception e) {
            Log.e("LoginButtonFail", e.toString());
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
            ;
        } catch (Exception e) {
            Log.e("LoginButtonFail2", e.toString());
        }

        try {
            JSONObject json_data = new JSONObject(result);
            code = json_data.getInt("code");
            Log.e("LoginBottonContentCode", (String.valueOf(code)));
            /******************
             *  Checked record is inserted or not
             */
            if (code == 2) {
                Toast.makeText(getBaseContext(), R.string.wrong_User_Name,
                        Toast.LENGTH_SHORT).show();
            }
            /******************
             *  Chech userName is exist or not
             */
            else if (code == 1) {
                Toast.makeText(getBaseContext(),R.string.wrong_Password,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), R.string.login_succesfully,
                        Toast.LENGTH_LONG).show();
                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences nesnesi oluşturuluyor ve prefernces referansına bağlanıyor
                editor = preferences.edit(); //aynı şekil editor nesnesi oluşturuluyor
                strUserIDOpening = getUserId(userNameforLogin);
                // Buraya bak murat
                editor.putString("user_id", strUserIDOpening);//email değeri
                Log.d("OpeningEditörUserId", preferences.getString("user_id", ""));
                editor.putString("baby_id", getBabyID(strUserIDOpening));
                Log.d("OpeningEditörBabyId", preferences.getString("baby_id", ""));
                //editor.putString("sifre", sifre_string);//şifre değeri
                //editor.putBoolean("login", true);//uygulamaya tekrar girdiğinde kontrol için kullanılcak
                //editor.putInt("sayısalDeger", 1000);// uygulamamızda kullanılmıyor ama göstermek amacıyla

                editor.commit();
                if (cbRememberMe.isChecked()) {
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                            .edit()
                            .putString(PREF_USERNAME, etUserName.getText().toString())
                            .putString(PREF_PASSWORD, etPassword.getText().toString())
                            .putBoolean("remembers", true)
                            .commit();
                    Log.i("info", "pref kaydedildi");
                } else {
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                            .edit()
                            .putString(PREF_USERNAME, "")
                            .putString(PREF_PASSWORD, "")
                            .putBoolean("remembers", false)
                            .commit();
                    Log.i("info", "cb checked edilmis değil ( kaydedilmedi )");
                }
                babyControl();
            }
        } catch (Exception e) {
            Log.e("LoginButtonFail 3", e.toString());
        } finally {
            Log.e("LoginButtonFinally", (String.valueOf(code)));
        }
    }

    private void babyControl() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        strUserIDOpening = getUserId(userNameforLogin);
        nameValuePairs.add(new BasicNameValuePair("user_id", strUserIDOpening));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://176.58.88.85/~murat/baby_control.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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
            ;
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }

        try {
            JSONObject json_data = new JSONObject(result);
            code = json_data.getInt("code");
            Log.e("kontrol", (String.valueOf(code)));
            /******************
             *  Checked record is inserted or not
             */
            if (code == 0) {
                Intent intentCreateBaby = new Intent(getApplicationContext(),
                        ActivityCreateBaby.class);
                Bundle b = new Bundle();
                b.putString("userid", strUserIDOpening);
                intentCreateBaby.putExtras(b);
                startActivity(intentCreateBaby);
            } else {

                Intent intentHomeScreen = new Intent(getApplicationContext(),
                        ActivityEditBaby.class);
                startActivity(intentHomeScreen);
            }
        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }
    }

    private String getBabyID(String strUserIDOpening)  {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("uid", strUserIDOpening));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://176.58.88.85/~murat/get_baby_id.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("BabyIdCon", "connection success ");
        } catch (Exception e) {
            Log.e("BabyIdFail", e.toString());
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
            Log.e("Babyresult", result);
        } catch (Exception e) {
            Log.e("BabyFail 2", e.toString());
        }
        return result;
    }

    private String getUserId(String userNameforLogin){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("email",userNameforLogin));
        nameValuePairs.add(new BasicNameValuePair("user_name",userNameforLogin));
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://176.58.88.85/~murat/get_user_id.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("UserIdCon", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("UserIdFail", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        try
        {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("result", result);
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }
        return result;
    }
}