package com.andlp.scroll.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/5/13 15:45
 * @ email：gdutxiaoxu@163.com
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected Context mContext;
    protected final int mItemLayoutId;
    protected List<T> mDatas;
    protected BaseRecyclerAdapter.OnItemClickListener mOnItemClickListener;
    protected BaseRecyclerAdapter.OnLongItemClickListener mOnLongItemClickListener;

    public boolean isEmpty() {
        return mDatas == null || mDatas.size() == 0;
    }

    public BaseRecyclerAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = new ArrayList<>();
    }

    public BaseRecyclerAdapter(Context context, int itemLayoutId, List<T> datas) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = datas;
    }

    @Override public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, mItemLayoutId, null);
        BaseRecyclerHolder holder = new BaseRecyclerHolder(view);
        setListener(parent, holder, viewType);
        return holder;
    }
    @Override public void onBindViewHolder(final BaseRecyclerHolder holder, int position) {
        BaseRecyclerHolder baseHolder = (BaseRecyclerHolder) holder;
        convert(baseHolder, (T) mDatas.get(position), position);
    }

    //holder 未自定义viewholder对象  可以getview获取控件  item=对应的数据
    public abstract void convert(BaseRecyclerHolder holder, T item, int position);

    @Override public int getItemCount() {
        return isEmpty() ? 0 : mDatas.size();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    public void setClickListener(BaseRecyclerHolder holder, int id, View.OnClickListener onClickListener) {
        holder.getView(id).setOnClickListener(onClickListener);
    }

    protected void setListener(final ViewGroup parent, final BaseRecyclerHolder viewHolder, int  viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();  //这个方法是获取在holder里面真正的位置，而不是对应list的位置
                    T t = mDatas.get(position);
                    mOnItemClickListener.onClick(v, viewHolder,  position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnLongItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });

    }


    public interface OnItemClickListener {
        void onClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public interface OnLongItemClickListener {
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    //点击事件
    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //长按
    public void setonLongItemClickListener(BaseRecyclerAdapter.OnLongItemClickListener onLongItemClickListener) {
        this.mOnLongItemClickListener = onLongItemClickListener;
    }


}

