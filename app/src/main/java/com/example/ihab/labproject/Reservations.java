package com.example.ihab.labproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ihab.labproject.models.Car;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reservations.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reservations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservations extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Reservations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservations.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservations newInstance(String param1, String param2) {
        Reservations fragment = new Reservations();
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        final String name = sharedPreferences.getString("loggedIn","");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext().getApplicationContext());
        ArrayList<Pair<Car,String>> reservedByMe = new ArrayList<>();
        Cursor cursor = dataBaseHelper.getReservesByEmail(name);
        System.out.println(cursor.getCount());
        while (cursor.moveToNext()){
            Pair<Car,String> stringPair = new Pair<>(Car.allCars.get(Integer.parseInt(cursor.getString(1))),cursor.getString(2));
            reservedByMe.add(stringPair);
        }
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerRes);
        AdapterRes resAdapter = new AdapterRes(getActivity().getApplicationContext(), reservedByMe);
        recyclerView.setAdapter(resAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
        // Inflate the layout for this fragment
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
