package com.example.ihab.labproject;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAdmin extends Fragment {
    EditText firstname,lastname,phone,email,pw,confirmPw;
    TextView area;
    Spinner gender,country,city;
    Button signUp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAdmin newInstance(String param1, String param2) {
        AddAdmin fragment = new AddAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_add_admin, container, false);
        // Inflate the layout for this fragment
        area = (TextView)view.findViewById(R.id.areaCodeAdmin);
        firstname = (EditText)view.findViewById(R.id.firstNameSignupAdmin);
        lastname = (EditText)view.findViewById(R.id.lastNameSignupAdmin);
        phone = (EditText)view.findViewById(R.id.phoneSignupAdmin);
        email = (EditText)view.findViewById(R.id.emailsignupAdmin);
        pw = (EditText)view.findViewById(R.id.passwordsignupAdmin);
        confirmPw = (EditText)view.findViewById(R.id.confirmpasssignupAdmin);
        gender = (Spinner)view.findViewById(R.id.genderSpinnerAdmin);
        country = (Spinner)view.findViewById(R.id.countryspinnerAdmin);
        city = (Spinner)view.findViewById(R.id.citySpinnerAdmin);
        signUp=  (Button) view.findViewById(R.id.signUpButtonAdmin);
        String[] options = { "Male", "Female"};
        ArrayAdapter objGenderArr = new ArrayAdapter (context,android.R.layout.simple_spinner_item, options);
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
        ArrayAdapter coutriesAdapter = new ArrayAdapter (context,android.R.layout.simple_spinner_item, countries);
        ArrayAdapter jordanAdapter = new ArrayAdapter (context,android.R.layout.simple_spinner_item, countryToCityMap.get("Jordan"));
        ArrayAdapter stateAdapter = new ArrayAdapter (context,android.R.layout.simple_spinner_item, countryToCityMap.get("USA"));
        ArrayAdapter italyAdapter = new ArrayAdapter (context,android.R.layout.simple_spinner_item, countryToCityMap.get("Italy"));
        ArrayAdapter palestinianAdapter = new ArrayAdapter (context,android.R.layout.simple_spinner_item, countryToCityMap.get("Palestine"));
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
                    Toast.makeText(context, errorText,Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,"Admin Added Successfully",Toast.LENGTH_LONG).show();
                    Profile user=new Profile();
                    user.setAdmin(false);
                    user.setCity(cityString);
                    user.setCountry(countryString);
                    user.setEmail(emailString);
                    user.setFirstName(fn);
                    user.setLastName(ln);
                    user.setGender(genderString);
                    user.setPassword(getHashedPassword(passwordString));
                    user.setPhone(phoneNum);
                    user.setAdmin(true);
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                    dataBaseHelper.insertUser(user);

                    Cursor cursor = dataBaseHelper.getAllUsers();
                    while (cursor.moveToNext()) {
                        System.out.println(cursor.getString(0)+' '+cursor.getString(1)+' '+
                                cursor.getString(2)+' '+cursor.getString(3)+' '+cursor.getString(4)+' '+
                                cursor.getString(5)+' '+cursor.getString(6)+' '+cursor.getString(7)+' '+
                                cursor.getString(8));
                    }
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
