import parsers.FileSSC
import parsers.StepFile
import java.io.File

fun main() {
    var filePath =  "/home/rodrigo/Songs/19-XX/1625 - Dasu - 8 6/steps.ssc"

    var file =  File(filePath)


    FileSSC(filePath).parseData()



}
