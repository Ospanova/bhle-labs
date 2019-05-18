
sealed trait GenericList [A]{

    def length: Int = {
        def size(list: GenericList[A], n : Int = 0): Int = list match {
            case GenericEnd() => n
            case GNode(head, tail) => size(tail, n + 1)
        }
        size(this)

    }
    def map[B] (f: A => B) {
        def g (list: GenericList[A]): GenericList[A] = {
            case GenericEnd() => GenericEnd()
            case GNode(head, tail) => GNode(f(head), g(tail[A]))
        }
        g(this)
    }

}
case class GenericEnd[A]() extends GenericList[A]
case class GNode[A] (head: A, tail: GenericList[A]) extends GenericList[A]
