package com.coracle.dms.xweb.controller;

import com.coracle.common.support.mongodb.MongoFileUpload;
import com.coracle.dms.dto.DmsAttachmentDto;
import com.coracle.dms.po.DmsAttachmentRelation;
import com.coracle.dms.po.DmsCommonAttachFile;
import com.coracle.dms.service.DmsAttachmentRelationService;
import com.coracle.dms.service.DmsCommonAttachFileService;
import com.coracle.dms.xweb.common.constants.ManageConstants;
import com.coracle.dms.xweb.common.session.LoginManager;
import com.coracle.dms.xweb.common.util.PoDefaultValueGenerator;
import com.coracle.yk.base.vo.BaseRequestParamVo;
import com.coracle.yk.base.vo.JsonResult;
import com.coracle.yk.xdatabase.base.mybatis.util.PageHelper;
import com.coracle.yk.xframework.common.exception.runtime.ControllerException;
import com.coracle.yk.xframework.common.exception.runtime.ParamException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.xiruo.medbid.components.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
@Scope("request")
@RequestMapping(value = "/api/v1/dms/commonAttachUpload")
@Api(description = "Dms附件上传接口")
public class DmsAttachmentFileController extends BaseController {
    private Log log = LogFactory.getLog(DmsAttachmentFileController.class);
    @Autowired
    private DmsCommonAttachFileService dmsCommonAttachFileService;
    @Autowired
    private DmsAttachmentRelationService dmsAttachmentRelationService;

    @ResponseBody
    @RequestMapping(value = "/mongoUpload", method = RequestMethod.POST)
    @ApiOperation(value = "附件上传",response = JsonResult.class)
    public Object mongoUpload(HttpServletRequest request,
                              @ApiParam("上传文件类型（1-渠道+门店；2-渠道联系人+门店店员）（当为这4种类型必须，其他非必须）")@RequestParam(value = "dealType",required = false) Integer dealType,
                              @ApiParam("对应实体Id（非必须）")@RequestParam(value = "id",required = false) Long id) {
        try {
            log.info("mongoUpload start");
            /**
             * 1、上传文件写入磁盘, 并用list记录所有上传文件名
             */
            List<String> sourceFileIds = new ArrayList<>();
            List<String> compressIds = new ArrayList<>();
            List<String> previewIds = new ArrayList<>();
            List<String> uploadNameList = new ArrayList<String>();
            List<Long> fileSizes = new ArrayList<Long>();
            //创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断 request 是否有文件上传,即多部分请求
            if(multipartResolver.isMultipart(request)){
                System.err.println("多部分请求开始");
                int index = 0;
                //转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                MultiValueMap<String, MultipartFile> multiFileMap = multiRequest.getMultiFileMap();
                for(List<MultipartFile> mfiles : multiFileMap.values()) {
                    for (int i = 0; i < mfiles.size(); i++) {
                        if (mfiles != null) {
                            //取得当前上传文件的文件名称
                            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                            String originalFileName = mfiles.get(i).getOriginalFilename();
                            if (BlankUtil.isNotEmpty(originalFileName)) {
                                //检查上传文件类型是否合法
                                checkAllowFileExtension(originalFileName);
                            }
                        }
                    }
                }

                for(List<MultipartFile> mfiles : multiFileMap.values()) {
                    for (int i = 0; i < mfiles.size(); i++) {
                        if (mfiles != null) {
                            //取得当前上传文件的文件名称
                            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                            String originalFileName = mfiles.get(i).getOriginalFilename();

                            if (BlankUtil.isNotEmpty(originalFileName)) {
                                uploadNameList.add(originalFileName);
                                fileSizes.add(mfiles.get(i).getSize());
                                GridFSInputFile sourceFile = MongoFileUpload.saveFile(mfiles.get(i).getInputStream(), originalFileName);
                                sourceFileIds.add(sourceFile.getId().toString());
                                if(ManageConstants.ALLOW_PREVIEW_TYPE.indexOf("," + originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase() + ",") >= 0) {

                                    //将MultipartFile转File
                                    File file = null;
                                    try {
                                        file=File.createTempFile("tmp", null);
                                        mfiles.get(i).transferTo(file);
                                        file.deleteOnExit();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    GridFSInputFile compressFile = MongoFileUpload.saveFile(FileUtils.createCompressImage(file), "compress_"+originalFileName);
                                    GridFSInputFile previewFile = MongoFileUpload.saveFile(FileUtils.createPreviewImage(file), "preview_"+originalFileName);
                                    compressIds.add(compressFile.getId().toString());
                                    previewIds.add(previewFile.getId().toString());
                                }else {//兼容不同类型文件上传对应的索引位置
                                    compressIds.add("");
                                    previewIds.add("");
                                }
                            }
                        }
                    }
                }
                /**
                 * 2、将文件信息存入数据库
                 */
                final String fileDownloadUrl = "/api/v1/dms/commonAttachUpload/getFile?id=";
                List<DmsCommonAttachFile> list = new ArrayList<DmsCommonAttachFile>();
                final int fileCount = sourceFileIds.size();
                String uploadFileName;
                DmsCommonAttachFile upload;
                for(int i = 0; i < fileCount; i++) {
                    uploadFileName = uploadNameList.get(i);
                    index = uploadNameList.get(i).lastIndexOf(".");
                    upload = new DmsCommonAttachFile();
                    PoDefaultValueGenerator.putCreateDefault(upload);
                    upload.setMd5(sourceFileIds.get(i));
                    upload.setFileName(uploadFileName);
                    upload.setOrginalName(uploadFileName);
                    upload.setExtension(uploadFileName.substring(index + 1).toLowerCase());
                    upload.setSize(fileSizes.get(i));
                    upload.setDownloadCount(0);
                    //白名单排除其他文件类型, 只有图片类型才可以预览和压缩
                    if(ManageConstants.ALLOW_PREVIEW_TYPE.indexOf("," + uploadFileName.substring(index + 1).toLowerCase() + ",") >= 0) {
                        upload.setFilePreviewPath(fileDownloadUrl + previewIds.get(i));
                        upload.setFileCompressPath(fileDownloadUrl + compressIds.get(i));
                    }
                    //记录附件表
                    DmsCommonAttachFile dmsCommonAttachFile = dmsCommonAttachFileService.insertByRePrimaryKey(upload);
                    //upload.setId(uploadId);
                    list.add(dmsCommonAttachFile);
                }
                /**
                 * 3、添加文件关联,需要做处理,当不是已知4种类型操作，无需操作
                 */
                if (dealType!=null && dealType!=0 && id!=null&& id!=0) {
                    for (int i = 0; i < list.size(); i++) {
                        DmsAttachmentRelation dmsAttachmentRelation = new DmsAttachmentRelation();
                        PoDefaultValueGenerator.putDefaultValue(dmsAttachmentRelation);
                        dmsAttachmentRelation.setId(null);
                        dmsAttachmentRelation.setCreatedBy(LoginManager.getCurrentUserId());
                        dmsAttachmentRelation.setLastUpdatedBy(LoginManager.getCurrentUserId());
                        dmsAttachmentRelation.setRelatedTableType(dealType);
                        dmsAttachmentRelation.setRelatedTableId(id);
                        dmsAttachmentRelation.setAttachId(list.get(i).getId());
                        dmsAttachmentRelation.setDownloadNum(0L);
                        dmsAttachmentRelationService.insert(dmsAttachmentRelation);
                    }
                }
                if(list.size() == 1) {
                    return toResult(list.get(0));
                } else {
                    return toResult(list);
                }
            } else {
                throw new ControllerException("非multipart/form-data请求，上传文件失败");
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new ControllerException(e, "上传附件异常");
        }
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    @ApiOperation(value = "附件查看")
    public void getFile(@ApiParam("附件id")@RequestParam("id") String id, HttpServletResponse response) {
        if(BlankUtil.isEmpty(id)) {
            throw new ParamException("参数不能为空");
        }
        DmsCommonAttachFile dmsCommonAttachFile = null;
        String md5Id = "";
        try {
            long fileTableId = Long.parseLong(id);
            dmsCommonAttachFile = dmsCommonAttachFileService.selectByPrimaryKey(fileTableId);
            if (BlankUtil.isNotEmpty(dmsCommonAttachFile)&&BlankUtil.isNotEmpty(dmsCommonAttachFile.getMd5())){
                md5Id = dmsCommonAttachFile.getMd5();
            }else {
                md5Id = id;
            }
        } catch (NumberFormatException e) {
            md5Id = id;
        }
        GridFSDBFile file = MongoFileUpload.getFileById(md5Id);
        if(file == null) {
            throw new ControllerException("文件不存在："+id);
        }
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            os.write(FileUtils.copyToByteArray(file.getInputStream()));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getFileDown", method = RequestMethod.GET)
    @ApiOperation(value = "附件下载")
    public void getFileDown(@ApiParam("附件id")@RequestParam("id") Long id, HttpServletResponse response) {
        if(BlankUtil.isEmpty(id)) {
            throw new ParamException("参数不能为空");
        }
        DmsCommonAttachFile dmsCommonAttachFile = dmsCommonAttachFileService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsCommonAttachFile)){
            throw new ControllerException("不存在ID为"+id+"的附件！");
        }
        GridFSDBFile file = MongoFileUpload.getFileById(dmsCommonAttachFile.getMd5());
        if(file == null) {
            throw new ControllerException("文件不存在："+dmsCommonAttachFile.getMd5());
        }
        //dmsCommonAttachFileService.updateByIdsSelectiveByAddCount(dmsCommonAttachFile);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new Date().getTime()+"_"+file.getFilename() + "\"");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            file.writeTo(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addFileDownCount", method = RequestMethod.GET)
    @ApiOperation(value = "附件下载次数统计")
    public Object addFileDownCount(@ApiParam("附件id")@RequestParam("id") Long id){
        if(BlankUtil.isEmpty(id)) {
            throw new ParamException("参数不能为空");
        }
        DmsCommonAttachFile dmsCommonAttachFile = dmsCommonAttachFileService.selectByPrimaryKey(id);
        if (BlankUtil.isEmpty(dmsCommonAttachFile)){
            throw new ControllerException("不存在ID为"+id+"的附件！");
        }
        dmsCommonAttachFileService.updateByIdsSelectiveByAddCount(dmsCommonAttachFile);
        int num = dmsCommonAttachFile.getDownloadCount()==null?1:dmsCommonAttachFile.getDownloadCount()+1;
        return toResult(num);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除附件", response = JsonResult.class)
    public Object delete(@RequestBody BaseRequestParamVo baseRequestParamVo) {
        dmsCommonAttachFileService.updateByIdsSelectiveByDelete(baseRequestParamVo.getIds());
        return toResult();
    }

    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取列表",response = JsonResult.class)
    public Object getList(@ApiParam("上传文件类型（1-渠道+门店；2-渠道联系人+门店店员）")@RequestParam("dealType") Integer dealType,
                          @ApiParam("对应操作Id")@RequestParam("id") Long id,
                          @ApiParam("附件 分页页码")@RequestParam(value = "p",required = false) Integer p,
                          @ApiParam("附件每页条数")@RequestParam(value = "s",required = false) Integer s){
        DmsAttachmentDto dmsAttachmentDto = new DmsAttachmentDto();
        if (p==null||p<=0)p=1;
        if (s==null||s<=0)s=5;
        dmsAttachmentDto.setP(p);
        dmsAttachmentDto.setS(s);
        dmsAttachmentDto.setRelatedTableType(dealType);
        dmsAttachmentDto.setRelatedTableId(id);
        PageHelper.Page<DmsAttachmentDto> pageList=dmsAttachmentRelationService.getPageList(dmsAttachmentDto,LoginManager.getUserSession());
        return toResult(pageList, "操作成功");
    }

    /**
     * 检查上传文件类型是否合法
     * 仅支持jpg,png,bmp,gif,tif,zip,rar,xls,xlsx,doc,docx
     * @param fileName
     * @return
     */
    private boolean checkAllowFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1).toLowerCase();
        if(ManageConstants.NOT_ALLOW_UPLOAD_TYPE.indexOf("," + extension + ",") >= 0) {
            throw new ControllerException("非法的文件类型"+extension);
        } else {
            return true;
        }
    }
}
