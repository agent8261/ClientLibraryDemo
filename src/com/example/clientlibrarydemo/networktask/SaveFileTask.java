package com.example.clientlibrarydemo.networktask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.clientlibrarydemo.ClientLibDemo;
import com.example.clientlibrarydemo.FileOpsDemo;

import edu.umich.imlc.mydesk.cloud.backend.android.utilities.Util;

import android.util.Log;
import android.widget.TextView;

public class SaveFileTask extends NetworkTask
{
  private static final String TAG = SaveFileTask.class.getSimpleName();
  private File filePath;
  String resultStr;
  
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  
  public SaveFileTask(TextView textView_, File filePath_)
  {
    super(textView_);
    filePath = filePath_;
  }

  // --------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    Util.printMethodName();
    try
    {
      createByteFile(filePath);
      resultStr = FileOpsDemo.doStoreFile
          (filePath, ClientLibDemo.fileID, ClientLibDemo.fileName, ClientLibDemo.fileType);
      Log.i(TAG, resultStr);
    }
    catch(IOException e)
    {
      resultStr = "IO Exception";
      Log.e(TAG, resultStr, e);
    }
    return null;
  }
  
  // --------------------------------------------------------------------------
  
  @Override
  protected void onPostExecute(Void result)
  {
    Util.printMethodName();
    println("Save File Task Done. Result:");
    println(resultStr);
  }
    
  // --------------------------------------------------------------------------
  // Create some binary data to send to the server
  public void createByteFile(File fileObj) 
      throws IOException
  {
    Util.printMethodName();
    FileOutputStream file = new FileOutputStream(fileObj);
    byte [] data = FileOpsDemo.createTestBytes();
    file.write(data, 0, data.length);
    file.close();    
  }
  
}
