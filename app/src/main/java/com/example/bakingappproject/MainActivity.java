package com.example.bakingappproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bakingappproject.DataModels.BakingReceipeDataModel;
import com.example.bakingappproject.DataModels.ReceipeAdapterClickListener;
import com.example.bakingappproject.DataModels.StepsDataModel;
import com.example.bakingappproject.Fragments.ReceipeDetailsFragment;
import com.example.bakingappproject.Fragments.ReceipeNameFragment;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements ReceipeAdapterClickListener {

    private static final String TAG = "TAGG";


    ReceipeNameFragment receipeNameFragment = new ReceipeNameFragment();

    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction()
                .add(R.id.container_for_fragments, receipeNameFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onBackPressed();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        return false;

    }


    @Override
    public void onItemClicks(BakingReceipeDataModel bakingReceipeDataModel) {

        findViewById(R.id.select_recipe_tv).setVisibility(View.GONE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe_data", bakingReceipeDataModel);
        ReceipeDetailsFragment receipeDetailsFragment = new ReceipeDetailsFragment();
        receipeDetailsFragment.setArguments(bundle);


        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (!fragmentManager.getFragments().contains(receipeDetailsFragment)) {

                fragmentManager.beginTransaction()
                        .remove(receipeDetailsFragment)
                        .commit();
            }

            fragmentManager.beginTransaction().replace(R.id.container_for_fragment_details, receipeDetailsFragment).commit();

        } else {
            // In portrait
            fragmentManager.beginTransaction()
                    .replace(R.id.container_for_fragments, receipeDetailsFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }




    }

    @Override
    public void onStepItemClickListener(StepsDataModel stepsDataModel) {

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            setTitle("Baking App");
        } else {
            super.onBackPressed();
        }

    }


}