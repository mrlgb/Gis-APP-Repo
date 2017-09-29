package com.tt.rds.app.common;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class PathUtils {
	/**
	 * 得到外置的SD卡目录。如果有第二张扩展卡，则采用第二张扩展卡
	 * @return
	 */
	public static File getExternalSDCardDirectory2()
	{
	    File innerDir = Environment.getExternalStorageDirectory();
	    File[] files = innerDir.listFiles();
	    for (File file : files) {
	        if (file.getName().equals("external_sd")) {
	           return file;
	        }
	    }
	    return innerDir;
	}
	
	/**
	 * 得到Smooth公司的程序的根目录。不带斜杠。
	 * @return
	 */
	public static File getSmoothRootDirectory()
	{
		return new File(getExternalSDCardDirectory2().getAbsolutePath() + File.separator + "Smooth");
	}
	
	/**
	 * 一层一层创建目录
	 * @param path
	 * @return
	 */
	public static Boolean mkdirs(File path,File sdCardPath)
	{
		if(!path.mkdirs())
		{
		 //如果直接不成功，则一层一层创建。
			//1、寻求根节点
			//File RootPath = getExitsRootPath(path);
			//2、一节一节创建
			
			return CreatePath(path,sdCardPath);
		}
		else
			return true;
	}
	
	private static File getExitsRootPath(File path)
	{
		Log.i("wangaiguo", path.getAbsolutePath()+" exits:"+path.exists());
		if(path.exists()) return path;
		else return getExitsRootPath(path.getParentFile());
	}
	
	/**
	 * 该方法有错误
	 * @param path
	 * @param exitsPath
	 * @return
	 */
	private static Boolean CreatePath(File path,File exitsPath)
	{
		if(path.compareTo(exitsPath) == 0) return true;
		else
		{
			if(path.getParentFile().compareTo(exitsPath)== 0)
			{
				return path.mkdir();
			}
			else
			{
				return CreatePath(path.getParentFile(),exitsPath);
			}
		}
	}
	
}
