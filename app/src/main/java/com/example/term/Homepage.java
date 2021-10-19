package com.example.term;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Homepage extends AppCompatActivity {
    private MeowBottomNavigation bnv_Main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


            bnv_Main = findViewById(R.id.meow);
            bnv_Main.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
            bnv_Main.add(new MeowBottomNavigation.Model(2, R.drawable.ic_history));
            bnv_Main.add(new MeowBottomNavigation.Model(3, R.drawable.ic_report));
            bnv_Main.add(new MeowBottomNavigation.Model(4, R.drawable.ic_user));

            bnv_Main.show(1, true);
            replace(new HomeFragment());
            bnv_Main.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
                @Override
                public Unit invoke(MeowBottomNavigation.Model model) {
                    switch (model.getId()) {
                        case 1:
                            replace(new HomeFragment());
                            break;
                        case 2:
                            replace(new HistoryFragment());
                            break;
                        case 3:
                            replace(new ReportFragment());
                            break;
                        case 4:
                            replace(new ProfileFragment());
                            break;
                    }
                    return null;
                }
            });
        }



    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}