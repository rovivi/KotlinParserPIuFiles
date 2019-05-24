import parsers.FileSSC
import parsers.StepFile
import java.io.File

fun main() {
    //var filePath =  "/home/rodrigo/Songs/19-XX/1625 - Dasu - 8 6/steps.ssc"
    val filePath = "C:\\sma\\ssc\\remx6.ssc"
    var gameData = parseTest(FileSSC(filePath,2))
    var arrax = arrayListOf("1","2","3")





}

private fun parseTest (data:StepFile){
    data.parseData()
}

