data class Book(val title: String, val author: String, val pubDate: String, var quanity: Int = 1, var quantityOnHand: Int = 1,
                val deweyDecimalNumber: String) {

    override fun toString(): String =
        "\n$title|/$author|/$pubDate|/$deweyDecimalNumber"

    fun equalsBook(other: Book): Boolean {
        return (this.title.decapitalize() == other.title.decapitalize()) && (this.author.decapitalize() == other.author.decapitalize())
                && (this.pubDate.decapitalize() == other.pubDate.decapitalize())
    }
}

enum class Genre (val tile: String, val minNumber: Int, val maxNumber: Int){
    Class000("Computer Science, information, and general works", 0, 100),
    Class100("philosophy and psychology", 100, 200),
    Class200("religion", 200, 300), Class300("Social Sciences", 300, 400),
    Class400("Language", 400, 500), Class500("Pure Science", 500,600),
    Class600("Technology", 600,700), Class700("Arts and recreation", 700,800),
    Class800("Literature", 800,900), Class900("History and Geography", 900,1000)
}