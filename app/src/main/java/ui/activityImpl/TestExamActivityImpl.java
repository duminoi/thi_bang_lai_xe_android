package ui.activityImpl;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.CustomPagerAdapter;
import model.Question;
import presenter.QuestionPresenter;
import presenterImpl.QuestionPresenterImpl;
import ui.activity.BaseQuestionActivity;
import ui.fragmentImpl.QuestionFragmentImpl;

public class TestExamActivityImpl extends AppCompatActivity implements BaseQuestionActivity {

    private ViewPager viewPager;
    private List<QuestionFragmentImpl> fragments = new ArrayList<>();
    private List<Question> questions;
    private QuestionPresenter questionPresenter;
    private CustomPagerAdapter pagerAdapter;
    private TextView timerText;

    private static final String task = "listCauHoi_de";
    private boolean[] dapan = new boolean[20];
    private int diem = 0;
    private int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_exam_activity);

        questionPresenter = new QuestionPresenterImpl(TestExamActivityImpl.this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Làm thử đề");
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.toolbar2)));
        }

        viewPager = findViewById(R.id.vp_ThiThu);
        FragmentManager manager = getSupportFragmentManager();
        pagerAdapter = new CustomPagerAdapter(manager);
        viewPager.setAdapter(pagerAdapter);

        Intent intent = getIntent();
        String idDe = intent.getStringExtra("topicId");
        questionPresenter.getListQuestion(task, idDe);
        fragments.clear();

        addEvents();
    }

    @Override
    public void setListQuestion(List<Question> questions) {
        this.questions = questions;
        Collections.shuffle(questions);
        for (int i = 0; i < questions.size(); i++) {
            int maCauHoi = questions.get(i).getId();
            String title = "Câu " + maCauHoi;
            pagerAdapter.addFragment(QuestionFragmentImpl.getInstance(i + 1, questions.get(i), "True", task), title);
        }
        pagerAdapter.notifyDataSetChanged();
        fragments = pagerAdapter.getFragments();
    }

    private void chamDiem() {
        Log.d("currentPage", String.valueOf(currentPage));
        for (int i = 0; i <= currentPage; i++) {
            if (fragments.get(i).traLoiDung()) {
                diem++;
            }
        }
    }

    private void addEvents() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("position", String.valueOf(position));
                if (position > currentPage) {
                    currentPage = position;
                }
                fragments.get(position).xuLyLuuAnswerDuocChon();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_exam_menu, menu);
        MenuItem timerItem = menu.findItem(R.id.break_timer);
        timerText = (TextView) timerItem.getActionView(); // Updated line

        timerText.setPadding(10, 0, 10, 0);
        timerText.setTextSize(20);
        timerText.setTextColor(getResources().getColor(R.color.white));

        startTimer(900000, 1000);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.item_ketthuc) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết thúc bài thi");
        builder.setMessage("Bạn có muốn kết thúc bài thi không?");
        builder.setCancelable(false);
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chamDiem();
                Intent intent = new Intent(getApplicationContext(), ResultTestExamActivityImpl.class);
                intent.putExtra("diem", diem);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void startTimer(long duration, long interval) {
        CountDownTimer timer = new CountDownTimer(duration, interval) {
            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Điểm: " + diem + "/20", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTick(long millisecondsLeft) {
                int secondsLeft = (int) Math.round((millisecondsLeft / (double) 1000));
                timerText.setText(secondsToString(secondsLeft));
            }
        };

        timer.start();
    }

    private String secondsToString(int improperSeconds) {
        Time secConverter = new Time();
        secConverter.hour = 0;
        secConverter.minute = 0;
        secConverter.second = 0;

        secConverter.second = improperSeconds;
        secConverter.normalize(true);

        String hours = String.valueOf(secConverter.hour);
        String minutes = String.valueOf(secConverter.minute);
        String seconds = String.valueOf(secConverter.second);

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        if (hours.length() < 2) {
            hours = "0" + hours;
        }

        return hours + ":" + minutes + ":" + seconds;
    }
}
