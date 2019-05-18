sealed trait IntList {

    // sum of members of IntList

    def size(list: IntList, n: Int = 0): Int = list match {
        case End => n
        case Node(head, tail) => size(tail, n + 1)
    }
    def length: Int = {

        (size(this))
    }
    def product : Long = {
        def pr (list: IntList, sum: Long = 1): Long = list match  {
            case End => sum
            case Node(head, tail) => pr(tail, head*sum)
        }
        pr(this)
    }
    def double : IntList = {
        def d (list: IntList) = list match {
            case End => End
            case Node(head, tail) => Node(head * 2, tail.double)
        }
        d(this)
    }
    def map(f: Int => Int): IntList =  {
        def d (list: IntList) = list match {
            case End => End
            case Node(head, tail) => Node(f(head), tail.map(f))
        }
        d(this)
    }

}
case object  End extends IntList
case class Node(head: Int, tail: IntList) extends IntList
