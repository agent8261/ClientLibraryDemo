package com.example.clientlibrarydemo.networktask;

import java.util.List;

import edu.umich.imlc.mydesk.MyDeskProtocolBuffer.FileMetaData_ShortInfo_PB;
import edu.umich.imlc.mydesk.cloud.backend.android.NetworkIO;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyField;
import edu.umich.imlc.mydesk.cloud.backend.android.exceptions.NullOrEmptyID;
import android.widget.TextView;

public class GetShortInfoTask extends NetworkTask
{
  private List<FileMetaData_ShortInfo_PB>  listShortInfo = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GetShortInfoTask(TextView textView_)
  {
    super(textView_);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  protected Void doInBackground(Void... params)
  {
    try
    {
      listShortInfo = NetworkIO.getAllFileShortInfo();
    }
    catch( NullOrEmptyField e )
    {
      e.printStackTrace();
    }
    catch( NullOrEmptyID e )
    {
      e.printStackTrace();
    }
    return null;
  }
  
  // ---------------------------------------------------------------------------
  @Override
  protected void onPostExecute(Void result)
  {
    if(listShortInfo == null)
    {
      println("No Data Returned");
      return;
    }
    
    for(FileMetaData_ShortInfo_PB shortInfo: listShortInfo)
    {
      println("FileName: " + shortInfo.getFileID());
      println("Seq Num: " + shortInfo.getSequenceNumber());      
    }    
  }
}
