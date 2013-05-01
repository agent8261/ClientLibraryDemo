package com.example.clientlibrarydemo.networktask;

import com.example.clientlibrarydemo.FileOpsDemo;
import com.example.clientlibrarydemo.Util;

import android.util.Log;
import android.widget.TextView;

public class GetShortInfoTask extends NetworkTask
{
  private static final String TAG = GetShortInfoTask.class.getSimpleName();
  String resultStr;
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GetShortInfoTask(TextView textView_)
  {
    super(textView_);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    Util.printMethodName();
    resultStr = FileOpsDemo.doLoadList();
    Log.i(TAG, resultStr);
    return null;
  }
  
  // ---------------------------------------------------------------------------
  @Override
  protected void onPostExecute(Void result)
  {
    println("Get Short List Task Done. Result:");
    println(resultStr);
  }
}
