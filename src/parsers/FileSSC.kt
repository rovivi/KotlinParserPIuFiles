package parsers

import game.StepObject
import java.util.regex.Pattern

class FileSSC(override var pathFile: String) : StepFile {
    internal var indexStep: Int = -1

    override fun writeFile(Path: String) {

    }

    override fun parseData(): StepObject {
        var stringData = StepFile.UtilsSteps.pathToString(pathFile)
        var songMetaData: HashMap<String,String> = HashMap()

        //Se limpian los comentarios
        stringData = stringData.replace(Regex("(\\s+//-([^;]+)\\s)|(//[\\s+]measure\\s[0-9]+\\s)"), "")
        //Se crea el matcher Para Seccionar el Regex
        var matcher = Pattern.compile("#([^;]+);").matcher(stringData)

        var indexLevel = -1

        while (matcher.find()) {
            var currentTag = matcher.group().split(":")
            var key = currentTag[0]
            var value = currentTag[1]
            print(key+","+value)
            if (key== "NOTEDATA"){
                indexLevel++

            }
            else if (indexLevel>=0){
                when (key) {
                    "NOTES" -> println(value)
                    "SCROLLS" -> key
                    "STEPSTYPE" -> key
                    else ->{
                        songMetaData[key] = value
                    }
                }
            }
        }






        return StepObject()
    }

}