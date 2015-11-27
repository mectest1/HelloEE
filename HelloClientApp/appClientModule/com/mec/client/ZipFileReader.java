package com.mec.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.Path;

public class ZipFileReader {

	public static void main(String[] args) throws Exception{
		File pathDirectory = new File(pathDir);
		if(!pathDirectory.exists()){
			pathDirectory.mkdirs();
		}
		
		extractZipFile(new File(path));
	}
	
	static void extractZipFile(File path) throws Exception{
		int bufferSize = 2048;
		int count = 0;
		out.printf("----------------zip file: %s\n", path);
		FileInputStream fis = new FileInputStream(path);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		FileOutputStream fos = null;
		File tmpFile = null;
		byte[] data = null;
		while(null != (entry = zis.getNextEntry())){
			out.println(entry.getName());
			if(!isZipFile(entry.getName())){
				continue;
			}
//				bufferSize = entry.getSize();
			count = 0;
			data = new byte[bufferSize];
//			tmpFile = File.createTempFile(entry.getName(), "tmp", pathDirectory);
			tmpFile = new File(String.format("%s%s%s", pathDir, entry.getName().replaceAll("/", "_"), System.currentTimeMillis()));
			fos = new FileOutputStream(tmpFile);
//				ByteOutputStream ois = new ByteArrayOutputStream();
			while(-1 != (count = zis.read(data, 0, bufferSize))){
				fos.write(data, 0, count);
			}
			if(null != fos){
				fos.close();
			}
			
			zis.closeEntry();
			extractZipFile(tmpFile);
		}
	}
	
	static boolean isZipFile(String name){
//		return name.endsWith(".jar") || name.endsWith(".war");
		return name.endsWith(".war");
	}
	
	static final String pathDir = "E:/Users/MEC/Music/2015-11-27_Season/tmp/";
	static final String path = "E:/Users/MEC/Music/2015-11-27_Season/CE.ear";
	private static final PrintStream out = System.out;
}
