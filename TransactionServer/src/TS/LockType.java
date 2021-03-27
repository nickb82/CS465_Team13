/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TS;

/**
 *
 * @author young
 */
public class LockType 
{
 public static final int READ = 1;
 public static final int WRITE = 2;
 
 private static int type = 0;
 
 public void setType(int type)
 {
    this.type = type;
 }
 
 public int getType()
 {
    return type;
 }
 
 public static void promote()
 {
    if(type == READ)
    {
       type = WRITE;
    }
 }
}
