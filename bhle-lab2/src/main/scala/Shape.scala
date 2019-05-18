sealed trait Shape {
    def getNumber(): Int
    def getPerimeter(): Double
    def area(): Double

    override def toString: String
}
trait Rectangular  extends Shape {
    def lenght: Double
    def height: Double
    def name: String
    override def getNumber(): Int = 4

    override def getPerimeter(): Double = 2*(lenght + height)

    override def area(): Double = lenght * height

    override def toString: String = s"A $name of width ${lenght.toInt}cm and height ${height.toInt}cm"
}
case class Circle(radius: Double) extends Shape {
    override def getNumber()  = 1

    override def getPerimeter()  = 2.0 * math.Pi * radius

    override def area()= math.Pi * radius * radius

    override def toString: String = s"A circle of radius ${radius.toInt}cm"
}
