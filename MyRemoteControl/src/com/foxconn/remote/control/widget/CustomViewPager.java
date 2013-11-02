package com.foxconn.remote.control.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *  自定義ViewPager 取消了滑動功能
 * 
 * @author KrisLight
 *
 */
public class CustomViewPager extends ViewPager {

	private boolean enabled;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.enabled = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.enabled) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}

	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
    /**
     * Set current item and return whether the item changed
     * <p>
     * This method does not call {@link #setCurrentItem(int)} unless the item
     * parameter differs from the current item
     *
     * @param item
     * @return true if set, false if same
     */
    public boolean setItem(final int item) {
        final boolean changed = item != getCurrentItem();
        if (changed)
            setCurrentItem(item, false);
        return changed;
    }
    
    /**
     * Set current item, invoke the listener if changes, and return whether the
     * item changed
     * <p>
     * This method does not call {@link #setCurrentItem(int)} unless the item
     * parameter differs from the current item
     *
     * @param item
     * @param listener
     * @return true if set, false if same
     */
    public boolean setItem(final int item, final OnPageChangeListener listener) {
        final boolean changed = setItem(item);
        if (changed && listener != null)
            listener.onPageSelected(item);
        return changed;
    }
    
    /**
     * Schedule a call to {@link #setItem(int)} to occur on the UI-thread
     *
     * @param item
     * @param listener
     */
    public void scheduleSetItem(final int item,
            final OnPageChangeListener listener) {
        post(new Runnable() {
            @Override
            public void run() {
                setItem(item, listener);
            }
        });
    }

    /**
     * Schedule a call to {@link #setItem(int)} to occur on the UI-thread
     *
     * @param item
     */
    public void scheduleSetItem(final int item) {
        post(new Runnable() {
            @Override
            public void run() {
                setItem(item);
            }
        });
    }

}