package com.bayue.live.deqingpu.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bayue.live.deqingpu.R;

/**
 * Created by BaYue on 2017/7/18 0018.
 * email : 2651742485@qq.com
 */

public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;
    private int leftRight;
    private int topBottom;
    //color的传入方式是resouce.getcolor
    protected Drawable mDivider;
    public SimpleDividerDecoration(Context context,int leftRight, int topBottom, int mColor) {
        dividerPaint = new Paint();
//        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
//        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.dp_4);
        this.leftRight = leftRight;
        this.topBottom = topBottom;
        if (mColor != 0) {
            mDivider = new ColorDrawable(mColor);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.bottom = dividerHeight;
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //判断总的数量是否可以整除
        int totalCount = layoutManager.getItemCount();
        int surplusCount = totalCount % layoutManager.getSpanCount();
        int childPosition = parent.getChildAdapterPosition(view);
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {//竖直方向的
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要bottom
                outRect.bottom = topBottom;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.bottom = topBottom;
            }
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要右边
                outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
        } else {
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要右边
                outRect.right = leftRight;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.right = leftRight;
            }
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要下边
                outRect.bottom = topBottom;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int childCount = parent.getChildCount();
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//
//        for (int i = 0; i < childCount - 1; i++) {
//            View view = parent.getChildAt(i);
//            float top = view.getBottom();
//            float bottom = view.getBottom() + dividerHeight;
//            c.drawRect(left, top, right, bottom, dividerPaint);
//        }
        final GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final GridLayoutManager.SpanSizeLookup lookup = layoutManager.getSpanSizeLookup();

        if (mDivider == null || layoutManager.getChildCount() == 0) {
            return;
        }
        //判断总的数量是否可以整除
        int spanCount = layoutManager.getSpanCount();

        int left;
        int right;
        int top;
        int bottom;

        final int childCount = parent.getChildCount();
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {

            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                //得到它在总数里面的位置
                final int position = parent.getChildAdapterPosition(child);
                //获取它所占有的比重
                final int spanSize = lookup.getSpanSize(position);
                //获取每排的位置
                final int spanIndex = lookup.getSpanIndex(position, layoutManager.getSpanCount());
                //将带有颜色的分割线处于中间位置
                final float centerLeft = (layoutManager.getLeftDecorationWidth(child) + 1 - leftRight) / 2;
                final float centerTop = (layoutManager.getBottomDecorationHeight(child) + 1 - topBottom) / 2;
                //判断是否为第一排
                boolean isFirst = position + spanSize <= layoutManager.getSpanCount();
                //画上边的，第一排不需要上边的,只需要在最左边的那项的时候画一次就好
                if (!isFirst && spanIndex == 0) {
                    left = layoutManager.getLeftDecorationWidth(child);
                    right = parent.getWidth() - layoutManager.getLeftDecorationWidth(child);

                    top = (int) (child.getTop() - centerTop) - topBottom;
                    bottom = top + topBottom;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
                //最右边的一排不需要右边的
                boolean isRight = spanIndex + spanSize == spanCount;
                if (!isRight) {
                    //计算右边的
                    left = (int) (child.getRight() + centerLeft);
                    right = left + leftRight;
                    top = child.getTop();

                    if (position + spanSize - 1 >= spanCount) {
                        top -= centerTop;
                    }
                    bottom = (int) (child.getBottom() + centerTop);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                //得到它在总数里面的位置
                final int position = parent.getChildAdapterPosition(child);
                //获取它所占有的比重
                final int spanSize = lookup.getSpanSize(position);
                //获取每排的位置
                final int spanIndex = lookup.getSpanIndex(position, layoutManager.getSpanCount());
                //将带有颜色的分割线处于中间位置
                final float centerLeft = (layoutManager.getRightDecorationWidth(child) + 1 - leftRight) / 2;
                final float centerTop = (layoutManager.getTopDecorationHeight(child) + 1 - topBottom) / 2;
                //判断是否为第一列
                boolean isFirst = position + spanSize <= layoutManager.getSpanCount();
                //画左边的，第一排不需要左边的,只需要在最上边的那项的时候画一次就好
                if (!isFirst && spanIndex == 0) {
                    left = (int) (child.getLeft() - centerLeft) - leftRight;
                    right = left + leftRight;

                    top = layoutManager.getRightDecorationWidth(child);
                    bottom = parent.getHeight() - layoutManager.getTopDecorationHeight(child);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
                //最下的一排不需要下边的
                boolean isRight = spanIndex + spanSize == spanCount;
                if (!isRight) {
                    //计算右边的
                    left = child.getLeft();
                    if (position + spanSize - 1 >= spanCount) {
                        left -= centerLeft;
                    }
                    right = (int) (child.getRight() + centerTop);

                    top = (int) (child.getBottom() + centerLeft);
                    bottom = top + leftRight;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
    }
}
