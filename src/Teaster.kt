import parsers.FileSSC
import parsers.StepFile
import java.io.File

fun main() {
    //var filePath =  "/home/rodrigo/Songs/19-XX/1625 - Dasu - 8 6/steps.ssc"
    val filePath = "C:\\sma\\ssc\\01\\01.ssc"
    var gameData = parseTest(FileSSC(filePath,0))
    var arrax = arrayListOf("1","2","3")





}

private fun parseTest (data:StepFile){
    data.parseData()
}

