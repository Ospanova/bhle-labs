
import java.io.File

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.Bucket
import mappin.JsonMapping
import org.slf4j.LoggerFactory

import collection.JavaConverters._

object Boot extends App with JsonMapping {

    val log = LoggerFactory.getLogger("Boot")

    implicit val system = ActorSystem()
    val r = new  Router()
    implicit val materializer = ActorMaterializer()
    val bindingFuture = Http().bindAndHandle(r.mainRoute, "localhost", 8080)
    log.info("Listening on port 8080...")

}