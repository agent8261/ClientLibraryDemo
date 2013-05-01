package com.example.clientlibrarydemo.networktask;

import edu.umich.imlc.mydesk.cloud.client.network.NetworkOps;
import android.widget.TextView;

public class CheckConnectionTask extends NetworkTask
{
  private boolean on = false;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public CheckConnectionTask(TextView textView_)
  {
    super(textView_);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    on = NetworkOps.isConnected();
    return null;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected void onPostExecute(Void result)
  {
    if(on)
    {
      println("True: Connected");
    }
    else
    {
      println("False: Can't Reach Server");
    }
  }

}
