package com.example.clientlibrarydemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import edu.umich.imlc.mydesk.MyDeskProtocolBuffer.FileMetaData_PB;
import edu.umich.imlc.mydesk.MyDeskProtocolBuffer.FileMetaData_ShortInfo_PB;
import edu.umich.imlc.mydesk.cloud.client.exceptions.Exception_DS;
import edu.umich.imlc.mydesk.cloud.client.exceptions.UserHasNoMyDeskAccount;
import edu.umich.imlc.mydesk.cloud.client.network.NetUtil;
import edu.umich.imlc.mydesk.cloud.client.network.NetworkOps;

import android.annotation.SuppressLint;
import android.util.Log;


/**
 * Test bed of File Operations
 */
public class FileOpsDemo
{
  private static final String TAG = FileOpsDemo.class.getSimpleName();
  private static final String FILE_ID_HEADER = "FileID: ";
  private static final String SEQ_NUM_HEADER = " Seq Num: ";
  
  
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  
  public static String validateCookie()
  {
    String result = null;
    
    try
    {
      byte [] data = NetUtil.validateCookie();
      result = new String(data, Charset.forName("UTF-8"));
    }
    catch( UserHasNoMyDeskAccount e )
    {
      e.printStackTrace();
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static String createNewFile(File filePath, String fileID, String name, String type)
  {
    Util.printMethodName();
    String result = null;
    FileInputStream in = null;
    try
    {
      in = new FileInputStream(filePath);
      String sha = getSHA_1_Sum(in);
      in.close();
      FileMetaData_PB meta = NetworkOps.createFile(filePath, fileID, name, type);
      
      StringBuffer b = new StringBuffer();
      b.append(String.format("File Created Successfully. Sha: %s\n", sha));
      if(meta.hasFileID())
        b.append(String.format("ID: %s ", meta.getFileID()));
      else
        b.append("ID: NONE");
      
      if(meta.hasSequenceNumber())
        b.append(String.format( "Seq Num: %d\n", meta.getSequenceNumber()));
      else
        b.append("Seq Num: NONE");
      result = b.toString();
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( FileNotFoundException e )
    {
      result = "Invalid File Path given: File Not Found";
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      result = "IO Exception";
      Log.e(TAG, result, e);
    }
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static String overWriteFile(File filePath, String fileID, String name, String type)
  {
    Util.printMethodName();
    String result = null;
    FileInputStream in = null;
    try
    {
      in = new FileInputStream(filePath);
      String sha = getSHA_1_Sum(in);
      in.close();
      FileMetaData_PB meta = NetworkOps.overWriteFile(filePath, fileID, name, type);
      
      StringBuffer b = new StringBuffer();
      b.append(String.format("File Created Successfully. Sha: %s\n", sha));
      b.append(String.format("ID: %s SeqNum: %d\n", meta.getFileID(), meta.getSequenceNumber()));
      result = b.toString();
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( FileNotFoundException e )
    {
      result = "Invalid File Path given: File Not Found";
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      result = "IO Exception";
      Log.e(TAG, result, e);
    }
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static String doLoadFile(String fileID)
  {
    Util.printMethodName();
    String result = null;
    try
    {
      byte [] data = NetworkOps.getFile(fileID);
      result = getSHA_1_Sum(data);
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  @SuppressLint("DefaultLocale")
  public static String doLoadFile(String fileID, File filePath)
  {
    Util.printMethodName();
    String result = null;
    try
    {
      Long seq = NetworkOps.getFile(fileID, filePath);
      FileInputStream in = new FileInputStream(filePath);
      String sha = getSHA_1_Sum(in); 
      result = String.format("Load Successful. Seq: %d Sha: %s\n", seq, sha);
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      result = "IO Exception";
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }        
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static String doLoadList()
  {
    Util.printMethodName();
    String result = null;
    try
    {
      List<FileMetaData_ShortInfo_PB> list;
      list = NetworkOps.getListMetaData();
      StringBuilder b = new StringBuilder();
      
      for(FileMetaData_ShortInfo_PB info: list)
      {
        b.append(FILE_ID_HEADER);
        b.append(info.getFileID());
        b.append(SEQ_NUM_HEADER);
        b.append(info.getSequenceNumber());
        b.append('\n');
      }
      result = b.toString();
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static String doGetMetaData(String fileID)
  {
    Util.printMethodName();
    String result = null;
    try
    {
      FileMetaData_PB meta;
      meta = NetworkOps.getFileMetaData(fileID);
      StringBuilder b = new StringBuilder();
      
      b.append(FILE_ID_HEADER).append(meta.getFileID());
      b.append(SEQ_NUM_HEADER).append(meta.getSequenceNumber()).append('\n');
      
      if(meta.hasFileName())
        b.append("File Name: ").append(meta.getFileName()).append('\n');
      else
        b.append("No File Name").append('\n');
      
      if(meta.hasFileType())
        b.append("File Type: ").append(meta.getFileType()).append('\n');
      else
        b.append("No Type").append('\n');
      
      if(meta.hasLastUpdated())
        b.append("Last Updated: ").append(meta.getLastUpdated()).append('\n');
      else
        b.append("Never Updated").append('\n');
      result = b.toString();
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static String doStoreFile(File filePath, String fileID, String name, String type)
  {
    Util.printMethodName();
    String result = null;
    FileInputStream in = null;
    try
    {
      in = new FileInputStream(filePath);
      String sha = getSHA_1_Sum(in);
      in.close();
      
      NetworkOps.storeFile(filePath, fileID, name, type);      
      result = "File Stored Successfully. Sha: " + sha;
    }
    catch(Exception_DS e)
    { 
      result = e.getMessage();
      if((result == null) || result.isEmpty())
      { result = "Failure"; }
      Log.e(TAG, result, e);
    }
    catch( FileNotFoundException e )
    {
      result = "Invalid File Path given: File Not Found";
      Log.e(TAG, result, e);
    }
    catch( IOException e )
    {
      result = "IO Exception";
      Log.e(TAG, result, e);
    }
    return result;
  }
  
  // --------------------------------------------------------------------------
  
  public static byte [] createTestBytes()
  {
    Util.printMethodName();
    byte [] data = new byte [16];
    data[0] = 6;   data[1] = 8;   data[2] = 2;   data[3] = 1;
    data[4] = 6;   data[5] = 8;   data[6] = 2;   data[7] = 1;
    data[8] = -6;  data[9] = -8;  data[10] = -2; data[11] = -1;
    data[12] = -6; data[13] = -8; data[14] = -2; data[15] = -1;
    return data;
  }
  
  // --------------------------------------------------------------------------
  
  public static String getSHA_1_Sum(InputStream input) throws IOException
  {
    Util.printMethodName();
    final int bufferSize = 10240;
    try
    {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      
      byte[] buffer = new byte[bufferSize];
      int length = 0;
      while( (length = input.read(buffer, 0, bufferSize)) > 0 )
      { md.update(buffer, 0, length); }
      return bytesToHex(md.digest());
    }
    catch(NoSuchAlgorithmException e)
    { throw new Error(e); }
  }
  
  // --------------------------------------------------------------------------
  
  public static String getSHA_1_Sum(byte[] unencodedBytes) 
  {
    Util.printMethodName();
    try
    {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      return bytesToHex(md.digest(unencodedBytes));
    }
    catch(NoSuchAlgorithmException e)
    { throw new Error(e); }
  }
  
  // --------------------------------------------------------------------------
  
  private static String bytesToHex(byte[] bytes) 
  {
    Util.printMethodName();
    final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    char[] hexChars = new char[bytes.length * 2];
    int v;
    for ( int j = 0; j < bytes.length; j++ ) 
    {
        v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }
}
