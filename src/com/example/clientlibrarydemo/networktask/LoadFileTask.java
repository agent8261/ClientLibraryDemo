package com.example.clientlibrarydemo.networktask;

import java.io.File;

import com.example.clientlibrarydemo.ClientLibDemo;
import com.example.clientlibrarydemo.FileOpsDemo;
import com.example.clientlibrarydemo.Util;


import android.util.Log;
import android.widget.TextView;

public class LoadFileTask extends NetworkTask
{
  private static final String TAG = LoadFileTask.class.getSimpleName();
  private File filePath;
  String resultStr;
  
  // --------------------------------------------------------------------------
  
  public LoadFileTask(TextView textView_, File filePath_)
  {
    super(textView_);
    filePath = filePath_;
  }

  // --------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    Util.printMethodName();
    resultStr = FileOpsDemo.doLoadFile(ClientLibDemo.fileID, filePath);
    Log.i(TAG, resultStr);
    return null;
  }

  // --------------------------------------------------------------------------
  
  @Override
  protected void onPostExecute(Void result)
  {
    Util.printMethodName();
    println("Load File Task Done. Result:");
    println(resultStr);
  }    
}
