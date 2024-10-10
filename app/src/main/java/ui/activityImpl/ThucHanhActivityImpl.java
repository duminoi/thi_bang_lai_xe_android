package ui.activityImpl;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.myapplication.R;

public class ThucHanhActivityImpl extends AppCompatActivity {

    TextView txtKN1, txtKN2, txtKN3, txtKN4, txtKN5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thuc_hanh_activity);

        // Set up ActionBar
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Giới thiệu thi thực hành");
            // Thay đổi màu nền ActionBar
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.toolbar2)));
        }

        loadTabs();
    }

    public void loadTabs() {
        // Lấy Tabhost id ra trước
        final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
        // Gọi lệnh setup
        tab.setup();
        TabHost.TabSpec spec;
        // Tạo tab1
        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Luật thi");
        tab.addTab(spec);
        // Tạo tab2
        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Kinh nghiệm thi");
        tab.addTab(spec);
        // Thiết lập tab mặc định được chọn ban đầu là tab 0
        tab.setCurrentTab(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
