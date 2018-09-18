import mformetal.stronglog.models.Classification
import mformetal.stronglog.models.Muscles
import mformetal.stronglog.models.Workout
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class Scraper {

    val root : File
        get() = File("/Users/mbpeele/Databases/exrx.net")

    fun scrape(isDebug : Boolean = false)  {
        root.subfile("WeightExercises")
                .listFiles()
                .flatMap { it.listFiles().toList() }
                .map { Jsoup.parse(it, "UTF-8").toWorkout() }
                .also {
//                    if (isDebug) {
//                        val dir = File("./scraper")
//                        val jsonFile = dir.subfile("exercises.json").apply {
//                            if (!exists()) createNewFile()
//                        }
//
//                        BufferedWriter(FileWriter(jsonFile)).apply {
//                            val jsonArray = Gson().toJson(it)
//                            write(jsonArray)
//                        }.close()
//                    }
                }
    }

    private fun Document.toWorkout() : Workout {
        return Workout(
                title = title().split(":").last().trim(),
                muscles = Muscles(
                        primary = {
                            val href = selectFirst("link[href~=^https?:\\/\\/exrx.net\\/WeightExercises]").attr("href")
                            href.split("/")[4].split(Regex("(?=\\p{Upper})")).first()
                        }(),
                        secondaries = listOf()
                ),
                classification = {
                    getElementsContainingOwnText("Classification")
                            .takeIf { !it.isEmpty() }
                            ?.let {
                                Classification(
                                        force = (getElementsContainingOwnText("Force:").parents()[1].childNode(3) as Element).text(),
                                        mechanics = (getElementsContainingOwnText("Mechanics:").parents()[1].childNode(3) as Element).text()
                                )
                            }
                }(),
                gifUrl = {
                    getElementsByTag("img")
                            .getOrNull(1)
                            ?.attr("src")
                            ?.split("/")
                            ?.last() ?: ""
                }(),
                videoUrl = {
                    getElementsByTag("iframe")
                            .getOrNull(1)
                            ?.attr("src") ?: ""
                }()
        )
    }

    private fun String.toFile() = File(this)
    private fun File.subfile(name: String) = ("$path/$name").toFile()
}