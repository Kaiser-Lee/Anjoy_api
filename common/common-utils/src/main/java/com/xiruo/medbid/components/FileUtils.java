package com.xiruo.medbid.components;

import com.xiruo.medbid.components.collection.CharStack;
import com.xiruo.medbid.components.collection.CollectionsUtils;
import com.xiruo.medbid.util.UtilConstants;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.Assert;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FileUtils {
	
	public static final int BUFFER_SIZE = 4096;




	public static List<File> getFileList(String path){
		File file = new File(path);		
		return getFileList(file);
	}
	
	public static List<File> getFileList(File file){
		File[] files = file.listFiles();
		if(files == null || files.length < 1){
			return null;
		}
		List<File> fileList = new ArrayList<File>();
		for(File f : files){
			if(!f.isDirectory()){
				fileList.add(f);
			}else{
				fileList = (List<File>)CollectionsUtils.contact(fileList , getFileList(f.getPath()));
			}
		}
		return fileList;
	}
	
	public static StringBuilder readFileToString(String path){
		BufferedReader reader = null;
		StringBuilder sb = null; 
		try{
			reader = new BufferedReader(new FileReader(path));
			sb = new StringBuilder();
			int c;
			c = reader.read();			
			while(c!=-1){
				sb.append((char)c);
				c = reader.read();				
			}	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(reader);
		}
		return sb;
	}
	


	public static StringBuilder readFileToString(String path, String encoding){
		BufferedReader reader = null;
		StringBuilder sb = null; 
		try{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),encoding));
			sb = new StringBuilder();
			int c;
			c = reader.read();			
			while(c!=-1){
				sb.append((char)c);
				c = reader.read();				
			}	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(reader);
		}
		return sb;
	}
	
	public static void saveStringToFile(String path, String content){
		makeDir(path);
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(writer);
		}
	}
	
	public static void saveStringToFile(String path, StringBuilder content){
		saveStringToFile(path, content.toString());
	}
	
	public static void saveStringToFile(String path, String content, String encoding){
		makeDir(path);
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoding));
			writer.write(content);	
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(writer);
		}
	}
	
	public static void saveStringToFile(String path, StringBuilder content, String encoding){
		saveStringToFile(path, content.toString(), encoding);
	}


	public static boolean deleteFileOrFolder(String path){
		return deleteFileOrFolder(new File(path));
	}
	
	public static boolean deleteFileOrFolder(File file){
		if(!file.exists()){
			throw new IllegalArgumentException("file not found");
		}
		File[] childs = file.listFiles();
		if(childs == null || childs.length <1){//element
			
		}else{//not element
			for(File child : childs){
				deleteFileOrFolder(child);
			}
		}
		return file.delete();
	}
	
	public static String getExtensionName(String filePath){
		if(StringUtils.isBlank(filePath)){
			return StringUtils.EMPTY;
		}
		int lastDotPos = filePath.lastIndexOf(".");
		int lastPathPos = filePath.lastIndexOf(File.separator);
		if(lastPathPos == -1){
			lastPathPos = filePath.lastIndexOf("/");
		}
		if(lastDotPos != -1){
			if(lastPathPos > lastDotPos){
				return  StringUtils.EMPTY;
			}else{
				return filePath.substring(lastDotPos + 1);
			}
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	public static String getExtensionName(File file){
		return getExtensionName(file.getName());
	}
	
	public static String getFileName(String filePath){
		if(StringUtils.isBlank(filePath)){
			return StringUtils.EMPTY;
		}
		CharStack stack = new CharStack();
		stack.pushString(filePath);
		return stack.popUntil(File.separatorChar, '/');
	}
	
	public static boolean makeDir(String path){
		File file = new File(path);
		if(file.isDirectory()){
			
		}else{
			file = file.getParentFile();
		}
		if(!file.exists()){
			return file.mkdirs();
		}
		return false;
	}
	
	public static void copy(File sourceFile, String toPath){
		copy(sourceFile.getPath(), toPath);
	}
	
	public static void copy(String fromPath, String toPath){
		makeDir(toPath);
		try{
			InputStream is = new BufferedInputStream(new FileInputStream(fromPath));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(toPath));
			copy(is, os);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void move(String fromPath, String toPath){
		copy(fromPath, toPath);
		FileUtils.deleteFileOrFolder(fromPath);
	}
	
	public static void move(File sourceFile, String toPath){
		move(sourceFile.getPath(), toPath);
	}


	public static int copy(File in, File out) throws IOException {
		return copy(new BufferedInputStream(new FileInputStream(in)),
		   new BufferedOutputStream(new FileOutputStream(out)));
	}


	public static void copy(byte[] in, File out) throws IOException {
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		copy(inStream, outStream);
	}


	public static byte[] copyToByteArray(File in) throws IOException {
		return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
	}


	public static int copy(InputStream in, OutputStream out) throws IOException {
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}


	public static void copy(byte[] in, OutputStream out) throws IOException {
		try {
			out.write(in);
		}
		finally {
			IOUtils.closeQuietly(out);
		}
	}


	public static byte[] copyToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}


	public static int copy(Reader in, Writer out) throws IOException {
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}


	public static void copy(String in, Writer out) throws IOException {
		try {
			out.write(in);
		}
		finally {
			IOUtils.closeQuietly(out);
		}
	}


	public static String copyToString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}
	
	public static List<File> getClassFiles(String parent, String packageDesc){
		Assert.hasText(packageDesc);
		packageDesc = packageDesc.replaceAll(".", File.separator);
		if(packageDesc.endsWith("*")){
			packageDesc = packageDesc.replace("*", "");
		}
		return FileUtils.getFileList(packageDesc);
	}
	
	public static long getAudioDuration(File file) throws IOException {  
        long duration = -1;  
        int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };  
        RandomAccessFile randomAccessFile = null;  
        try {  
            randomAccessFile = new RandomAccessFile(file, "rw");  
            long length = file.length();//文件的长度  
            int pos = 6;//设置初始位置  
            int frameCount = 0;//初始帧数  
            int packedPos = -1;  
            byte[] datas = new byte[1];//初始数据值  
            while (pos <= length) {  
                randomAccessFile.seek(pos);  
                if (randomAccessFile.read(datas, 0, 1) != 1) {  
                    duration = length > 0 ? ((length - 6) / 650) : 0;  
                    break;  
                }  
                packedPos = (datas[0] >> 3) & 0x0F;  
                pos += packedSize[packedPos] + 1;  
                frameCount++;  
            }  
            duration += frameCount * 20;//帧数*20  
        } finally {  
            if (randomAccessFile != null) {  
                randomAccessFile.close();  
            }  
        }  
        return duration;  
    }


	public static InputStream bufferedImageToInputStream(BufferedImage bi, String extension) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ImageOutputStream imOut = null;
		try {
			imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(bi, extension, imOut);
			return new ByteArrayInputStream(bs.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void savePic(InputStream inputStream, String fileName) {

		OutputStream os = null;
		try {
			String path = "D:\\testFile\\";
			// 2、保存到临时文件
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流保存到本地文件

			File tempFile = new File(path);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
			// 开始读取
			while ((len = inputStream.read(bs)) != -1) {
				os.write(bs, 0, len);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 完毕，关闭所有链接
			try {
				os.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过原图创建压缩图
	 * @param file
	 * @return
	 */
	public static InputStream createCompressImage(File file)
	{
		//先将存在CMYK模式的图片转RGB模式。
		InputStream sourceImgFile = cmykToRGB(file);
		try {
			Thumbnails.Builder builder = Thumbnails.of(sourceImgFile);

			//图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
			builder.scale(1f);
			if(sourceImgFile.available()>=1024*1024*4){
				builder.outputQuality(0.1d);
			}else  if(sourceImgFile.available()>=1024*1024*3){
				builder.outputQuality(0.2d);
			}else  if(sourceImgFile.available()>=1024*1024*2){
				builder.outputQuality(0.3d);
			}else if(sourceImgFile.available()>=1024*500){
				builder.outputQuality(0.7d);
			}else if(sourceImgFile.available()>=1024*300){
				builder.outputQuality(0.8d);
			}else if(sourceImgFile.available()>=1024*200)  {
				builder.outputQuality(0.9d);
			}else {
				//少于200KB不压缩
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			builder.outputFormat("jpg").toOutputStream(baos);
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sourceImgFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}




	/**
	 * 通过原图创建预览图
	 * @param file
	 * @return
	 */
	public static InputStream createPreviewImage(File file) {
		//先将存在CMYK模式的图片转RGB模式。
		InputStream sourceImgFile = cmykToRGB(file);
		try {

			//图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
			BufferedImage sourceBuffImage = ImageIO.read(sourceImgFile);
			int sWidth = sourceBuffImage.getWidth();
			int sHeight = sourceBuffImage.getHeight();
			int tWidth;
			int tHeight;
			Thumbnails.Builder builder = Thumbnails.of(sourceBuffImage);
			if(sWidth < UtilConstants.UPLOAD_PREVIEW_WIDTH
					&& sHeight < UtilConstants.UPLOAD_PREVIEW_HEIGHT) {
				tWidth = sWidth;
				tHeight = sHeight;
			} else if(sWidth > sHeight) {
				tWidth = UtilConstants.UPLOAD_PREVIEW_WIDTH;
				tHeight = UtilConstants.UPLOAD_PREVIEW_WIDTH * sHeight / sWidth;
			} else {
				tHeight = UtilConstants.UPLOAD_PREVIEW_HEIGHT;
				tWidth = UtilConstants.UPLOAD_PREVIEW_HEIGHT * sWidth / sHeight;
			}
			sourceBuffImage.flush();
			builder.size(tWidth, tHeight);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			builder.outputFormat("jpg").toOutputStream(baos);
			// 输出缩略图
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sourceImgFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 通过文件，cmyk类型转RGB
	 * @param file
	 * @return
	 */
	public static InputStream cmykToRGB(File file) {

		InputStream sourceImgFile = null;
		try {
			ImageInputStream input = ImageIO.createImageInputStream(file);
			Iterator readers = ImageIO.getImageReaders(input);
			if (readers == null || !readers.hasNext()) {
				throw new RuntimeException("1 No ImageReaders found");
			}
			ImageReader reader = (ImageReader) readers.next();
			reader.setInput(input);
			String format = reader.getFormatName();
			BufferedImage image;

			//if ( "JPEG".equalsIgnoreCase(format) ||"JPG".equalsIgnoreCase(format) ) {   //只转JPEG、JPG格式图片
			try {
				// 尝试读取图片 (包括颜色的转换).
				image = reader.read(0); //RGB
			} catch (IIOException e) {
				// 读取Raster (没有颜色的转换).
				Raster raster = reader.readRaster(0, null);//CMYK
				image = createJPEG4(raster);
			}
			image.getGraphics().drawImage(image, 0, 0, null);
			//BufferedImage 转 InputStream
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageOutputStream imageOutput = ImageIO.createImageOutputStream(byteArrayOutputStream);
			ImageIO.write(image, "jpg", imageOutput);
			sourceImgFile = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			//}
		}catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					sourceImgFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return sourceImgFile;
	}

	private static BufferedImage createJPEG4(Raster raster) {
		int w = raster.getWidth();
		int h = raster.getHeight();
		byte[] rgb = new byte[w * h * 3];
		//彩色空间转换
		float[] Y = raster.getSamples(0, 0, w, h, 0, (float[]) null);
		float[] Cb = raster.getSamples(0, 0, w, h, 1, (float[]) null);
		float[] Cr = raster.getSamples(0, 0, w, h, 2, (float[]) null);
		float[] K = raster.getSamples(0, 0, w, h, 3, (float[]) null);
		for (int i = 0, imax = Y.length, base = 0; i < imax; i++, base += 3) {
			float k = 220 - K[i], y = 255 - Y[i], cb = 255 - Cb[i],
					cr = 255 - Cr[i];

			double val = y + 1.402 * (cr - 128) - k;
			val = (val - 128) * .65f + 128;
			rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
					: (byte) (val + 0.5);

			val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
			val = (val - 128) * .65f + 128;
			rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
					: (byte) (val + 0.5);

			val = y + 1.772 * (cb - 128) - k;
			val = (val - 128) * .65f + 128;
			rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
					: (byte) (val + 0.5);
		}
		raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
		ColorModel cm = new ComponentColorModel(cs, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		return new BufferedImage(cm, (WritableRaster) raster, true, null);
	}
}