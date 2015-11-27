package com.mec.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileReader {

	/**
	 * <p>
	 * List file items in ZIP file recursively;
	 * </p>
	 * @param zipFilePath zip file full path
	 * @param tempDir temporary directory, default to the same directory as zipFilePath;
	 * @throws Exception
	 */
	public static void unzipList(String zipFilePath, String tempDir) throws Exception{
		path = zipFilePath;
		pathDir = tempDir;
		
		File zipFile = new File(path);
		
		if(!zipFile.exists()){
			throw new FileNotFoundException(String.format("File %s not found.", zipFilePath));
		}
		if(null == tempDir){
			tempDir = zipFile.getParent();
		}
		File pathDirectory = new File(pathDir);
		if(!pathDirectory.exists()){
			pathDirectory.mkdirs();
		}
		
		extractZipFile(zipFile);
	}
	
	private static void extractZipFile(File path) throws Exception{
		int bufferSize = 2048;
		int count = 0;
//		out.printf("----------------zip file: %s\n", path);
		FileInputStream fis = new FileInputStream(path);
		
		try(ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));){
			ZipEntry entry;
			FileOutputStream fos = null;
			File tmpFile = null;
			byte[] data = null;
			while (null != (entry = zis.getNextEntry())) {
				//			out.println(entry.getName());
				if (!isZipFile(entry.getName())) {
					continue;
				}
				//				bufferSize = entry.getSize();
				count = 0;
				data = new byte[bufferSize];
				//			tmpFile = File.createTempFile(entry.getName(), "tmp", pathDirectory);
				tmpFile = new File(String.format("%s%s%s", pathDir, entry.getName().replaceAll("/", "_"),
						System.currentTimeMillis()));
				fos = new FileOutputStream(tmpFile);
				//				ByteOutputStream ois = new ByteArrayOutputStream();
				while (-1 != (count = zis.read(data, 0, bufferSize))) {
					fos.write(data, 0, count);
				}
				if (null != fos) {
					fos.close();
				}

				zis.closeEntry();
				extractZipFile(tmpFile);
			} 
		}
	}
	
	static boolean isZipFile(String name){
//		return name.endsWith(".jar") || name.endsWith(".war");
		return name.endsWith(".war");
	}
	
	static String pathDir;
	static String path;
//	static final String pathDir = "E:/Users/MEC/Music/2015-11-27_Season/tmp/";
//	static final String path = "E:/Users/MEC/Music/2015-11-27_Season/CE.ear";
//	private static final PrintStream out = System.out;
}
