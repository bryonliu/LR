package com.top.bryon.lr.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.top.bryon.lr.R;
import com.top.bryon.sweatalertdialog.widget.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SweetAlertDialog(this).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_bar, menu);
        MenuItem settingMenu = menu.findItem(R.id.action_settings);
        MenuItem quitMenu = menu.findItem(R.id.action_quit);
        settingMenu.setVisible(false);
        quitMenu.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }
}
