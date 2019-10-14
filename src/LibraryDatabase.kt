interface LibraryDatabase {
    val database: MutableMap<String, Any?>

    fun search(searchCategory: String, searchKey: String)

    fun <T> borrow(key: T): Boolean

    fun <T> returnItem(key: T): Boolean

    fun <T> addItem(key: String, item: T, newItem: Boolean)

    fun backup(key: String)

    fun multipleItems(key: String)

    fun start()

    fun getBook(choice: String): String
}