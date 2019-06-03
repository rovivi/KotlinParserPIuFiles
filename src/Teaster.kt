import game.GameRow
import parsers.FileSSC
import parsers.StepFile
import java.io.File
import kotlin.random.Random

fun main() {
    //var filePath =  "/home/rodrigo/Songs/19-XX/1625 - Dasu - 8 6/steps.ssc"
    val filePath = "C:\\sma\\ssc\\monkey.ssc"
    var gameData = parseTest(FileSSC(filePath,0))
    var arrax = arrayListOf("1","2","3")
//
//    var arrayX = arrayListOf<GameRow>()
//    for (i in 0..5){
//        arrayX.add(GameRow())
//    }
//
//
//    arrayX.forEach{x-> x.currentBeat= Random.nextDouble()}
//    Common.orderByBeat(arrayX)
//
//    var i =0
//    while (0 <=arrayX.size){
//        val custom= GameRow()
//        custom.currentBeat= arrayX[i].currentBeat+1
//        arrayX.add(custom)
//        Common.orderByBeat(arrayX)
//        println(arrayX[i].currentBeat)
//        i++
//    }
//
//    arrayX.forEach{x-> println(x.currentBeat)}
//




}

private fun parseTest (data:StepFile){
    data.parseData()
}