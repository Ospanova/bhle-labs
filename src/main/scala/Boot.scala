case class Film( name: String,
                 yearOfRelease: Int,
                 imdbRating: Double)
case class Director( firstName: String,
                     lastName: String,
                     yearOfBirth: Int,
                     films: Seq[Film])

object Boot extends App{

    val memento = new Film("Memento", 2000, 8.5)
    val darkKnight = new Film("Dark Knight", 2008, 9.0)
    val inception = new Film("Inception", 2010, 8.8)
    val highPlainsDrifter = new Film("High Plains Drifter", 1973, 7.7)
    val outlawJoseyWales = new Film("The Outlaw Josey Wales", 1976, 7.9)
    val unforgiven = new Film("Unforgiven", 1992, 8.3)
    val granTorino = new Film("Gran Torino", 2008, 8.2)
    val invictus = new Film("Invictus", 2009, 7.4)
    val predator = new Film("Predator", 1987, 7.9)
    val dieHard = new Film("Die Hard", 1988, 8.3)
    val huntForRedOctober = new Film("The Hunt for Red October", 1990, 7.6)
    val thomasCrownAffair = new Film("The Thomas Crown Affair", 1999, 6.8)
    val eastwood = new Director("Clint", "Eastwood", 1930,
        Seq(highPlainsDrifter, outlawJoseyWales, unforgiven, granTorino, invictus))
    val mcTiernan = new Director("John", "McTiernan", 1951,
        Seq(predator, dieHard, huntForRedOctober, thomasCrownAffair))
    val nolan = new Director("Christopher", "Nolan", 1970,
        Seq(memento, darkKnight, inception))
    val someGuy = new Director("Just", "Some Guy", 1990,
        Seq())
    val directors = Seq(eastwood, mcTiernan, nolan, someGuy)

    def filterByCountOfFilms(numberOfFilms: Int) = directors.filter(i => i.films.size > numberOfFilms)

    def filterByYear(year: Int) = directors.filter(x => x.yearOfBirth < year)

    def comboFilter (numberOfFilms: Int, year: Int) = filterByYear(year).intersect(filterByCountOfFilms(numberOfFilms))

    def sortByAge(ascending: Boolean): Unit = {
        if (ascending)
            directors.sortWith(_.yearOfBirth < _.yearOfBirth)
        else
            directors.sortWith(_.yearOfBirth >  _.yearOfBirth)
    }

    def nolanFilms = nolan.films.map(x => x.name)
    println(nolanFilms)
    def allFilms = directors.flatMap(x => x.films.map(_.name))
    println(allFilms)
    def earliestFilm = mcTiernan.films.sortWith(_.yearOfRelease < _.yearOfRelease).headOption
    println(earliestFilm)
    def allSortedFilms = directors.flatMap(x => x.films).sortWith(_.imdbRating < _.imdbRating)
    def avgScore: Double = {
        var sum = 0.0
        var cnt = 0
        directors.foreach(x => x.films.foreach(i => {
            sum = sum + i.imdbRating
            cnt = cnt + 1
        }))
        sum/(cnt*1.0)

//        val f = directors.flatMap(d => d.films).map(_.imdbRating)
//        f.sum / f.length * 1.0
    }
    def printAllFilms = directors.foreach(x => x.films.foreach(i => println(s"Tonight only! ${i.name} by ${x.lastName}!")))
    def earliestFilmOfAll = directors.flatMap(x => x.films.sortWith(_.yearOfRelease > _.yearOfRelease)).headOption
    println(earliestFilmOfAll)

    def divide (a: Int, b: Int ): Option[Int] = {
        b match {
            case 0 => None
            case _ => Some(a/b)
        }
    }
    Calculator("44", "+", "4").printing()
    Calculator("44", "-", "4").printing()
    Calculator("44", "*", "4").printing()
    Calculator("44", "/", "4").printing()
    Calculator("44", "+", "((").printing()


}

case class Calculator (operand1: String, operator: String, operand2: String) {
    def stringToInt (str: String) : Option[Int]  = {
        if(str matches "\\d+")
            Some(str.toInt)
        else
            None
    }
    def value1 = stringToInt(operand1)
    def value2 = stringToInt(operand2)

    def calculator () = {
        if (operator.equals("+"))
            this.sum()
        else if (operator.equals("-"))
            this.sub()
        else if (operator.equals("*"))
            this.multiply()
        else
            this.divide()
    }
    def calculator2 (a: Int, b: Int) = {
        operator match {
            case "+" => Some(a + b)
            case "-" => Some(a - b)
            case "*" =>  Some(a * b)
            case "/" => Boot.divide(a, b)
            case _ => None
        }
    }

    def printing (): Unit = {
        value1 match {
            case None => println("Invalid input")
            case Some(value) => {
                value2 match
                {
                    case Some(k) => {
                        calculator2(value, k) match {
                            case Some(x) => println(x)
                            case None => println("Error by dividing to zero")
                        }
                    }
                    case None => println("Invalid input")
                }
            }
        }
    }
    def sum (): Unit = {
        value1 match  {
            case None => println("Error! Incorrect input")
            case Some(a) => {
                //println(a)
                value2 match {
                    case None => println("Error! Incorrect input")
                    case Some(b) => println(a + b)
                }
            }
        }
    }
    def sub(): Unit = {
        value1 match  {
            case None => println("Error! Incorrect input")
            case Some(a) => {
                //println(a)
                value2 match {
                    case None => println("Error! Incorrect input")
                    case Some(b) => println(a - b)
                }
            }
        }
    }
    def multiply() = {
        value1 match  {
            case None => println("Error! Incorrect input")
            case Some(a) => {
                value2 match {
                    case None => println("Error! Incorrect input")
                    case Some(b) => println(a*b)
                }
            }
        }
    }
    def divide() = {
        value1 match  {
            case None => println("Error! To zero")
            case Some(a) => {
                   value2 match {
                    case None => println("Error! Incorrect input")
                    case Some(b) => {
                        if (b == 0)
                            println("ERROR! Can't divide to zero!")
                        else
                            println(a/b)
                    }
                }
            }
            case _ => println("Incorrect Input")
        }
    }
}