import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

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
                        val dir = File("./scraper")
                        val jsonFile = dir.subfile("exercises.json")
                        if (jsonFile.exists()) {
                            BufferedWriter(FileWriter(jsonFile)).apply {
                                write(it.joinToString())
                            }
                        } else {
                            jsonFile.createNewFile()
                            BufferedWriter(FileWriter(jsonFile)).apply {
                                write(it.joinToString())
                            }
                        }
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
            add("classification",
                    document.getElementsContainingOwnText("Classification")
                            .takeIf { !it.isEmpty() }
                            ?.let {
                                JsonObject().apply {
                                    addProperty("force", (document.getElementsContainingOwnText("Force:").parents()[1].childNode(3) as Element).text())
                                    addProperty("mechanics", (document.getElementsContainingOwnText("Mechanics:").parents()[1].childNode(3) as Element).text())
                                }
                            })
            addProperty("gif", document.getElementsByTag("img")
                    .getOrNull(1)
                    ?.attr("src")
                    ?.split("/")
                    ?.last() ?: "")
            addProperty("video", document.getElementsByTag("iframe")
                    .getOrNull(1)
                    ?.attr("src") ?: "")
        }
    }

    private fun String.toFile() = File(this)
    private fun File.subfile(name: String) = ("$path/$name").toFile()
}