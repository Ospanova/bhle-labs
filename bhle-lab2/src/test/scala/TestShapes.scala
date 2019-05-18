
import org.scalatest.FunSuite

class TestShapes extends FunSuite{
    test("Rectangle") {
        assert(Rectangle(4, 3).getPerimeter() === 14)
    }
    test("Square") {
        assert(Square(4).getPerimeter() === 16)
    }
    test("DRAW"){
        assert(Draw(Circle(3)) === "A circle of radius 3cm")
    }
    val intList = Node(1, Node(2, Node(3, Node(4, End()))))

    test("IntList"){
        assert(intList.length === 4)
        assert(intList.tail.length === 3)
        assert(End().length === 0)
    }

    test("Product") {
        val intList = Node(1, Node(2, Node(3, Node(4, End()))))

        assert(intList.product === 1 * 2 * 3 * 4)
        assert(intList.tail.product === 2 * 3 * 4)
        assert(End().product === 1)

    }
    test ("Double") {
        val intList = Node(1, Node(2, Node(3, Node(4, End()))))

        assert(intList.double == Node(1 * 2, Node(2 * 2, Node(3 * 2, Node(4 * 2, End())))))
        assert(intList.tail.double == Node(4, Node(6, Node(8, End()))))
        assert(End().double == End)
    }
    test("MAP") {
        val intList = Node(1, Node(2, Node(3, Node(4, End()))))

        assert(intList.map(x => x * 3) == Node(1 * 3, Node(2 * 3, Node(3 * 3, Node(4 * 3, End())))))
        assert(intList.map(x => 5 - x) == Node(5 - 1, Node(5 - 2, Node(5 - 3, Node(5 - 4, End())))))
    }

}
