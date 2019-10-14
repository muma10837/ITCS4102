import java.io.File
import java.io.File.*
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

class BookDatabase(): LibraryDatabase {
    override val database: MutableMap<String, Any?> = mutableMapOf<String, Any?>()

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
                if (it.equals(other = item as Book)) {
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
            database[key.toString()] = item
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
            val book: Book = Book(title = parameterList[0], author = parameterList[1], pubDate = parameterList[2],
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
