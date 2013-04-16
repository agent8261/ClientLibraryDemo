package com.example.clientlibrarydemo;

import android.util.Log;

public class Util
{
  public static void printMethodName()
  {
    String temp = (new Throwable("dummy")).getStackTrace()[1].toString();
    int end = temp.indexOf("(");
    int start = temp.lastIndexOf(".", temp.lastIndexOf(".", end) - 1) + 1;
    String mName = new String(temp.substring(start, end) + "()");
    Log.i(Util.class.getSimpleName(), mName);
  }
}
