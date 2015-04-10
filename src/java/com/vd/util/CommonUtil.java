package com.vd.util;

import java.io.File;
import java.io.IOException;

public class CommonUtil {
	public static void delFile(String filepath) throws IOException{  
		File f = new File(filepath);//定义文件路径         
		if(f.exists() && f.isDirectory()){//判断是文件还是目录  
		    if(f.listFiles().length==0){//若目录下没有文件则直接删除  
		        f.delete();  
		    } else {
		    	File delFile[]=f.listFiles();  
		        int i =f.listFiles().length;
		        for(int j=0;j<i;j++){  
		            if(delFile[j].isDirectory()){  
		                      delFile(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径  
		            }  
		            delFile[j].delete();//删除文件  
		        }
		        f.delete();
			}
		}  
	}
}
