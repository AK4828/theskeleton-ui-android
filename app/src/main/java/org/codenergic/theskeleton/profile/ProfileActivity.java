package org.codenergic.theskeleton.profile;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import org.codenergic.theskeleton.R;
import org.codenergic.theskeleton.base.BaseActivity;
import org.codenergic.theskeleton.base.BasePresenter;

/**
 * Created by diasa on 12/14/17.
 */
public class ProfileActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;

    @BindView(R.id.materialup_toolbar)
    Toolbar toolbar;
    @BindView(R.id.materialup_tabs)
    TabLayout tabLayout;
    @BindView(R.id.materialup_viewpager)
    ViewPager viewPager;
    @BindView(R.id.materialup_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.materialup_profile_image)
    FrameLayout profileImage;

    private int mMaxScrollSize;
    private boolean mIsAvatarShown = true;

    @Override
    public int getLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void setup() {
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());
        appBarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appBarLayout.getTotalScrollRange();
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public BasePresenter attachPresenter() {
        return null;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            profileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;
            profileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 3;

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            return new Fragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0 : return "Ulasan";
                case 1 : return "Cerita";
                case 2 : return "Karya Tulis";
                default: return "";
            }
        }
    }
}
