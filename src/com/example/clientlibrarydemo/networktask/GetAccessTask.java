package com.example.clientlibrarydemo.networktask;

import java.nio.charset.Charset;

import android.widget.TextView;
import edu.umich.imlc.mydesk.cloud.backend.android.NetworkIO;

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
    
    byte [] data = NetworkIO.getAccess();
    dataStr = new String(data, Charset.forName("UTF-8"));
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
