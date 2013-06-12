package com.example.clientlibrarydemo;

import java.io.File;

import com.example.clientlibrarydemo.networktask.CheckConnectionTask;
import com.example.clientlibrarydemo.networktask.GetAccessTask;
import com.example.clientlibrarydemo.networktask.GetMetaTask;
import com.example.clientlibrarydemo.networktask.GetShortInfoTask;
import com.example.clientlibrarydemo.networktask.LoadFileTask;
import com.example.clientlibrarydemo.networktask.NetworkTask;
import com.example.clientlibrarydemo.networktask.OverWriteFileTask;
import com.example.clientlibrarydemo.networktask.SaveFileTask;
import com.example.clientlibrarydemo.storage.StorageDemo;

import edu.umich.imlc.mydesk.cloud.android.auth.LoginCallback;
import edu.umich.imlc.mydesk.cloud.android.auth.LoginUtilities;
import edu.umich.imlc.mydesk.cloud.android.exceptions.AppNeedsUserPermission;
import edu.umich.imlc.mydesk.cloud.client.utilities.Util;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ClientLibDemo extends Activity
{
  private final String TAG = ClientLibDemo.class.getSimpleName();
  static final String HANDLER_NAME = "BusHandler";

  //public static final String fileName = "Short.flac";
  public static final String fileName = "demoFile_Name";
  public static final String fileID = "demoFile_ID";
  public static final String fileType = "demoFile_type";
  
  public static final String overwriteName = "Another_File_name";
  public static final String storeName = "CopyOfDemo";
  
  @SuppressLint("SdCardPath")
  private final String filePathStr = "/sdcard/";
  
  private TextView textView = null;
  
  StorageDemo storageDemo = null;
  
  boolean loggedIn = false;
  
  File orginalPath;
  File overwritePath;
  File storedPath;
  
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_lib_demo);
    textView = (TextView)findViewById(R.id.textView1);
    
    orginalPath = new File(getExternalFilesDir(null), fileName);
    overwritePath = new File(getExternalFilesDir(null), overwriteName);
    storedPath = new File(getExternalFilesDir(null), storeName);    
    storageDemo = new StorageDemo(this);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_client_lib_demo, menu);
    return true;
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) 
  {    
    switch (item.getItemId()) 
    {
      // Authentication
      case R.id.login:
        return clearScreenThenLogin();
      case R.id.verifyLogin:
        return clearScreenThenExecuteTask(new GetAccessTask(textView));
      case R.id.checkConnection:
        return clearScreenThenExecuteTask(new CheckConnectionTask(textView));
      // File Operations
      case R.id.createFile:
        return clearScreenThenExecuteTask(new SaveFileTask(textView, orginalPath));
      case R.id.loadFile:
        return clearScreenThenExecuteTask(new LoadFileTask(textView, storedPath));
      case R.id.overWriteFile:
        return clearScreenThenExecuteTask(new OverWriteFileTask(textView, overwritePath));
      case R.id.getMetaData:
        return clearScreenThenExecuteTask(new GetMetaTask(textView));
      case R.id.getShortList:
        return clearScreenThenExecuteTask(new GetShortInfoTask(textView));
      
      // Storage Srvc
      case R.id.srvcLogin:
        storageDemo.doLogin(); return true;
      case R.id.srvcCreate:
        storageDemo.createFiles(); return true;
      case R.id.srvcSave:
        storageDemo.saveFiles();  return true;
      case R.id.srvcSync:
        storageDemo.sync(); return true;
        
      // Misc
      case R.id.exit:
      {
        finish();
        return true;
      }
    }
    return super.onOptionsItemSelected(item);
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onDestroy()
  {
    super.onDestroy();
    Util.printMethodName(TAG);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void onActivityResult
    (final int requestCode, final int resultCode, final Intent data) 
  {
    LoggedCallback cb = new LoggedCallback();
    LoginUtilities.doOnActivityResult(this, requestCode, resultCode, data, cb);
  }
  
  // ---------------------------------------------------------------------------
  
  private boolean clearScreenThenLogin()
  {
    clear();
    Log.i(TAG, "Begin Login...");
    textView.append("Begin Login..\n");
    LoginUtilities.startAccountPicker(this, false);
    return true;
  }
 
  // ---------------------------------------------------------------------------
  
  private boolean clearScreenThenExecuteTask(NetworkTask task)
  {
    clear();
    task.execute(null, null);
    return true;
  }

  // ---------------------------------------------------------------------------
  
  void clear()
  {
    textView.setText("");
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class LoggedCallback implements LoginCallback
  {

    @Override
    public void onSuccess(String accountName)
    {
      Log.i(TAG, "Logged Successful");
      textView.append(accountName);
    }

    @Override
    public void onFailure(Exception e)
    { 
      Log.e(TAG, e.getMessage(), e);
      if(e instanceof AppNeedsUserPermission)
      {
        AppNeedsUserPermission err = (AppNeedsUserPermission) e;
        startActivity(err.getIntent());
      }
    }
    
  }
  
  // ---------------------------------------------------------------------------
}
