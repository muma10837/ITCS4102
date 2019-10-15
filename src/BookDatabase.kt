/**
 * This program is made based off a interface and works similar to java. Kotlin also has open classes which are similar
 * to java's parent and child classes. Kotlin does not make you make getters and setters they are built into the classes
 * this helps to cut down code as getters and setters can clutter down a program. Another really cool data structure is
 * the forEach that most Collections use. This lets you literally parse through each item in the Collection and use each
 * entry in the Collection. This helps to cut down clutter and makes it easier to read as you can see below. Something that
 * would have taken a ton of lines for each one took all of what five lines.
 */
import java.io.File

class BookDatabase(): LibraryDatabase {
    override val database: MutableMap<String, Any?> = mutableMapOf()

    override fun search(searchCategory: String, searchKey: String) {
        var book: Book
        if (searchCategory.equals(SearchCategory.title) || searchCategory.toInt() == 0) {
            database.forEach() {
                book = it.value as Book
                if(book.title.contains(searchKey)) {
                    println("Title: ${book.title}, Author: ${book.author}, PublicationDate: ${book.pubDate}, Dewey Decimal Number: ${book.deweyDecimalNumber}")
                }
            }
        } else if (searchCategory.equals(SearchCategory.author) || searchCategory.toInt() == 1) {
            database.forEach() {
                book = it.value as Book
                if(book.author.contains(searchKey)) {
                    println("Title: ${book.title}, Author: ${book.author}, PublicationDate: ${book.pubDate}, Dewey Decimal Number: ${book.deweyDecimalNumber}")
                }
            }
        } else if (searchCategory.equals(SearchCategory.pubYear) || searchCategory.toInt() == 2) {
            database.forEach() {
                book = it.value as Book
                if(book.pubDate.contains(searchKey)) {
                    println("Title: ${book.title}, Author: ${book.author}, PublicationDate: ${book.pubDate}, Dewey Decimal Number: ${book.deweyDecimalNumber}")
                }
            }
        } else if(searchCategory.equals(SearchCategory.deweyNumber) || searchCategory.toInt() == 3) {
            database.forEach() {
                book = it.value as Book
                if (book.deweyDecimalNumber.contains(searchKey)) {
                    println("Title: ${book.title}, Author: ${book.author}, PublicationDate: ${book.pubDate}, Dewey Decimal Number: ${book.deweyDecimalNumber}")
                }
            }
        }
    }

    /**
     * if there is a book to borrow returns true otherwise returns false
     */
    override fun <String> borrow(key: String): Boolean {
        val book: Book = database[key.toString()] as Book? ?: return false

        if(book.quantityOnHand != 0) {
            book.quantityOnHand--
            return true
        }
        return false
    }

    /**
     * adds a book to the database if the book is a new addition backs it up otherwise does not
     * so I can reuse method for start()
     */
    override fun <T> addItem(key: String, item: T, newItem: Boolean) {
        var found: Boolean = false
        var key = key
        if(newItem) {
            database.forEach() {
                if ((item as Book).equalsBook(it.value as Book)) {
                    key = it.key
                    val book: Book = database[key] as Book
                    book.quanity++
                    book.quantityOnHand++
                    found = true
                }
            }
            if (!found) {
                database[key] = item
            }
        } else {
            database[key] = item
        }
        if (newItem)
            backup(key = key)

    }

    /**
     * adds the item to the text file
     */
    override fun backup(key: String) {
        val text= database[key]
        val file: String = System.getProperty("user.dir") + "/src/Books.txt"
        File(file).appendText(text.toString())
    }

    /**
     * if item exists in the database it increases amount on hand and amount in general
     * then adds to database again need to test
     */
    override fun multipleItems(key: String) {
        val book: Book = database[key] as Book
        book.quantityOnHand++
        book.quanity++
        database[key] = book
    }

    /**
     * this gets the database started
     */
    override fun start() {
        val file: String = System.getProperty("user.dir") + "/src/Books.txt"

        File(file).forEachLine {
            val parameterList = it.split("|/")
            val book= Book(title = parameterList[0], author = parameterList[1], pubDate = parameterList[2],
                deweyDecimalNumber = parameterList[3])
            if(database.containsKey(book.deweyDecimalNumber))
                multipleItems(book.deweyDecimalNumber)
            else
                addItem(book.deweyDecimalNumber, book, false)
        }
    }

    override fun <T> returnItem(key: T): Boolean {
        val book: Book = database[key.toString()] as Book? ?: return false

        if(book.quantityOnHand < book.quanity) {
            book.quantityOnHand++
            return true
        }
        return false
    }

    override fun getBook(choice: String): String {
        val book = database[choice] as Book
        return "${book.title} by ${book.author} published in ${book.pubDate}"
    }

}

enum class SearchCategory(val searchCode: Int) {
    title(0), author(1), pubYear(2), deweyNumber(3)
}

