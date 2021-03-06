package net.qiujuer.italker.push.frags.bankcard;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;
import net.qiujuer.italker.factory.model.api.bank.BankItemModel;
import net.qiujuer.italker.factory.presenter.bank.BankContract;
import net.qiujuer.italker.factory.presenter.bank.BankPresenter;
import net.qiujuer.italker.push.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankcardFragment extends PresenterFragment<BankContract.Presenter> implements BankContract.View {
    @BindView(R.id.appbar)
    View mLayAppbar;
    private Adapter mAdapter = new Adapter();

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @OnClick(R.id.add_bankcard)
    void onAddBankcardClick(View view) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, new BankcardProfileFragment(), BankcardProfileFragment.class.getName()).addToBackStack(null).commit();
    }


    public BankcardFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_bankcard;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadList();
    }

    @Override
    public void listLoaded(List<BankItemModel> model) {
        mAdapter.replace(model);
    }

    @Override
    protected BankContract.Presenter initPresenter() {
        return new BankPresenter(this);
    }

    class Adapter extends RecyclerAdapter<BankItemModel> {

        @Override
        protected int getItemViewType(int position, BankItemModel bankItemModel) {
            return R.layout.cell_bankcard;
        }

        @Override
        protected ViewHolder<BankItemModel> onCreateViewHolder(View root, int viewType) {
            return new BankcardFragment.ViewHolder(root);
        }
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<BankItemModel> {

        @BindView(R.id.cardType)
        TextView mCardType;
        @BindView(R.id.bankName)
        TextView mBankName;
        @BindView(R.id.cardNumber)
        TextView mCardNumber;
        @BindView(R.id.ll_background)
        LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(BankItemModel bankItemModel) {
            mCardType.setText(bankItemModel.getCardType());
            mBankName.setText(bankItemModel.getBankName());
            mCardNumber.setText(bankItemModel.getCardNumber());
            String id = bankItemModel.getId();
            int i = Integer.parseInt(id);
            int color;
            if ((i % 2) == 0) {
                color = getResources().getColor(R.color.blue_200);

            } else {
                color = getResources().getColor(R.color.red_200);
            }
            mLinearLayout.setBackgroundColor(color);

        }
    }

    @OnClick(R.id.ib_back)
    public void onBackClick(View view) {
        getActivity().finish();
    }
}
