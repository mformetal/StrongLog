import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.jsoup.Jsoup
import java.io.File

class Scraper {

    private val moshi = Moshi.Builder().build()
    private val charset = "UTF-8"

    val root : File
        get() = (javaClass.classLoader.getResource("exrx.net").file).toFile()

    class Workout(val title: String)

    fun scrape() {
        root.subfile("WeightExercises")
                .listFiles()
                .flatMap { it.listFiles().toList() }
                .map {
                    val document = Jsoup.parse(it, "UTF-8")

                    Workout(title = document.title())
                }
                .toList()
                .run {
                    val type = Types.newParameterizedType(List::class.java, Workout::class.java)
                    val adapter = moshi.adapter<List<Workout>>(type)
                    val json = adapter.toJson(this)
                    println(json)
                }
    }

    private fun String.toFile() = File(this)
    private fun File.subfile(name: String) = ("$path/$name").toFile()
}