package game

import java.util.*
import kotlin.collections.ArrayList

class GameRow {
    var BPM : Double? =null
    var scroll : Double? =null
    var notes :Array<Note>? = null
    var speedMod :Array<Int>?= null
    var currentBeat :Double = 0.0

    constructor()

    constructor(notes :ArrayList<Note>)

    override fun toString(): String {
        return "GameRow(BPM=$BPM, scroll=$scroll, notes=${Arrays.toString(notes)}, speedMod=${Arrays.toString(speedMod)})"
    }

}


