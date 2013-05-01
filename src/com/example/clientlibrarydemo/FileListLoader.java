package com.example.clientlibrarydemo;

import edu.umich.imlc.mydesk.test.common.GenericContract;
import edu.umich.imlc.mydesk.test.common.GenericContract.MetaDataColumns;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class FileListLoader implements LoaderCallbacks<Cursor>
{
  public static final int LOAD_ID = 0;
  
  public static final String[] loaderProjection = 
    { MetaDataColumns.ID, MetaDataColumns.NAME, MetaDataColumns.URI };
  
  public static final String[] mFromColumns = 
    { MetaDataColumns.NAME, MetaDataColumns.URI };
  
  public static final int[] mToFields = 
    { android.R.id.text1, android.R.id.text2 };
  
  private Context context;
  SimpleCursorAdapter mAdapter;
  
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  
  public FileListLoader(Context c)
  {
    context = c;
    this.mAdapter = new SimpleCursorAdapter
        (context, android.R.layout.two_line_list_item, null, mFromColumns, mToFields, 0);
  };
  
  public final SimpleCursorAdapter getAdapter()
  {
    return mAdapter;
  }
  // ----------------------------------------------------------------------------
  
  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args)
  {
    switch ( id )
    {
      case LOAD_ID:
        CursorLoader loader = new CursorLoader(context);
        loader.setUri(GenericContract.URI_FILES);

        loader.setProjection(loaderProjection);
        return loader;
      default:
        return null;
    }
  }

  // ----------------------------------------------------------------------------
  
  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
  {
    mAdapter.changeCursor(cursor);
  }

  // ----------------------------------------------------------------------------
  
  @Override
  public void onLoaderReset(Loader<Cursor> arg0)
  {
    mAdapter.changeCursor(null);
  }

}
