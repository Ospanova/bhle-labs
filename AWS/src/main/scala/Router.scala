import actors.{AWSActor, AWSService}
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.{complete, get, parameters, path}
import akka.http.scaladsl.server.{ExceptionHandler, Route}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import models.{Message, Path => PathModel}
import akka.pattern.ask
import akka.util.Timeout
import mappin.JsonMapping
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.concurrent.duration._
import scala.util.{Failure, Success}


class Router (implicit val system: ActorSystem) extends JsonMapping {
    val aws = AWSService()
    implicit val timeout = Timeout(30.seconds)
    implicit val executionContext = system.dispatcher

    val prefixPath = "src/main/resources/s3/"
    val prefixPathOut= "src/main/resources/out/"
    val prefixPathIn = "src/main/resources/in/"
    val bucketName = "bhle-task1"
    val bucketName2 = "bhle-task2"

    val awsActor = system.actorOf(AWSActor.props("bhle-task1", aws), "awsActor1")
    val awsActor2 = system.actorOf(AWSActor.props("bhle-task2", aws), "awsActor2")


    def route1: Route =
        path("file") {
            post {

                entity(as[PathModel]) { pathModel =>
                    complete {
                        (awsActor ? AWSActor.Create(prefixPath, pathModel.path, bucketName)).mapTo[Message]
                    }
                }
            } ~
              get {
                  entity(as[PathModel]) { pathModel =>
                      complete((awsActor ? AWSActor.Get(prefixPath,pathModel.path, bucketName)).mapTo[Message])
                  }
              }
        }
    def route2: Route =
            path("out") {
                get {
                    complete((awsActor2 ? AWSActor.Upload(prefixPathIn, prefixPathOut, bucketName2)).mapTo[Message])
                }
            } ~
            path ("in") {
                get {

                    complete((awsActor2 ? AWSActor.Download(prefixPathIn, prefixPathOut,bucketName2)).mapTo[Message])
                }
            }

    def mainRoute = pathPrefix("aws") {
        concat(route1, route2)
    }
}
