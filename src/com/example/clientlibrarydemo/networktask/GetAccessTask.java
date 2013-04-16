package com.example.clientlibrarydemo.networktask;

import java.io.IOException;
import java.nio.charset.Charset;

import android.widget.TextView;
import edu.umich.imlc.mydesk.cloud.backend.android.NetworkIO;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.Exception_DS;

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
    
    byte[] data;
    try
    {
      data = NetworkIO.getAccess();
      if(data != null)
        dataStr = new String(data, Charset.forName("UTF-8"));
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    catch( Exception_DS e )
    {
      e.printStackTrace();
    }
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
