package ui.activityImpl;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat; // Thay thế android.support.v4.content.ContextCompat
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import ui.MainActivity;

public class ResultTestExamActivityImpl extends AppCompatActivity {

    TextView txtDiem, txtKQ;
    Button btnXemLaiDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_test_activity);

        // Thiết lập ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Kết quả thi");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.toolbar2)));
        }

        txtKQ = findViewById(R.id.txtKQ);
        txtDiem = findViewById(R.id.txtDiem);
        btnXemLaiDe = findViewById(R.id.btnXemLaiDe);

        Intent intent = getIntent();
        int diem = intent.getIntExtra("diem", 0);
        txtDiem.setText(String.valueOf(diem)); // Chuyển đổi điểm thành chuỗi

        if (diem >= 16) {
            txtKQ.setText("ĐẠT");
            txtKQ.setTextColor(getResources().getColor(R.color.green));
        } else {
            txtKQ.setText("KHÔNG ĐẠT");
            txtKQ.setTextColor(getResources().getColor(R.color.red));
        }

        btnXemLaiDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish(); // Đảm bảo kết thúc activity hiện tại
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); // Kết thúc activity hiện tại khi quay lại
            return true; // Trả về true để báo hiệu rằng sự kiện đã được xử lý
        }
        return super.onOptionsItemSelected(item);
    }
}
