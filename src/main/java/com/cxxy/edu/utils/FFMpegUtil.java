package com.cxxy.edu.utils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class FFMpegUtil {
	    private static String ffmpegEXE = "E:\\ffmpeg\\ffmpeg-20190513-dcc9998-win64-static\\bin\\ffmpeg.exe";


	    public static void convetor(String videoInputPath, String videoOutPath) throws Exception
	    {
			File file = new File(videoInputPath);
	    	List<String> command = new ArrayList<String>();
			command.add(ffmpegEXE);
			command.add("-i");
			command.add(videoInputPath);
			command.add("-y");
			command.add("-movflags");
			command.add("faststart");
			command.add(videoOutPath);
			ProcessBuilder builder = new ProcessBuilder(command);
			Process process = null;
			try {
				process = builder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			InputStream errorStream = process.getErrorStream();
			InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
			BufferedReader br = new BufferedReader(inputStreamReader); String line = "";
			while ((line = br.readLine()) != null) {

			}
			if (br != null) {
	        	br.close(); 
	        } 
	        if (inputStreamReader != null) { 
	        	inputStreamReader.close(); 
	        	} 
	        if (errorStream != null) 
	        { 
	        	errorStream.close(); 
	        	
	        }
            System.out.println("格式转换成功");

	        //删除初始文件
			file.delete();
			System.out.println("初始文件删除成功");

	    }

}
