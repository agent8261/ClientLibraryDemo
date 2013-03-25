package com.example.clientlibrarydemo.networktask;

import edu.umich.imlc.mydesk.cloud.backend.android.NetworkIO;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.FileNotFound;
//import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NoMyDeskAccount;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyID;
//import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.UnauthorizedAccess;

import android.widget.TextView;

public class LoadFileTask extends NetworkTask
{
  private static final String DEFAULT_FILE_ID = "None";
  private static String fileID = DEFAULT_FILE_ID;
  private byte [] data;
  
  public LoadFileTask(TextView textView_)
  {
    super(textView_);
  }

  @Override
  protected Void doInBackground(Void... params)
  {
    try
    {
      data = NetworkIO.getFile(fileID);
    }
    catch( NullOrEmptyID e )
    {
      e.printStackTrace();
    }
    catch( FileNotFound e )
    {
      e.printStackTrace();
    }
/*    catch( NoMyDeskAccount e )
    {
      e.printStackTrace();
    }
    catch( UnauthorizedAccess e )
    {
      e.printStackTrace();
    }*/
    return null;
  }

  @Override
  protected void onPostExecute(Void result)
  {
    verifyFile();
  }

  //===========================================================================
  // Verify the binary file worked
  private void verifyFile()
  {
    if(data == null)
    {
      return;
    }
    
    int i = 0;
    for( byte b : data )
    {
      print(""+ b + " ");
      if( (i % 4) == 3 )
      { 
        println(""); }
      i++;
    }
    println("End Byte File\n");
  }
  
  public static void setFileID(String fileID_)
  {
    fileID = fileID_;
    if((fileID_ == null) || (fileID_.isEmpty()))
    {
      fileID = DEFAULT_FILE_ID;
    }
  }
  
  public static String getFileID()
  {
    return fileID;
  }
}
