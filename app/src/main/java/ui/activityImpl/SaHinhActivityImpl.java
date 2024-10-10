package ui.activityImpl;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
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

public class SaHinhActivityImpl extends AppCompatActivity implements BaseQuestionActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Question> questions;
    private QuestionPresenter questionPresenter;
    private CustomPagerAdapter pagerAdapter;

    private static final String task = "sa_hinh";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sa_hinh_activity);

        questionPresenter = new QuestionPresenterImpl(SaHinhActivityImpl.this);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Ôn câu sa hình");
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.toolbar2)));
        }

        viewPager = findViewById(R.id.vpSaHinh);
        FragmentManager manager = getSupportFragmentManager();
        pagerAdapter = new CustomPagerAdapter(manager);
        viewPager.setAdapter(pagerAdapter);

        // Clear fragments before adding new ones
        fragments.clear();
        questionPresenter.getListQuestion(task, "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true; // Return true if the event is handled
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
