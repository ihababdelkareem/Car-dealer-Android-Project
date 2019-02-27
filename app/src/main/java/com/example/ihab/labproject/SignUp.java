package com.example.ihab.labproject;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ihab.labproject.models.Profile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Pattern;
public class SignUp extends AppCompatActivity {
    EditText firstname,lastname,phone,email,pw,confirmPw;
    TextView area;
    Spinner gender,country,city;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        area = (TextView)findViewById(R.id.areaCode);
        firstname = (EditText)findViewById(R.id.firstNameSignup);
        lastname = (EditText)findViewById(R.id.lastNameSignup);
        phone = (EditText)findViewById(R.id.phoneSignup);
        email = (EditText)findViewById(R.id.emailsignup);
        pw = (EditText)findViewById(R.id.passwordsignup);
        confirmPw = (EditText)findViewById(R.id.confirmpasssignup);
        gender = (Spinner)findViewById(R.id.genderSpinner);
        country = (Spinner)findViewById(R.id.countryspinner);
        city = (Spinner)findViewById(R.id.citySpinner);
        signUp=  (Button) findViewById(R.id.signUpButton);
        String[] options = { "Male", "Female"};
        ArrayAdapter objGenderArr = new ArrayAdapter (this,android.R.layout.simple_spinner_item, options);
        gender.setAdapter(objGenderArr);
        gender.setPrompt("Gender");
        final String[] countries = {"Palestine","Jordan","USA","Italy"};
        final HashMap<String,String[]> countryToCityMap = new HashMap<>();
        final HashMap<String , ArrayAdapter> countryToAdapterMap = new HashMap<>();
        final HashMap<String ,String> countryToAreaCode = new HashMap<>();
        countryToAreaCode.put("Palestine","+970");
        countryToAreaCode.put("Jordan","+962");
        countryToAreaCode.put("USA","+1");
        countryToAreaCode.put("Italy","+39");
        countryToCityMap.put("Palestine",new String[]{"Ramallah","Jericho","Jenin","Nablus"});
        countryToCityMap.put("Jordan",new String[]{"Amman","Balqaa","Jarash","Zarqa"});
        countryToCityMap.put("USA",new String[]{"Detroit","Los Angeles","NYC","Chicago"});
        countryToCityMap.put("Italy",new String[]{"Milan","Rome","Florence","Pisa"});
        ArrayAdapter coutriesAdapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item, countries);
        ArrayAdapter jordanAdapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item, countryToCityMap.get("Jordan"));
        ArrayAdapter stateAdapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item, countryToCityMap.get("USA"));
        ArrayAdapter italyAdapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item, countryToCityMap.get("Italy"));
        ArrayAdapter palestinianAdapter = new ArrayAdapter (this,android.R.layout.simple_spinner_item, countryToCityMap.get("Palestine"));
        countryToAdapterMap.put("Palestine",palestinianAdapter);
        countryToAdapterMap.put("Jordan",jordanAdapter);
        countryToAdapterMap.put("USA",stateAdapter);
        countryToAdapterMap.put("Italy",italyAdapter);
        country.setAdapter(coutriesAdapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String countryValue = country.getSelectedItem().toString();
                city.setAdapter(countryToAdapterMap.get(countryValue));
                area.setText(countryToAreaCode.get(countryValue));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String  fn = firstname.getText().toString();
                String  ln = lastname.getText().toString();
                String emailString = email.getText().toString();
                String genderString = gender.getSelectedItem().toString();
                String countryString = country.getSelectedItem().toString();
                String cityString = city.getSelectedItem().toString();
                String passwordString = pw.getText().toString();
                String confirmed = confirmPw.getText().toString();
                String phoneNum = area.getText().toString()+phone.getText().toString();

                String errorText = "";
                if(!isValidName(fn))errorText+="Invalid First Name \n";
                if(!isValidName(ln))errorText+="Invalid Last Name \n";
                if(!isValidPassWord(passwordString))errorText+="Invalid Password \n";
                if(!isValid(emailString))errorText+="Invalid E-Mail \n";
                if(!confirmed.equals(passwordString))errorText+="Passwords Don't Match \n";
                if(!isValidPhone(phoneNum))errorText+="Invalid Phone Number \n";
                if(errorText.length()!=0){
                    Toast.makeText(SignUp.this, errorText,Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(SignUp.this,"Registered Succesfully, Navigating Back to Login",Toast.LENGTH_LONG).show();
                    Profile user = new Profile();
                    user.setAdmin(false);
                    user.setCity(cityString);
                    user.setCountry(countryString);
                    user.setEmail(emailString);
                    user.setFirstName(fn);
                    user.setLastName(ln);
                    user.setGender(genderString);
                    user.setPassword(getHashedPassword(passwordString));
                    user.setPhone(phoneNum);
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUp.this);
                    dataBaseHelper.insertUser(user);


                    Cursor cursor = dataBaseHelper.getAllUsers();
            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(0)+' '+cursor.getString(1)+' '+
                        cursor.getString(2)+' '+cursor.getString(3)+' '+cursor.getString(4)+' '+
                        cursor.getString(5)+' '+cursor.getString(6)+' '+cursor.getString(7)+' '+
                        cursor.getString(8));
            }
                    Intent myIntent=new Intent(SignUp.this,
                            LoginActivity.class);
                    SignUp.this.startActivity(myIntent);
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    boolean isValidPassWord(String password){
        boolean hasAlpha=false;
        boolean hasSpecial = false;
        boolean hasNumeric = false;
       for(int i =0;i<password.length();i++){
           if(Character.isAlphabetic(password.charAt(i)))hasAlpha=true;
           else if(Character.isDigit(password.charAt(i)))hasNumeric=true;
           else hasSpecial = true;
       }
       return hasAlpha&&hasNumeric&&hasSpecial&&password.length()>=5;
    }
    boolean isValidName(String name){
        return name.length()>=3;
    }


    public  String getHashedPassword(String pw){
        String passwordToHash = pw;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone){
        for(int i =1;i<phone.length();i++){

            if(!Character.isDigit(phone.charAt(i))){
                System.out.println(">"+phone.charAt(i)+"<");
                return false;
            }
        }
        return phone.length()>10;
    }
}
