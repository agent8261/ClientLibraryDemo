package com.example.clientlibrarydemo.networktask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.umich.imlc.mydesk.cloud.backend.android.NetworkIO;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.FileNotFound;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyField;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyID;

import android.widget.TextView;

public class SaveFileTask extends NetworkTask
{
  private static final int byteSize = 16;
  private static final String fileType = "bin";
  private String fileName;
  private String message = null;
  
  private File filePath;
  
  public SaveFileTask(TextView textView_, File filePath_, String fileName_)
  {
    super(textView_);
    filePath = filePath_;
    fileName = fileName_;
  }

  @Override
  protected Void doInBackground(Void... params)
  {
    createByteFile(filePath);
    String fileId = generateFileIDString();
    LoadFileTask.setFileID(fileId);
    try
    {
      NetworkIO.storeFile(filePath, fileId, fileName, fileType);
    }
    catch( NullOrEmptyField e )
    {
      message = e.getMessage();
      //e.printStackTrace();
    }
    catch( NullOrEmptyID e )
    {
      message = e.getMessage();
      //e.printStackTrace();
    }
    catch( FileNotFound e )
    {
      message = e.getMessage();
      //e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(Void result)
  {
    if(message != null)
    {
      println(message);
      return;
    }
    verifyFile(filePath);
  }
  
  //===========================================================================
  // Verify the binary file worked
  void verifyFile(File fileObj)
  {
    FileInputStream file = null;
    println("Verifying Save File");
    try
    {
      file = new FileInputStream(fileObj);
      byte[] data = new byte[byteSize];
      file.read(data);
      int i = 0;
      for( byte b : data )
      {
        print(""+ b + " ");
        if( (i % 4) == 3 )
        { println(""); }
        i++;
      }
      println("End Byte File");
    }
    catch( FileNotFoundException e )
    { e.printStackTrace();  }
    catch( IOException e )
    { e.printStackTrace();  }
    finally
    {
      if( file != null )
      {
        try
        { file.close(); }
        catch( IOException e )
        { e.printStackTrace();  }
      }
    }
    println("Save File Task Done");    
  }
  
  //===========================================================================
  // Create some binary data to send to the server
  public void createByteFile(File fileObj)
  {
    FileOutputStream file;
    try
    {
      file = new FileOutputStream(fileObj);
      byte [] data = new byte [byteSize];
      data[0] = 6;   data[1] = 8;   data[2] = 2;   data[3] = 1;
      data[4] = 6;   data[5] = 8;   data[6] = 2;   data[7] = 1;
      data[8] = -6;  data[9] = -8;  data[10] = -2; data[11] = -1;
      data[12] = -6; data[13] = -8; data[14] = -2; data[15] = -1;

      file.write(data, 0, byteSize);
      file.close();      
      //println("Create Byte File Done");
    }
    catch( FileNotFoundException e )
    {
      //println("File not found");
      e.printStackTrace();
    }
    catch( IOException e )
    {
      //println("IOException");
      e.printStackTrace();
    }
    
  }
  
  public static String generateFileIDString()
  {
    return "RandomFileID";
    //return UUID.randomUUID().toString() + "||" + UUID.randomUUID().toString();
  }// generateFileIDString

}
