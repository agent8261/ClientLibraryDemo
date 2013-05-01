package com.example.clientlibrarydemo.networktask;

import com.example.clientlibrarydemo.ClientLibDemo;
import com.example.clientlibrarydemo.FileOpsDemo;
import com.example.clientlibrarydemo.Util;

import android.util.Log;
import android.widget.TextView;

public class GetMetaTask extends NetworkTask
{
  private static final String TAG = GetMetaTask.class.getSimpleName();
  String resultStr;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GetMetaTask(TextView textView_)
  {
    super(textView_);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    Util.printMethodName();
    resultStr = FileOpsDemo.doGetMetaData(ClientLibDemo.fileID);
    Log.i(TAG, resultStr);
    return null;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected void onPostExecute(Void result)
  {
    Util.printMethodName();
    println("Get Meta Task Done. Result:");
    println(resultStr);
  }

}
