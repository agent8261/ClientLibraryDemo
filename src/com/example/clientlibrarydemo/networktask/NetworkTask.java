package com.example.clientlibrarydemo.networktask;


import android.os.AsyncTask;
import android.widget.TextView;

public abstract class NetworkTask extends AsyncTask<Void, Void, Void>
{
  protected TextView textView = null;
  
  public NetworkTask(TextView textView_)
  {
    textView = textView_;
  }
 
  abstract protected Void doInBackground(Void... params);
  
  abstract protected void onPostExecute(Void result);
  
  protected void clearView()
  {
    textView.setText("");
  }
  
  protected void println(String text)
  {
    textView.append(text +"\n"); 
  }
  
  protected void print(String text)
  {
    textView.append(text);
  }
}
