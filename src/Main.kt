import java.util.*

var mode: String? = "n"
val database: BookDatabase = BookDatabase()

fun main(args: Array<String>) {
    database.start()
    start()
}

fun start() {
    while(mode != "q" && mode != "Q") {
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
            "q" -> println("You chose to quit")
            "Q" -> println("You chose to quit")
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

//need to add a loop till they enter a correct dewey number
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
        println("Below are your search categories please choose a number or type the category exactly:")
        for (item in SearchCategory.values())
            println("${item.searchCode})   $item")

        print("Please choose your search option: ")
        val searchCategory: String? = readLine()

        print("Please choose your search key: ")
        val searchKey: String? = readLine()

        if(choice != "m" && choice != "b" && choice != "r") {
            database.search(searchCategory!!, searchKey!!)
        }
    }
}
