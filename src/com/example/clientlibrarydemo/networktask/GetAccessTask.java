package com.example.clientlibrarydemo.networktask;


import com.example.clientlibrarydemo.FileOpsDemo;

import android.widget.TextView;

public class GetAccessTask extends NetworkTask
{
  private String dataStr = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GetAccessTask(TextView textView_)
  {
    super(textView_);
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... arg0)
  {
    dataStr = FileOpsDemo.validateCookie();
    return null;
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  protected void onPostExecute(Void result)
  {
    if(dataStr == null)
    {
      println("No Data Returned");
      return;
    }
    println(dataStr);
  }
}
