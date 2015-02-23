package com.example.murat.benimbebegim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aytunc on 16.2.2015.
 */
public class ActivitySignUp extends Activity implements View.OnClickListener {
    /******************
     *  Define the Variables
     */
    String user_name,name,surname,email,password,getConfPassword;
    InputStream is=null;
    String result=null;
    String line=null;
    EditText  edtUserName_Kayit,edtEmail_Kayit,edtPassword_Kayit,
            edtCon_password_Kayit,edtName_Kayit,edtSurname_Kayit;
    int code;
    boolean isValidEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_signup);
        /******************
         *  Get the Components
         */
        InitUI();
    }
    /******************
     *  Define Components
     */
    private void InitUI() {
        edtUserName_Kayit = (EditText) findViewById(R.id.username);
        edtEmail_Kayit = (EditText) findViewById(R.id.email);
        edtPassword_Kayit = (EditText) findViewById(R.id.password);
        edtName_Kayit = (EditText) findViewById(R.id.name);
        edtSurname_Kayit = (EditText) findViewById(R.id.surname);
        edtCon_password_Kayit = (EditText) findViewById(R.id.password_conf);
        Button btnRegister_Kayit = (Button) findViewById(R.id.register);
        btnRegister_Kayit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                /******************
                 *  Get the values on the screen
                 */
                user_name = edtUserName_Kayit.getText().toString();
                name = edtName_Kayit.getText().toString();
                surname = edtSurname_Kayit.getText().toString();
                email = edtEmail_Kayit.getText().toString();
                password = edtPassword_Kayit.getText().toString();
                getConfPassword = edtCon_password_Kayit.getText().toString();
                isValidEmail=isValidEmailAddress(email);
                /******************
                 *  check if null area or not
                 */

                if (user_name.equals("") || name.equals("") || surname.equals("")
                        || email.equals("") || password.equals("") || getConfPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Boş Alan Bırakmayınız!!!", Toast.LENGTH_LONG).show();
                    return;
                }

                /******************
                 *  check if both password matches
                 */

                else if (!password.equals(getConfPassword)) {
                    Toast.makeText(getApplicationContext(), "Şifreler Eşleşmiyor!!!", Toast.LENGTH_LONG).show();
                    return;
                }
                /******************
                 *  Check email is valid or not
                 */
                else if(!isValidEmail==true) {
                    Toast.makeText(getApplicationContext(), "Yanlış Email Formatı!!!", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    // Save the Datas in Database
                   insert();
                }
                break;

            default:
                break;
        }
    }
    /******************
     *  Insert method for inserting datas to database
     */
   private void insert() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user_name",user_name));
        nameValuePairs.add(new BasicNameValuePair("name",name));
        nameValuePairs.add(new BasicNameValuePair("surname",surname));
        nameValuePairs.add(new BasicNameValuePair("email",email));
        nameValuePairs.add(new BasicNameValuePair("password",password));

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://176.58.88.85/~murat/insert.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
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
            Log.e("KontrolÖnce", result);
            Log.e("pass 2", "connection success ");
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }

        try
        {
            JSONObject json_data = new JSONObject(result);
            code=json_data.getInt("code");
            Log.e("KontrolÖnce", (String.valueOf(code)));
            /******************
             *  Checked record is inserted or not
             */
            if(code==1)
            {
                Toast.makeText(getBaseContext(), "kayıt başarıyla eklendi.",
                        Toast.LENGTH_SHORT).show();
                Intent intentOpening = new Intent(getApplicationContext(),
                        ActivityFeatures.class);
                startActivity(intentOpening);
            }
            /******************"
             *  Chech userName is exist or not
             */
            else if(code==2){
                Toast.makeText(getBaseContext(), user_name+" isimli kullanıcı mevcut!!!",
                        Toast.LENGTH_SHORT).show();
            }
            /******************
             *  Check emailAdress is exist or not
             */
            else if(code==3){
                Toast.makeText(getBaseContext(), "Mevcut Email Adresi!!!",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "Sorry, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Log.e("Fail 3", e.toString());
        }
       finally {
            Log.e("Kontrol", (String.valueOf(code)));
        }
    }
   public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
}
