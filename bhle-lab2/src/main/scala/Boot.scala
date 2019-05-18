object Boot extends App {


    def r : Calculator = Succeed(4)
    r match  {
        case Succeed(v)=> v
        case Fail(v)=> v
    }
    val intList = Node(1, Node(2, Node(3, Node(4, End()))))
//
//    println(intList.length)
//    println(intList.product)
//    println(intList.double)

    println(intList.map(x => x * 3))
    //assert(intList.map(x => 5 - x) == Node(5 - 1, Node(5 - 2, Node(5 - 3, Node(5 - 4, End)))))
}
