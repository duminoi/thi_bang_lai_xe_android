package ui.activityImpl;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable; // Cập nhật từ android.support.annotation.Nullable
import androidx.core.content.ContextCompat; // Cập nhật từ android.support.v4.content.ContextCompat
import androidx.appcompat.app.ActionBar; // Cập nhật từ android.support.v7.app.ActionBar
import androidx.appcompat.app.AppCompatActivity; // Cập nhật từ android.support.v7.app.AppCompatActivity

import android.widget.ListView;

import com.example.myapplication.R;

import java.util.List;

import adapter.FinesAdapter;
import model.Fines;
import presenter.FinesPresenter;
import presenterImpl.FinesPresenterImpl;
import ui.activity.FinesActivity;

public class FinesActivityImpl extends AppCompatActivity implements FinesActivity {

    private FinesAdapter finesAdapter;
    private ListView listFines;

    private FinesPresenter finesPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fines_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Các mức xử phạt");
        actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(),R.color.toolbar2)));

        finesPresenter = new FinesPresenterImpl(FinesActivityImpl.this);
        finesPresenter.getListFines();

    }

    @Override
    public void setListFines(List<Fines> fines) {
        finesAdapter = new FinesAdapter(this, R.layout.fines_item, fines);
        listFines = findViewById(R.id.listFines);
        listFines.setAdapter(finesAdapter);
        finesAdapter.notifyDataSetChanged();
    }
}
