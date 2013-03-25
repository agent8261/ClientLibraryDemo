package com.example.clientlibrarydemo.networktask;

import edu.umich.imlc.mydesk.MyDeskProtocolBuffer.FileMetaData_PB;
import edu.umich.imlc.mydesk.cloud.backend.android.NetworkIO;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.FileNotFound;
//import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NoMyDeskAccount;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyField;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyID;
//import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.UnauthorizedAccess;
import android.widget.TextView;

public class GetMetaTask extends NetworkTask
{
  private FileMetaData_PB meta = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GetMetaTask(TextView textView_)
  {
    super(textView_);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    String fileId = LoadFileTask.getFileID();
    try
    {
      meta = NetworkIO.getFileMetaData(fileId);
    }
    catch( NullOrEmptyField e )
    {
      e.printStackTrace();
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

  // ---------------------------------------------------------------------------
  
  @Override
  protected void onPostExecute(Void result)
  {
    if(meta == null )
    {
      println("No Meta Data for File");
      return;
    }
    
    println("File ID: "+ meta.getFileID());
    println("Seq Num: " + meta.getSequenceNumber());
    
    if(meta.hasFileName())
    {
      println("File Name: " + meta.getFileName());
    }
    else
    {
      println("No File Name");
    }
    
    if(meta.hasFileType())
    {
      println("File Type: " + meta.getFileType());
    }
    else
    {
      println("No Type");
    }
    
    if(meta.hasLastUpdated())
    {
      println("Last Updated: " + meta.getLastUpdated());
    }
    else
    {
      println("Never Updated");
    }
  }

}
