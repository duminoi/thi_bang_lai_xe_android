package ui.activityImpl;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import adapter.SectionsPageAdapter;
import model.ExamPrinciple;
import presenter.ExamPrinciplePresenter;
import presenterImpl.ExamPrinciplePresenterImpl;
import ui.activity.ExamPrincipleActivity;
import ui.fragmentImpl.PrincipleTab1FragmentImpl;
import ui.fragmentImpl.PrincipleTab2FragmentImpl;

public class ExamPrincipleActivityImpl extends AppCompatActivity implements ExamPrincipleActivity {

    private ViewPager mViewPager;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ExamPrinciplePresenter examPrinciplePresenter;
    private List<ExamPrinciple> examPrinciples;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_principle_activity);

        examPrinciplePresenter = new ExamPrinciplePresenterImpl(ExamPrincipleActivityImpl.this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Giới thiệu thi thực hành");
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.toolbar2)));
        }

        // Set up the ViewPager with the sections adapter.
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);

        TabLayout tabLayout = findViewById(R.id.principleTab);
        tabLayout.setupWithViewPager(mViewPager);

        examPrinciplePresenter.getListExamPrinciples();
    }

    @Override
    public void setListExamPrinciples(List<ExamPrinciple> examPrinciples) {
        this.examPrinciples = examPrinciples;
        mSectionsPageAdapter.addFragment(PrincipleTab1FragmentImpl.getInstance(examPrinciples.get(1)), "Luật thi");
        mSectionsPageAdapter.addFragment(PrincipleTab2FragmentImpl.getInstance(examPrinciples.get(0)), "Kinh nghiệm thi");

        mViewPager.setAdapter(mSectionsPageAdapter);
        mSectionsPageAdapter.notifyDataSetChanged();
    }
}
