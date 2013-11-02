package com.foxconn.remote.control.loader;

import java.util.List;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Loader
 * @author KrisLight
 *
 */
abstract public class AbstractListLoader extends AsyncTaskLoader<List<?>> {
  abstract protected List<?> buildList();
  List<?> lastList = null;

  public AbstractListLoader(Context context) {
    super(context);
  }
  
  @Override
  public List<?> loadInBackground() {
    return buildList();
  }

  @Override
  public void deliverResult(List<?> list) {
    if (isReset()) {
      return;
    }
    lastList = list;
    if (isStarted()) {
      super.deliverResult(list);
    }
  }

  @Override
  protected void onStartLoading() {
    if (lastList!=null) {
      deliverResult(lastList);
    }
    if (takeContentChanged() || lastList==null) {
      forceLoad();
    }
  }

  @Override
  protected void onStopLoading() {
    cancelLoad();
  }

  @Override
  public void onCanceled(List<?> list) {
    super.onCanceled(list);
    lastList = null;
  }

  @Override
  protected void onReset() {
    super.onReset();
    // Ensure the loader is stopped
    onStopLoading();
    lastList =null;
  }
}