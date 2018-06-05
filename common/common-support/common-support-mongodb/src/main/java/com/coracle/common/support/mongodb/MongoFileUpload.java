package com.coracle.common.support.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by huangbaidong
 * 2017/4/21.
 */
public class MongoFileUpload {
    private static final Logger logger = LoggerFactory.getLogger(MongoFileUpload.class);

    public static GridFSInputFile saveFile(String collectionName, InputStream in, Object id, String fileName) {
        GridFS gridFS = MongoDBUtil.getInstance().getGridFS(MongoDBUtil.dbname);
        GridFSInputFile gridFSInputFile = gridFS.createFile(in, fileName);
        gridFSInputFile.setId(id);
        gridFSInputFile.save();
        return gridFSInputFile;
    }

    public static GridFSInputFile saveFile(String collectionName, InputStream in, String fileName) {
        GridFS gridFS = MongoDBUtil.getInstance().getGridFS(MongoDBUtil.dbname, collectionName);
        GridFSInputFile gridFSInputFile = gridFS.createFile(in,fileName);
        gridFSInputFile.save();
        return gridFSInputFile;
    }

    public static GridFSInputFile saveFile(InputStream in, String fileName) {
        GridFS gridFS = MongoDBUtil.getInstance().getGridFS(MongoDBUtil.dbname);
        GridFSInputFile gridFSInputFile = gridFS.createFile(in,fileName);
        gridFSInputFile.save();
        return gridFSInputFile;
    }

    public static GridFSDBFile getFileById(String collectionName, String id) {
        DBObject query  = new BasicDBObject("_id", new ObjectId(id));
        GridFS gridFS = MongoDBUtil.getInstance().getGridFS(MongoDBUtil.dbname, collectionName);
        GridFSDBFile file = gridFS.findOne(query);
        return file;
    }

    public static GridFSDBFile getFileById(String id) {
        DBObject query  = new BasicDBObject("_id", new ObjectId(id));
        GridFS gridFS = MongoDBUtil.getInstance().getGridFS(MongoDBUtil.dbname);
        GridFSDBFile file = gridFS.findOne(query);
        logger.info("MongoDB文件信息：dbName：{}, _id：{}", MongoDBUtil.dbname, id);
        return file;
    }

}
