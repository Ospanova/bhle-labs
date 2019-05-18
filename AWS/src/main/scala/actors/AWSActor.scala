package actors

import akka.actor.{Actor, Props}
import akka.http.scaladsl.model.ErrorInfo

object AWSActor {

    def props(bucketName: String, AWSService: AWSService) = Props(new AWSActor(bucketName, AWSService))

    case class Create(pathPrefix: String, path: String, bucketName: String)

    case class Get(pathPrefix: String, path: String, bucketName: String)

    case class Upload(prefixPathIn: String, prefixPathOut: String, bucketName: String)

    case class Download (prefixPathIn: String, prefixPathOut: String, bucketName: String)
}

class AWSActor(bucketName: String, AWSService: AWSService) extends Actor {
    import AWSActor._
    override def receive: Receive = {
        case msg: Create =>
            println(s"Download request with path ")
            sender() ! AWSService.createFile(msg.pathPrefix, msg.path, msg.bucketName)

        case msg: Get =>
            sender() ! AWSService.getFile(msg.pathPrefix, msg.path, msg.bucketName)
        case msg: Upload =>
            sender() ! AWSService.uploadFiles(msg.prefixPathIn,msg.prefixPathOut, msg.bucketName)
        case msg: Download =>
            sender() ! AWSService.downloadFiles(msg.prefixPathIn, msg.prefixPathOut,msg.bucketName)
    }

}