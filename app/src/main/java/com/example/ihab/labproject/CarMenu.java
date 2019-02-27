package com.example.ihab.labproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ihab.labproject.models.Car;
//import android.widget.ListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarMenu.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarMenu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public CarMenu() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static CarMenu newInstance(String param1, String param2) {
        CarMenu fragment = new CarMenu();
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
//        LinearLayout containter = view.findViewById(R.id.scrollMenu);
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData",
//                Context.MODE_PRIVATE);
//        final String name = sharedPreferences.getString("loggedIn","");
////
////        LinearLayout test = new LinearLayout(getActivity().getApplicationContext());
////        test.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
////        Button buttonx = new Button(getActivity().getApplicationContext());
////        buttonx.setText("button");
////        Button buttony = new Button(getActivity().getApplicationContext());
////        buttony.setText("button");
////        TextView textView = new TextView(getActivity().getApplicationContext());
////        textView.setText("text view");
////        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
////        test.addView(textView,params);
////        test.addView(buttonx,params);
////        test.addView(buttony,params);
////        container.addView(test,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
////
//
//        System.out.println(containter);
//        final DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
//        final Cursor cursor = dataBaseHelper.getAllCars();
//        while (cursor.moveToNext()) {
//            //CardView cardView = new CardView(getActivity().getApplicationContext());
//            LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
//            layoutParamsButton.setMargins(10,0,10,0);
//          //  layoutParamsButton.weight=10;
//           // layoutParamsButton.width=10;
//            LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
//           layoutParamsText.setMargins(0,25,0,-20);
//
//            LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
//            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//            final String a = cursor.getString(0);
//            final String b = cursor.getString(1);
//            String toCard = (cursor.getString(1)+" "+cursor.getString(2));
//            if(toCard.length()>14)toCard=toCard.substring(0,10);
//
//            TextView tv = new TextView(getActivity().getApplicationContext());
//            tv.setTextColor(Color.WHITE);
//            tv.setTextSize(20);
//            tv.setText(toCard);
//           // tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
//
//            Button button = new Button(getActivity().getApplicationContext());
//            button.setText("Favorite");
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dataBaseHelper.insertFavorite(name,Integer.parseInt(a));
//                    Cursor cursor1 = dataBaseHelper.getAllFavorite();
//                    while (cursor1.moveToNext()){
//                        System.out.println(cursor1.getString(0)+" "+cursor1.getString(1));
//                    }
//                }
//            });
//
//            Button button2 = new Button(getActivity().getApplicationContext());
//            button2.setText("Reserve");
//            button2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dataBaseHelper.insertReserve(name,Integer.parseInt(a));
//                    Cursor cursor2 = dataBaseHelper.getAllReserves();
//                    while (cursor2.moveToNext()){
//                        System.out.println(cursor2.getString(0)+" "+cursor2.getString(1));
//                    }
//                }
//            });
//
//
//            //  button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                linearLayout.addView(tv,layoutParamsText);
//                linearLayout.addView(button,layoutParamsButton);
//                linearLayout.addView(button2,layoutParamsButton);
//              //  cardView.addView(linearLayout);
//                containter.addView(linearLayout);
//        }

        View view = inflater.inflate(R.layout.fragment_car_menu, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerCars);
        ListAdapter listAdapter = new ListAdapter(getActivity().getApplicationContext(),Car.allCars);
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
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
}
