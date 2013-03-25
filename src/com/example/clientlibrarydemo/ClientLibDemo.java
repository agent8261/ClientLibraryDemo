package com.example.clientlibrarydemo;

import java.io.File;

import com.example.clientlibrarydemo.networktask.CheckConnectionTask;
import com.example.clientlibrarydemo.networktask.GetAccessTask;
import com.example.clientlibrarydemo.networktask.GetMetaTask;
import com.example.clientlibrarydemo.networktask.GetShortInfoTask;
import com.example.clientlibrarydemo.networktask.LoadFileTask;
import com.example.clientlibrarydemo.networktask.NetworkTask;
import com.example.clientlibrarydemo.networktask.SaveFileTask;

//import edu.umich.imlc.mydesk.cloud.backend.android.auth.LoginUtilities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

public class ClientLibDemo extends Activity
{
  private final String TAG = "INFO";
  private final String fileName = "A_Binary_File";
  
  private TextView textView = null;
  
  // ----------------------------------------------------------------------------
  // ----------------------------------------------------------------------------
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_lib_demo);
    textView = (TextView)findViewById(R.id.textView1);
    CookieSyncManager.createInstance(getApplicationContext());
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
  protected void onResume()
  {
    super.onResume();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  protected void onStart()
  {
    super.onStart();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) 
  {    
    switch (item.getItemId()) 
    {
      case R.id.saveFile:
      {
        File file = new File(getFilesDir(), fileName);
        return clearScreenThenExecuteTask(new SaveFileTask(textView, file, fileName));
      }
      case R.id.loadFile:
      {
        return clearScreenThenExecuteTask(new LoadFileTask(textView));
      }
      case R.id.login:
      {
        return clearScreenThenLogin();
      } 
      case R.id.getShortList:
      {
        return clearScreenThenExecuteTask(new GetShortInfoTask(textView));
      }
      case R.id.getMetaData:
      {
        return clearScreenThenExecuteTask(new GetMetaTask(textView));
      }
      case R.id.doSecureGet:
      {
        return clearScreenThenExecuteTask(new GetAccessTask(textView));
      }
      case R.id.checkConnection:
      {
        return clearScreenThenExecuteTask(new CheckConnectionTask(textView));
      }
      case R.id.exit:
      {
        finish();
        return true;
      }
    }
    return super.onOptionsItemSelected(item);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void onActivityResult
    (final int requestCode, final int resultCode, final Intent data) 
  {
    Log.i(TAG,"Start Activity Result");
    //LoginUtilities.doOnActivityResult(this, requestCode, resultCode, data);
  }
  
  private boolean clearScreenThenLogin()
  {
    return clearScreenThenLoginGAuth();
  }
  
  // ---------------------------------------------------------------------------
  
  @SuppressWarnings("unused")
  private boolean clearScreenThenLoginNick()
  {
    clear();
    //Intent intent = new Intent(this, AccountList.class);
   // startActivity(intent);
    return true;
  }
  
  // ---------------------------------------------------------------------------
  
  private boolean clearScreenThenLoginGAuth()
  {
    clear();
    Log.i(TAG, "Starting Account Picker");
    textView.append("Starting Account Picker\n");
    //LoginUtilities.startAccountPicker(this);
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
  
  protected void clear()
  {
    textView.setText("");
  }
  
  // ---------------------------------------------------------------------------
  
  protected void println(String text)
  {
    textView.append(text +"\n"); 
  }
  
  // ---------------------------------------------------------------------------
  protected void print(String text)
  {
    textView.append(text);
  }
  
  /*
  private void makePb()
  {
    FileShortInfoList_PB.Builder iBuilder = FileShortInfoList_PB.newBuilder();
    String id = "test";
    long seq = 0;
    
    FileShortInfoList_PB info = iBuilder.addFileShortInfo(FileShortInfo_PB.newBuilder().setFileID(id).setSequenceNumber(seq).build()).build();
    printByteArray(info.toByteArray());
  }
  
  private static void printByteArray(byte [] bArr)
  {
    int i=0;
    for(byte b: bArr)
    {
      String bStr = toHexString(b);
      System.out.print(bStr);
      if((i%4) == 3 )
      {
        System.out.print("\n");
      }
      else
      {
        System.out.print(".");
      }
      i++;
    }
    System.out.print("\n");
  }
  
  private static String toHexString(byte b)
  {
    return String.format("%02X ", b);  
  }
  */
}
