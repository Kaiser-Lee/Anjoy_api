package com.coracle.common.support.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Created by huangbaidong
 * 2017/4/24.
 */
public class Test {

    public static void main( String args[] ){
        /*try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "192.16.8.116" , 27017 );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("mymongo");
            System.out.println("Connect to database successfully");

//            mongoDatabase.createCollection("user");
//            System.out.println("集合创建成功");

            MongoCollection<Document> collection = mongoDatabase.getCollection("user");

            Document document = new Document("title", "MongoDB").
                    append("description", "database").
                    append("likes", 100).
                    append("by", "Fly");
            List<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }*/
        MongoCollection<Document> collection = MongoDBUtil.getInstance().getCollection("mymongo", "user");
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> mongoCursor = documents.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }

//        GridFS gridFS = MongoDBUtil.getInstance().getGridFS("mymongo");
//        try {
//            GridFSInputFile gridFSInputFile = gridFS.createFile(new File("D:/song.mp3"));
//            DBObject object = new BasicDBObject();
//            object.put("business_type",1);
//            object.put("business_name", "image");
//            gridFSInputFile.setMetaData(object);
//            gridFSInputFile.save();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        GridFSDBFile gridFSDBFile = MongoFileUpload.getFileById("58fd6aae637b0c4f34aea548");
        System.out.println(gridFSDBFile);

//        DBObject query  = new BasicDBObject("_id", "58fd6aac637b0c4f34aea545");
//
//        GridFSDBFile file = gridFS.findOne(query);
//        System.out.println(file);
    }
}
