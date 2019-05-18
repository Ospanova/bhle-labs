package mappin

import models.{Message, Path}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonMapping  extends DefaultJsonProtocol{
    implicit val pathFormat = jsonFormat1(Path)
    implicit val msgFormat= jsonFormat1(Message)

}
