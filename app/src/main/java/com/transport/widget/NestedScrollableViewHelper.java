package com.transport.widget;

import android.view.View;

import androidx.core.widget.NestedScrollView;

import com.sothree.slidinguppanel.ScrollableViewHelper;

/**
 * Created by SAM on 15/3/20.
 */
public class NestedScrollableViewHelper extends ScrollableViewHelper {
    @Override
    public int getScrollableViewScrollPosition(View scrollableView, boolean isSlidingUp) {

        if (scrollableView instanceof NestedScrollView) {
            if(isSlidingUp){
                return scrollableView.getScrollY();
            } else {
                NestedScrollView nsv = ((NestedScrollView) scrollableView);
                View child = nsv.getChildAt(0);
                return (child.getBottom() - (nsv.getHeight() + nsv.getScrollY()));
            }
        } else {
            return 0;
        }
//        return super.getScrollableViewScrollPosition(scrollableView, isSlidingUp);
    }


}