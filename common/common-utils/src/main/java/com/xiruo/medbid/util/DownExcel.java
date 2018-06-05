package com.xiruo.medbid.util;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
/*
*
 * 生成应用的下载文件
 *
*/
@SuppressWarnings({"deprecation","unused"})
public class DownExcel {
    private static DownExcel downExcel=null;
    private DownExcel(){
    }
    public static DownExcel getInstall(){
        if(null==downExcel){
            synchronized(DownExcel.class){
                if(downExcel==null){
                    downExcel=new DownExcel();
                }
            }
        }
        return downExcel;
    }

    /**
     * excel 2003版本的导出方法 支持多个sheet导出 导出的文件后缀为.xls
     *
     * @param dataMap       要导出的数据
     * @param excelFilePath excel文件的存放位置
     * @param fileName      excel文件名字
     *
     * @return
     */
    public String exportXlsExcel(Map<String,List<String[]>> dataMap,String excelFilePath,String fileName){
        FileOutputStream fout=null;
        String fileLocal="";
        HSSFWorkbook wb = null;
        try{
            File file=new File(excelFilePath);
            if(!file.exists()){
                file.mkdirs();
            }
            // 第一步，创建一个webbook，对应一个Excel文件
            wb=new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet=null;
            List<String[]> dataList=null;

            // 创建两种单元格格式
            HSSFCellStyle style=wb.createCellStyle();
            // 创建两种字体
            Font f = wb.createFont();
            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 10);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBoldweight(Font.BOLDWEIGHT_BOLD);

            // 设置第一种单元格的样式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setFont(f);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd HH:mm:ss"));


            Set<String> keyTitle=dataMap.keySet();
            for(String title : keyTitle){
                sheet=wb.createSheet(title);
                dataList=dataMap.get(title);
                for(int i=0;null!=dataList&&i<dataList.size();i++){
                    // 生成第一行
                    HSSFRow row=sheet.createRow(i);
                    String[] arr=dataList.get(i);
                    for(int j=0;null!=arr&&j<arr.length;j++){
                        // 给这一行的第一列赋值
                        HSSFCell cell=row.createCell(j);
                        cell.setCellValue(arr[j]);
                        /*给单元格值加粗 cell.setCellStyle(style);*/
                    }
                }
            }


            // 第六步，将文件存到指定位置
            fileLocal=excelFilePath+"/"+fileName+".xls";
            fout=new FileOutputStream(fileLocal);
            wb.write(fout);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fout.close();
                if (wb != null) {
                	wb.close();
                }
            }catch(IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return fileLocal;
    }

    /**
     * 通过响应输出流实现文件下载
     *
     * @param response     响应的请求
     * @param fileLocal    文件的绝对路径 请用/斜杠表示路径
     * @param downloadName 自定义的文件名 ( 不要后缀),如果此值为空则使用时间日期做为默认的文件名
     * @param deleFile     下载完成后是否删除文件（true: 删除 , false：不删除）
     */
    public void downLoadFile(HttpServletResponse response, String fileLocal, String downloadName, boolean deleFile){
        InputStream in=null;
        OutputStream out=null;
        try{
            if(!"".equals(downloadName)){
                downloadName=downloadName+fileLocal.substring(fileLocal.lastIndexOf("."));
            }else{
                downloadName=fileLocal.substring(fileLocal.lastIndexOf("/")+1);
            }
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(downloadName,"UTF-8"));
            in=new FileInputStream(fileLocal);
            int len=0;
            byte buffer[]=new byte[1024];
            out=response.getOutputStream();
            while((len=in.read(buffer))>0){
                out.write(buffer,0,len);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(in!=null){
                try{
                    //
                    in.close();
                    if(deleFile){
                        Thread.sleep(1000l);
                        File file=new File(fileLocal);
                        file.delete();
                    }
                }catch(Exception e){
                }
            }
        }
    }


}