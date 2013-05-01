package com.example.clientlibrarydemo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.example.clientlibrarydemo.FileListLoader;
import com.example.clientlibrarydemo.R;
import com.example.clientlibrarydemo.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncStatusObserver;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import edu.umich.imlc.mydesk.test.common.GenericContract.MetaData;
import edu.umich.imlc.mydesk.test.common.exceptions.MyDeskException;

public class StorageDemo
{
  private static final String TAG = StorageDemo.class.getSimpleName();
  private static final String UTF_8 = "UTF-8";
  
  static final String [] fileNames = 
    {"Foo.txt", "Bar.txt", "Baz.txt", "Spam.txt", "Eggs.txt" };
  
  static final String [] fileTypes =
    {"Foo_Type", "Bar_Type", "Baz_Type", "Spam_Type", "Eggs_Type" };
  
  
  
  private Context context;
  
  private TextView textView = null;
  private ListView listView = null;
  FileListLoader fileListLoader = null;
  GenericStorageApi api = null;
  
  ArrayList<File> filePaths = new ArrayList<File>();
  TreeMap<String, MetaData> fileMetas = new TreeMap<String, MetaData>();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public class Observer implements SyncStatusObserver
  {
    @Override
    public void onStatusChanged(int code)
    {
      Util.printMethodName();
      Log.i("SyncStatus", String.format("Status: %d", code));
    }
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public StorageDemo(Activity activity)
  {
    Util.printMethodName();
    context = activity.getApplicationContext();
    fileListLoader = new FileListLoader(context);
    activity.getLoaderManager().initLoader
      (FileListLoader.LOAD_ID, null, fileListLoader);
    listView = (ListView)activity.findViewById(R.id.listView1);
    listView.setAdapter(fileListLoader.getAdapter());
    textView = (TextView)activity.findViewById(R.id.textView1);
    api = new GenericStorageApi(context);
    createSomeFiles(activity);
    Observer o = new Observer();
    ContentResolver.addStatusChangeListener(
        ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE & ContentResolver.SYNC_OBSERVER_TYPE_PENDING & ContentResolver.SYNC_OBSERVER_TYPE_SETTINGS,
        o); 
  }
  
  // ---------------------------------------------------------------------------
  
  public void createSomeFiles(Activity a)
  {
    Util.printMethodName();
    for(String s: fileNames)
    {
      File f = new File(a.getExternalFilesDir(null), s);
      filePaths.add(f);
      try
      {
        PrintWriter writer = new PrintWriter(f, UTF_8);
        writer.format("This is a file called: %s\n", s);
        writer.close();
      }
      catch( FileNotFoundException e )
      { Log.e(TAG, e.getMessage(), e); }
      catch( UnsupportedEncodingException e )
      { Log.e(TAG, e.getMessage(), e); }
      
    }    
  }
  
  // ---------------------------------------------------------------------------
  
  public void doLogin()
  {
    Util.printMethodName();
    //api.startLoginActivity();
    api.loginChooseAccount();
  }
  
  // ---------------------------------------------------------------------------
  
  public void sync()
  {
    Util.printMethodName();
    clear();
    api.requestSync();
  }
  
  // ---------------------------------------------------------------------------
  
  public void createFiles()
  {
    Util.printMethodName();
    clear();
    try
    {
      textView.append(String.format("Size: %d\n", filePaths.size()));
      for(int i = 0; i < filePaths.size(); i++)
      {
        MetaData m = api.createNewFile(fileNames[i], fileTypes[i], filePaths.get(i));
        if(m == null)
          textView.append("Null Meta Returned\n");
        else
          textView.append(printMeta(m));
      }
    }
    catch( MyDeskException e )
    { Log.e(TAG, e.getMessage(), e); }
  }
  
  // ---------------------------------------------------------------------------
  
  @SuppressLint("DefaultLocale")
  public void saveFiles()
  {
    Util.printMethodName();
    clear();
    int i = 0;
    for(File f: filePaths)
    {
      try
      {
        PrintWriter writer = new PrintWriter(f, UTF_8);
        String s = String.format("This is an update :: %d\n", i++);
        writer.append(s);
        writer.close();
        api.saveFile(f);
      }
      catch( FileNotFoundException e )
      { Log.e(TAG, e.getMessage(), e); }
      catch( UnsupportedEncodingException e )
      { Log.e(TAG, e.getMessage(), e); }
      catch( MyDeskException e )
      { Log.e(TAG, e.getMessage(), e); }
    }
  }
  
  // ---------------------------------------------------------------------------
  
  @SuppressLint("DefaultLocale")
  public static String printMeta(MetaData m)
  {
    String s = String.format("Name: %s Id: %s Ses: %d", 
        m.fileName(), m.fileId(), m.sequenceNumber());
    Log.i(TAG, s);
    return s;
  }
  
  // ---------------------------------------------------------------------------
  
  void clear()
  {
    textView.setText("");
  }
  
  // ---------------------------------------------------------------------------
}
