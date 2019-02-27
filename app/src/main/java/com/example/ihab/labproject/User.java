package com.example.ihab.labproject;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link User.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link User#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User extends Fragment {
    public ImageView profilePic;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public User() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User.
     */
    // TODO: Rename and change types and number of parameters
    public static User newInstance(String param1, String param2) {
        User fragment = new User();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(requestCode==100) {
                if(resultCode == getActivity().RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        System.out.println("OKKKKKKK");
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData",
                                Context.MODE_PRIVATE);
                        final String name = sharedPreferences.getString("loggedIn","");
                          saveToInternalStorage(bitmap,name);
                        loadImageFromStorage(name);
                      //  profilePic.setImageBitmap(bitmap);
                    }
                    catch(Exception e){
                    }

                }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String fileName){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,fileName + ".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        SharedPreferences.Editor editor = getActivity().getSharedPreferences("pictures", getActivity().MODE_PRIVATE).edit();
//        editor.putString(LoginActivity.currEmail, directory.getAbsolutePath());
//        editor.apply();
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String fileName)
    {
        try {
            //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            ContextWrapper cw = new ContextWrapper(getContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f=new File(directory, fileName + ".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            profilePic.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(getActivity(),"No Profile Pic", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        final DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        Cursor cursor = dataBaseHelper.getUserByEmail(name);
        cursor.moveToNext();
        final EditText firstName = view.findViewById(R.id.firstNameUserFrag);
        final EditText lastName = view.findViewById(R.id.lastNameUserFrag);
        final EditText number = view.findViewById(R.id.numberUserFrag);
        final EditText password = view.findViewById(R.id.passwordUserFrag);
        profilePic = view.findViewById(R.id.profilePicView);
        loadImageFromStorage(name);
        firstName.setText(cursor.getString(3));
        lastName.setText(cursor.getString(4));
        number.setText(cursor.getString(8));

        System.out.println(firstName.getText().toString() + " <<< ");
        Button button = view.findViewById(R.id.buttonUserEdit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String firstNameString = firstName.getText().toString();
                 String lastNameString = lastName.getText().toString();
                 String numberString = number.getText().toString();
                String passwordString = password.getText().toString();
                SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("FIRSTNAME",firstNameString);
                cv.put("LASTNAME",lastNameString);
                cv.put("PHONE",numberString);
                String errorText = "";
                if(!password.getText().toString().equals("")){
                    cv.put("PASSWORD",getHashedPassword(passwordString));
                    if(!isValidPassWord(passwordString))errorText+="Invalid Password \n";
                }

                if(!isValidName(firstNameString))errorText+="Invalid First Name \n";
                if(!isValidName(lastNameString))errorText+="Invalid Last Name \n";
                if(!isValidPhone(numberString))errorText+="Invalid Phone Number \n";
                if(errorText.length()!=0){
                    Toast.makeText(getActivity().getApplicationContext(), errorText,Toast.LENGTH_LONG).show();
                }
                else{
                    sqLiteDatabase.update("USER",cv,"EMAIL = ?",new String[]{name});
                    Toast.makeText(getActivity().getApplicationContext(), "Updated Succesfully",Toast.LENGTH_LONG).show();
                }

//                sqLiteDatabase.execSQL("UPDATE USER " +
//                        "SET FIRSTNAME ='"+firstNameString+"',LASTNAME = '"+lastNameString+"', NUMBER = '"+numberString+"'" +
//                        " WHERE EMAIL = '"+name+"'");
            }
        });


        ImageButton edit = (ImageButton)view.findViewById(R.id.editProfilePic);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



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
