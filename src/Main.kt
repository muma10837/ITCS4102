/**
 * The main file has the vast majority of the IO and control structures. For IO you have readLine and println().
 * readLine reads in a String? variable and println() prints to the console. String? is a String or null. This is a really
 * cool thing that Kotlin has no variable can be null without a ? this helps control those pesky null pointers that pop up all
 * the time in Java. Then something even better is !! which tells the JVM that this variable is impossible to be NULL. Doing
 * things this way helps you to know what can be NULL test it then pass it off as a non NULL variable. So you do not have to
 * worry about it anymore. Then for control structures we have when which is similar to a switch statement. If, else if, else
 * which is self explanatory. We also have a while, do while, for loops in here. For loops work really well in Kotlin.
 * They let you easily parse through anything and use the parts of it as your value to end the for loop. This helps readability
 * as you can easily see what you are increasing by and helps to cut down on the clutter you would normally need to do to
 * get the same effect. Another cool thing with Kotlin is that Kotlin can use anything
 * it could want from the java library. Kotlin has all the benefits of easier readability and stronger NULL safety with all the
 * benefits java can give. Another interesting data structure is the ability to name loops and to be able to break or continue a
 * loop using that name. The final interesting data structure I wish to talk about here is the ability to name parameters. This
 * allows you to put the parameters in whatever order you wish to just throw the name of the parameter and you are good to go.
 * This not only helps in readability but also helps you to not mess up order of parameters. The rest i will be talking of in BookDatabase
 */

import java.util.*
import kotlin.system.exitProcess

var mode: String? = "n"
val database: BookDatabase = BookDatabase()

fun main(args: Array<String>) {
    database.start()
    start()
}

fun start() {
    loop@ while(mode != "q" && mode != "Q") {
        println("Please choose the option you would like to do")
        println("1) Search for a book in the database")
        println("2) See a book description")
        println("3) Add a book to the database")
        println("4) Borrow a book")
        println("5) Return a book")
        println("Press q to quit")

        mode = readLine()

        if(mode == null)
            mode = "s"


        when(mode) {
            "1" -> search()
            "2" -> description()
            "3" -> add()
            "4" -> borrow()
            "5"-> returnItem()
            "q" -> {
                println("You chose to quit")
                exitProcess(0)
            }
            "Q" -> {
                println("You chose to quit")
                exitProcess(0)
            }
            "s" -> println("Stupid is as stupid does")
            else -> println("invalid choice")
        }
    }
}

fun returnItem() {
    var returned: Boolean = false
    var choice: String? = ""

    while(!returned) {
        println("To return a book please enter a Dewey Decimal number.")
        println("To go back to main menu press m")
        print("if you need one please press 1 otherwise enter the dewey number: ")
        choice = readLine()

        if (choice.equals("1")) {
            search()
        } else if (choice.equals("m")) {
            start()
        }else {
            returned = database.returnItem(choice!!)
        }

        if(!returned)
            println("This book does not seem to be ours")
    }
    println("You have returned ${database.getBook(choice!!)}")
}

fun borrow() {
    var borrowed: Boolean = false
    var choice: String? = ""

    while(!borrowed) {
        println("To borrow a book please enter a Dewey Decimal number.")
        println("To go back to main menu press m")
        print("if you need one please press 1 otherwise enter the dewey number: ")
       choice = readLine()

        if (choice.equals("1")) {
            search()
        } else if (choice.equals("m")) {
            start()
        }else {
            borrowed = database.borrow(choice!!)
        }

        if(!borrowed)
            println("We do not have this book in stock sorry")
    }
    println("You have borrowed ${database.getBook(choice!!)}")
}

fun add() {
    var key: String
    print("Please enter a title")
    val title = readLine()

    print("Please enter an author: ")
    val author = readLine()

    print("Please enter a publication date: ")
    val pubDate = readLine()

    do {
        val random: Random = Random()
        key = (((random.nextInt(100000000)).toDouble()) / 100000).toString()
    }while(database.database.containsKey(key = key))
    database.addItem(key = key,
        item = Book(title = title!!,author = author!!, pubDate = pubDate!!, deweyDecimalNumber = key), newItem = true)
}

fun description() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun search() {
    var choice: String? =""
    while(choice != "m" && choice != "b" && choice != "r") {
        println("To go to main menu press m, to borrow a book press b, to return a book press r, otherwise hit enter")
        choice = readLine()

        when(choice) {
            "m"-> start()
            "b"-> borrow()
            "r"-> returnItem()
        }

        if(choice != "m" && choice != "b" && choice != "r") {
            println("Below are your search categories please choose a number or type the category exactly:")
            for (item in SearchCategory.values())
                println("${item.searchCode})   $item")

            print("Please choose your search option: ")
            val searchCategory: String? = readLine()

            print("Please choose your search key: ")
            val searchKey: String? = readLine()

            database.search(searchCategory!!, searchKey!!)
        }
    }
}
