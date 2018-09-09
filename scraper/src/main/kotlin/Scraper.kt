import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File

class Scraper {

    val root : File
        get() = (javaClass.classLoader.getResource("exrx.net").file).toFile()

    fun scrape(isDebug : Boolean = false) : List<String> {
        return root.subfile("WeightExercises")
                .listFiles()
                .flatMap { it.listFiles().toList() }
                .map { Jsoup.parse(it, "UTF-8").toWorkout() }
                .toList()
                .map { it.toString() }
                .also {
                    if (isDebug) {
                        println(it)
                    }
                }
    }

    private fun Document.toWorkout() : JsonElement {
        val document = this
        return JsonObject().apply {
            addProperty("title", document.title().split(":").last().trim())
            add("muscles", JsonObject().apply {
                val href = document.selectFirst("link[href~=^https?:\\/\\/exrx.net\\/WeightExercises]").attr("href")
                val primaryMuscle = href.split("/")[4].split(Regex("(?=\\p{Upper})")).first()
                addProperty("primary", primaryMuscle)
            })
            add("classification", JsonObject().apply {
                val classification = document.getElementsContainingOwnText("Classification")
                if (!classification.isEmpty()) {
                    addProperty("force", (document.getElementsContainingOwnText("Force:").parents()[1].childNode(3) as Element).text())
                    addProperty("mechanics", (document.getElementsContainingOwnText("Mechanics:").parents()[1].childNode(3) as Element).text())
                }
            })
        }
    }

    private fun String.toFile() = File(this)
    private fun File.subfile(name: String) = ("$path/$name").toFile()
}