package com.example.term;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;


public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout HpullToRefresh;
    List<ModelClass>userList;
    Adapter adapter;
    ProgressBar proinhis;
    IP i=new IP();

    SharedPreferences sharedPreferences;

    public static final String fileName="data";
    public static final String userId="userId";
    public static final String name="name";
    public static final String parent="parent";
    public static final String phone="phone";
    public static final String photoUrl="photoUrl";

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history, container, false);
        userList=new ArrayList<>();
        sharedPreferences=this.getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);

        HpullToRefresh=view.findViewById(R.id.HpullToRefresh);
        proinhis = view.findViewById(R.id.proinhis);

        new fetchData().execute();


        HpullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userList.clear();

                new fetchData().execute();

                HpullToRefresh.setRefreshing(false);
            }
        });


        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
    class fetchData extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            proinhis.setVisibility(VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String[] field = new String[1];
            field[0] = "id";
            //Creating array for data
            String[] data = new String[1];
            data[0] = sharedPreferences.getString(userId,"");
            PutData putData = new PutData("http://" + i.getIp() + "/token.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    //End ProgressBar (Set visibility to GONE)

                    String url = "http://" + i.getIp() + "/history.php?id=" + sharedPreferences.getString(userId,"");
                    FetchData fetchData = new FetchData(url);
                    if (fetchData.startFetch()) {
                        if (fetchData.onComplete()) {
                            String result1 = fetchData.getResult();
                            String[] str = result1.split("/");
                            for (int i = 0; i < str.length; i++) {
                                String[] sp = str[i].split(";");
                                userList.add(new ModelClass(sp[0], sp[1], sp[2], sp[3]));

                            }

                        }


                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            proinhis.setVisibility(View.GONE);
        }
    }




}