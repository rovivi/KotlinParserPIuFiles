package parsers


import game.GameRow
import game.Note
import game.StepObject
import java.lang.Exception
import java.util.regex.Pattern

class FileSSC(override var pathFile: String) : StepFile {
    var indexStep: Int = 3

    override fun writeFile(path: String) {
    }

    override fun parseData(): StepObject {
        var stringData = StepFile.UtilsSteps.pathToString(pathFile)
        val songMetaData: HashMap<String, String> = HashMap()
        val modifiers: HashMap<String, ArrayList<ArrayList<Double>>> = HashMap()
        val stepObject = StepObject()

        //Se limpian los comentarios
        stringData = stringData.replace(Regex("(\\s+//-([^;]+)\\s)|(//[\\s+]measure\\s[0-9]+\\s)"), "")

        //Se crea el matcher Para Seccionar el Regex
        val matcher = Pattern.compile("#([^;]+);").matcher(stringData)
        var indexLevel = -1//

        while (matcher.find()) {
            val currentTag = matcher.group().split(":")
            val key = currentTag[0].replace("#", "")
            val value = currentTag[1].replace(";", "")
            if (key == "NOTEDATA") {
                indexLevel++
            }//next step
            when (indexLevel) {
                indexStep -> when (key) {
                    "NOTES" -> stepObject.steps= processNotes(value)
                    "STEPSTYPE" -> print("owo")
                    "BPMS", "STOPS", "DELAYS", "WARPS", "TIMESIGNATURES", "TICKCOUNTS", "COMBOS", "SPEEDS", "SCROLLS" ->
                        if (value != "") modifiers[key] = fillModifiers(value)
                    else -> {
                        //if (value != "") modifiers[key] = fillModifiers(value)
                    }
                }
                -1 -> songMetaData[key] = value
            }
        }
        return stepObject
    }

    private fun fillModifiers(data: String): ArrayList<ArrayList<Double>> {
        val list: ArrayList<ArrayList<Double>> = ArrayList()
        val elements = data.replace("\r", "").replace("\n", "").split(",")
        elements.forEach { e ->
            val currentItem: ArrayList<Double> = ArrayList()
            val params = e.split("=")
            params.forEach { x ->
                currentItem.add(x.toDouble())
            }
            list.add(currentItem)
        }
        return list
    }

    private fun processNotes(notes: String): ArrayList<GameRow> {
        val data = notes.replace(" ", "").replace("\n", "").replace("\n\n", "\n")
        val listGameRow = ArrayList<GameRow>()
        val blocks = data.split(",")
        var currentBeat = 0.0
        blocks.forEach { block ->
            val rowsStep = block.split("\r").filter { x -> x != "" }
            val blockSize = rowsStep.size
            println(blockSize)
            rowsStep.forEach { row ->
                if (!checkEmptyRow(row)) {
                    val gameRow = stringToGameRow(row)
                    gameRow.currentBeat=currentBeat
                    listGameRow.add(gameRow)
                    //println("$row beat: $currentBeat")
                }
                currentBeat += 4.0 / blockSize
            }
        }
        return listGameRow
    }

    private fun stringToGameRow(data: String): GameRow {
        var row = data.replace("{x}", "f")
        val re = Regex("\\{([^}]+)}")
        val matcher = Pattern.compile(re.toString()).matcher(row)
        val arrayNotes=ArrayList<Note>()
        val arrayEspecialNote = ArrayList<String>()

        while (matcher.find()) {
             arrayEspecialNote.add( matcher.group())
        }
        row = row.replace(re, "E")
        var indexEspecialNote= 0
        row.forEach { l->
            if (l =='E'){
                arrayNotes.add(specialToNote(arrayEspecialNote[indexEspecialNote]))
                indexEspecialNote++
            }
            else {
                arrayNotes.add(charToNote(l))
            }
        }
        print ("-----$row ------")
        return GameRow()
    }

    private fun checkEmptyRow(row: String): Boolean {
        return Regex("(0+)").matches(row)
    }


        private fun charToNote(caracter: Char): Note {
            val note = Note()
            var charCode: Short = Common.NOTE_EMPTY

            when (caracter) {
                '1' -> charCode = Common.NOTE_TAP
                '2','6' -> charCode = Common.NOTE_LONG_START
                '3' -> charCode = Common.NOTE_LONG_END
                //'L' -> charCode = 4
                'M' -> charCode = Common.NOTE_MINE
                'F', 'f' -> charCode = Common.NOTE_FAKE
                'V' -> {
                    charCode = Common.NOTE_TAP
                    note.vanish=true
                }
                'h' -> {
                    charCode = Common.NOTE_TAP
                    note.hidden=true
                }
                'x' ->{
                    charCode= Common.NOTE_LONG_START
                    note.player =Common.PLAYER_1
                }
                'X' ->{
                    charCode= Common.NOTE_TAP
                    note.player =Common.PLAYER_1
                }
                'y' ->{
                    charCode= Common.NOTE_LONG_START
                    note.player =Common.PLAYER_2
                }
                'Y' ->{
                    charCode= Common.NOTE_TAP
                    note.player =Common.PLAYER_2
                }'z' ->{
                charCode= Common.NOTE_LONG_START
                note.player =Common.PLAYER_3
                }
                'Z' ->{
                    charCode= Common.NOTE_TAP
                    note.player =Common.PLAYER_3
                }
                else -> {
                    //Default Stuff
                }
            }
            note.type=charCode
            return note
        }

        private fun specialToNote (re:String ):Note{
            val noteString = re.replace("{","").replace("}","").replace("|","")
            return try {
                val note= charToNote(noteString[0])
                when(noteString[1]) {
                    'v','V'-> note.vanish=true
                    'h','H'-> note.hidden=true
                    's','S'-> note.sundded=true
                }
                if (noteString[2]=='1') note.fake=true
                note
            } catch (ex:Exception){
                ex.printStackTrace()
                Note()
            }
        }


    }