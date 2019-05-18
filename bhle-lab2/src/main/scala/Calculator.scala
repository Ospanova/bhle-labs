sealed trait Calculator {

}
case class Succeed(n: Int) extends Calculator
case class  Fail (s: String)extends Calculator




