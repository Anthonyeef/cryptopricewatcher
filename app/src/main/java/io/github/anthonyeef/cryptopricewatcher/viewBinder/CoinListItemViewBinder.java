package io.github.anthonyeef.cryptopricewatcher.viewBinder;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.anthonyeef.cryptopricewatcher.R;
import io.github.anthonyeef.cryptopricewatcher.data.entity.CoinEntity;
import io.github.anthonyeef.cryptopricewatcher.databinding.ViewCoinItemBinding;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by wuyifen on 24/03/2018.
 */

public class CoinListItemViewBinder extends ItemViewBinder<CoinEntity, CoinListItemViewBinder.CoinListItemViewHolder> {

    static class CoinListItemViewHolder extends RecyclerView.ViewHolder {
        private ViewCoinItemBinding coinItemBinding;
        private TextView index;

        CoinListItemViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());

            coinItemBinding = (ViewCoinItemBinding) binding;
            index = coinItemBinding.getRoot().findViewById(R.id.index);
        }

        public void bindCoinData(CoinEntity item) {
            coinItemBinding.setCoin(item);
            index.setText("#" + String.valueOf(getAdapterPosition() + 1));
            coinItemBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    protected CoinListItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new CoinListItemViewHolder(ViewCoinItemBinding.inflate(inflater, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull CoinListItemViewHolder holder, @NonNull CoinEntity item) {
        holder.bindCoinData(item);
    }
}
