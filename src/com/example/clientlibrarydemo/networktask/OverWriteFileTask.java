package com.example.clientlibrarydemo.networktask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;
import android.widget.TextView;

import com.example.clientlibrarydemo.ClientLibDemo;
import com.example.clientlibrarydemo.FileOpsDemo;
import com.example.clientlibrarydemo.Util;

public class OverWriteFileTask extends NetworkTask
{
  private static final String TAG = SaveFileTask.class.getSimpleName();
  private File filePath;
  String resultStr;
  
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  
  public OverWriteFileTask(TextView textView_, File filePath_)
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
      resultStr = FileOpsDemo.overWriteFile
          (filePath, ClientLibDemo.fileID, ClientLibDemo.fileName, ClientLibDemo.fileType, 1);
      
      //resultStr = FileOpsDemo.doStoreFile
        //  (filePath, ClientLibDemo.fileID, ClientLibDemo.fileName, ClientLibDemo.fileType);
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
    file.write(data, 0, data.length);
    file.write(data, 0, data.length);
    file.close();    
  }
  
}