package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.bignerdranch.myapplication.databinding.ActivityNavigationBarBinding;

public class NavigationBarActivity extends BaseActivity {

    private ActivityNavigationBarBinding binding;

    public static Intent newIntent(Context packageContext) {
        return  new Intent(packageContext, NavigationBarActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation_bar);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}