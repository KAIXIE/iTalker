package net.qiujuer.italker.push.frags.setting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.contact.PersonalContract;
import net.qiujuer.italker.factory.presenter.contact.PersonalPresenter;
import net.qiujuer.italker.push.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends PresenterFragment<PersonalContract.Presenter> implements PersonalContract.View {
    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.portraitView)
    PortraitView mPortraitView;

    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_desc)
    TextView mDesc;

    @BindView(R.id.sex_rg)
    RadioGroup mSex;
    private User mUser;

    @OnClick(R.id.change_portrait)
    void onChangePortraitClick(View view) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, ChangePortraitFragment.newInstance(mUser), ChangePortraitFragment.class.getName()).addToBackStack(null).commit();
    }

    public EditProfileFragment() {
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Glide.with(this)
                .load(R.drawable.bg_login)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    public String getUserId() {
        return Account.getUserId();
    }

    @Override
    public void onLoadDone(User user) {
        mUser = user;
        mPortraitView.setup(Glide.with(this), user);
        mName.setText(user.getName());
        mDesc.setText(user.getDesc());
        boolean isMan = user.getSex() == 0 ? true : false;
        mSex.check(isMan ? R.id.radio_man : R.id.radio_woman);
    }

    @Override
    public void allowSayHello(boolean isAllow) {

    }

    @Override
    public void setFollowStatus(boolean isFollow) {

    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public boolean onBackPressed() {
        Application.showToast("onBackPressed");
        getActivity().getSupportFragmentManager().popBackStack();
        return true;
    }
}
