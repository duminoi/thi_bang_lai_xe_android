package ui.activityImpl;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Cập nhật từ android.support.v7.widget.Toolbar
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;

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

public class LyThuyetActivityImpl extends AppCompatActivity implements BaseQuestionActivity {

    private ViewPager viewPager;
    private List<Question> questions;
    private QuestionPresenter questionPresenter;
    private CustomPagerAdapter pagerAdapter;

    private static final String task = "ly_thuyet";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_thuyet_activity);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ôn câu lý thuyết");

        // Sử dụng setBackgroundDrawable() để tương thích với API level 15
        toolbar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.toolbar2)));

        viewPager = findViewById(R.id.vpLyThuyet);
        questionPresenter = new QuestionPresenterImpl(LyThuyetActivityImpl.this);
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        questionPresenter.getListQuestion(task, "");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setListQuestion(List<Question> questions) {
        this.questions = questions;
        Collections.shuffle(questions);
        for (int i = 0; i < questions.size(); i++) {
            int maCauHoi = questions.get(i).getId();
            String title = "Câu " + maCauHoi;
            pagerAdapter.addFragment(QuestionFragmentImpl.getInstance(i + 1, questions.get(i), "False", task), title);
        }
        pagerAdapter.notifyDataSetChanged();
    }
}
