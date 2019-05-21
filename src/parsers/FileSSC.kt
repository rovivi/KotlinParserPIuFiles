package parsers

import game.GameRow
import game.StepObject
import java.lang.reflect.Array
import java.util.regex.Pattern

class FileSSC(override var pathFile: String) : StepFile {
    var indexStep: Int = 0

    override fun writeFile(path: String) {
    }

    override fun parseData(): StepObject {
        var stringData = StepFile.UtilsSteps.pathToString(pathFile)
        val songMetaData: HashMap<String,String> = HashMap()
        val modifiers:  HashMap<String,ArrayList<ArrayList<Double>>> = HashMap()
        val stepObject = StepObject()

        //Se limpian los comentarios
        stringData = stringData.replace(Regex("(\\s+//-([^;]+)\\s)|(//[\\s+]measure\\s[0-9]+\\s)"), "")

        //Se crea el matcher Para Seccionar el Regex
        val matcher = Pattern.compile("#([^;]+);").matcher(stringData)
        var indexLevel = -1//

        while (matcher.find()) {
            val currentTag = matcher.group().split(":")
            val key = currentTag[0].replace("#","")
            val value = currentTag[1].replace(";","")
            if (key == "NOTEDATA") {
                indexLevel++
            }//next step
            when (indexLevel) {
                indexStep -> when (key) {
                    "NOTES"-> processNotes(value)
                    "STEPSTYPE" -> print("owo")
                    "BPMS", "STOPS", "DELAYS", "WARPS", "TIMESIGNATURES", "TICKCOUNTS", "COMBOS", "SPEEDS", "SCROLLS"->
                        if (value != "") modifiers[key] = fillModifiers(value)
                    else ->{
                        //if (value != "") modifiers[key] = fillModifiers(value)
                    }
                }
                -1 -> songMetaData[key] = value
            }
        }
        return stepObject
    }

    private fun fillModifiers (data:String):ArrayList<ArrayList<Double>> {
        val list: ArrayList<ArrayList<Double>> = ArrayList()
        val elements = data.replace("\r","").replace("\n","").split(",")
        elements.forEach{e->
            val currentItem : ArrayList <Double> = ArrayList()
            val params = e.split("=")
            params.forEach { x->
                currentItem.add(x.toDouble())
            }
            list.add(currentItem)
        }
        return list
    }

    private fun processNotes (notes :String ):ArrayList<GameRow> {
        val data= notes.replace(" ","").replace("\n","").replace("\n\n","\n")
        val listGameRow = ArrayList<GameRow>()
        val blocks = data.split(",")
        var currentBeat =0.0
        blocks.forEach{ block->
            val rowsStep = block.split(",\r")
            rowsStep.forEach{row ->
                var gameRow =stringToGameRow(row)
                listGameRow.add(gameRow)
            }
        }
        return listGameRow
    }


    private fun stringToGameRow (row: String ):GameRow{
        var matcher = Pattern.compile("\\{([^\\}]+)\\}").matcher(row)
        println(row)
        return GameRow()
    }
}