package it.sephiroth.android.library.bottomnavigation.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import it.sephiroth.android.library.bottomnavigation.MiscUtils;

@TargetApi (Build.VERSION_CODES.KITKAT_WATCH)
public class MainActivityTablet extends BaseActivity implements BottomNavigation.OnMenuItemSelectionListener {
    private static final String TAG = MainActivityTablet.class.getSimpleName();
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setupUI();
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_main_tablet;
    }

    protected void setupUI() {
        final int statusbarHeight = getStatusBarHeight();
        final boolean translucentStatus = hasTranslucentStatusBar();

        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton
            .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction(
                                "Action",
                                null
                            ).show();
                    }
                }
            );

        final CoordinatorLayout mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.CoordinatorLayout01);

        if (translucentStatus) {
            MiscUtils.INSTANCE.log(Log.DEBUG, "hasTranslucentStatusBar");
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mCoordinatorLayout.getLayoutParams();
            params.topMargin = -statusbarHeight;

            params = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            params.topMargin = statusbarHeight;
        }

        if (null != getBottomNavigation()) {
            getBottomNavigation().setDefaultSelectedIndex(0);
        }

    }

    @Override
    public void onMenuItemSelect(final int itemId, final int position, final boolean fromUser) {
        MiscUtils.INSTANCE.log(Log.INFO, "onMenuItemSelect(" + itemId + ", " + position + ")");
        getBottomNavigation().getBadgeProvider().remove(itemId);
    }

    @Override
    public void onMenuItemReselect(@IdRes final int itemId, final int position, final boolean fromUser) {
        MiscUtils.INSTANCE.log(Log.INFO, "onMenuItemReselect(" + itemId + ", " + position + ")");

        final FragmentManager manager = getSupportFragmentManager();
        MainActivityFragment fragment = (MainActivityFragment) manager.findFragmentById(R.id.fragment);
        fragment.scrollToTop();

    }
}
