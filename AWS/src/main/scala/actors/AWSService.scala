package actors

import java.io.File

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.model.{GetObjectRequest, ListObjectsRequest, ObjectListing}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import models.Message
import org.slf4j.LoggerFactory

case class AWSService () {
    val log = LoggerFactory.getLogger("Boot")

    val awsCreds = new BasicAWSCredentials(
        "AKIA3TQLBWXTTKY6U4CW",
        "+FywyYVNmsjjdPZXFuZ0f0vEpo8As4O7tdB0F9ox")


    // Frankfurt client
    val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard
      .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
      .withRegion(Regions.EU_CENTRAL_1)
      .build

    // check if bucket exists


    def createFile(prefixPath: String, path: String, bucketName: String ): Message = {
        log.info(s"Upload file request with path: ${prefixPath +path}")
        val file = new File(prefixPath + path)
        if(!file.exists())
            Message("File not found")
        else{
            s3Client.putObject(bucketName, path, new File(prefixPath + path))
            Message("Uploaded to aws")
        }
    }
    def getFile (prefixPath: String, path: String, bucketName: String): Message = {
        if(s3Client.doesObjectExist(bucketName, path)){
            getFromAws(prefixPath, path, bucketName)
            Message("File downloaded from aws")
        } else{
            Message("File not found")
        }
    }
    def getFromAws(prefixPath: String, path: String, bucketName: String): Unit = {
        val wholePath = (prefixPath + path).substring(0, (prefixPath + path).lastIndexOf('/'))
        val newDir = new File(wholePath)
        newDir.mkdir()
        s3Client.getObject(new GetObjectRequest(bucketName, path),
            new File(prefixPath + path))
    }
    def uploadFiles(prefixPathIn: String, prefixPathOut: String, bucketName: String): Message = {
        log.info("Upload from out request")
        log.info(s"prefixIn is $prefixPathIn")
        log.info(s"Out is $prefixPathOut")
        log.info(s"bucket is $bucketName")


        upload(prefixPathIn, prefixPathOut,"", new File(prefixPathOut), bucketName)
        Message("SUCCESS by uploading!")
    }
    def upload(prefixPathIn: String, prefixPathOut: String, curPath: String, file: File, bucketName: String): Unit = {
        val wholePath = prefixPathOut + curPath
        if (!s3Client.doesObjectExist(bucketName, curPath) && curPath != "" && file.isFile) {
            s3Client.putObject(bucketName, curPath, file)
            log.info(s"curpath is $curPath")
        }

        if (file.isDirectory) {
            file.listFiles.foreach(to_file => {
                val suff = to_file.getPath().substring(to_file.getPath.lastIndexOf('/') + 1)
                upload(prefixPathIn, prefixPathOut,curPath + "/" + suff, to_file, bucketName)
            })
        }
    }
    def downloadFiles (prefixPathOut: String, prefixPathIn: String, bucketName: String): Message = {
        val listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
        val list: ObjectListing = s3Client.listObjects(bucketName)
        list.getObjectSummaries().forEach(key => downloadFromAws(prefixPathIn, prefixPathOut,key.getKey(),  bucketName))
        Message("Download")
    }
    def downloadFromAws(prefixPathOut: String, prefixPathIn: String, path: String,  bucketName: String): Unit = {
        val wholePath = (prefixPathIn + path).substring(0, (prefixPathIn + path).lastIndexOf('/'))
        val newDir = new File(wholePath)
        newDir.mkdir()
        s3Client.getObject(new GetObjectRequest(bucketName, path),
            new File(prefixPathIn + path))
    }
}
