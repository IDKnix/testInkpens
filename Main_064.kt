import Inkpacks.inkcloud_064
import kotlin.system.exitProcess

private const val MAX_NOTES = 9999

fun main() {
    val inkCloud = inkcloud_064("0.6.4")
    var note = 0

    print("Initiate InkPens Simple Version? ")
    val initiate = readlnOrNull()

    if (initiate != null) {
        if (initiate.contains('y', ignoreCase = true)) {
            println("Selected yes")
            while (note <= MAX_NOTES) {
                print("Note $note start: ")
                val noteValue = readlnOrNull() ?: ""

                if (noteValue == "//note view LATEST" && note > 0) {
                    inkCloud.latestNote(note)
                }

                if (noteValue.startsWith("//note view: ")) {
                    val noteNumber = noteValue.substringAfterLast(" ").toInt()
                    if (noteNumber in 0 until note) {
                        val key = "note$noteNumber"
                        val noteContent = inkCloud.getNote(key, "err")
                        try {
                            if (noteContent.isBlank()) {
                                println("Invalid Note")
                            } else {
                                inkCloud.specificNote(noteNumber, note)
                            }
                        } catch (e: Exception) {
                            println(e.message)
                        }
                    } else {
                        println("ERROR IN LIST")
                    }
                } else {
                    inkCloud.saveNote("note$note", noteValue)
                    inkCloud.getNote("note$note", "err")
                }

                note++
            }

            if (initiate.contains('n', ignoreCase = true)) {
                println("Selected no")
                exitProcess(0)
            } else {
                println("Invalid answer")
                exitProcess(1)
            }
        }
    }
}
