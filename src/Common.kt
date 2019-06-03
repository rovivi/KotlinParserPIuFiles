import game.GameRow
import game.Note
import java.util.*
import kotlin.collections.ArrayList

class Common {

    companion object {
        /**NOTE FIELDS*/
        const val NOTE_EMPTY: Short = 0
        const val NOTE_TAP: Short = 1
        const val NOTE_LONG_START: Short = 2
        const val NOTE_LONG_END: Short = 3
        const val NOTE_FAKE: Short = 4
        const val NOTE_MINE: Short = 5
        const val NOTE_MINE_DEATH: Short = 6
        const val NOTE_POSION: Short = 7
        const val NOTE_LONG_BODY: Short = 8
        const val NOTE_LONG_PRESED: Short = 9
        const val NOTE_LONG_TOUCHABLE: Short = 10
        const val NOTE_PRESED: Short = 127

        /**PERFORMANCE*/
        const val PLAYER_0: Byte = 1
        const val PLAYER_1: Byte = 2
        const val PLAYER_2: Byte = 3
        const val PLAYER_3: Byte = 4

        fun almostEqual(a: Double, b: Double): Boolean {
            val tolerance = 0.00000001
            return Math.abs(a - b) < tolerance
        }
        fun beatToSecond(value:Double,BPM:Double) :Double {
            return  value/BPM
        }
        fun secondToBeat(value:Double,BPM:Double) :Double {
            return  value*BPM
        }
        fun orderByBeat (array:List<GameRow>){
            Collections.sort(array){ x,y->
                if( x.currentBeat>y.currentBeat ) 1 else -1
            }
        }
        fun applyLongNotes (steps:ArrayList<GameRow>){
            var currentTickCount = 0.0
            var i = 0
            while (i <steps.size){
                val row =steps[i]
                if (row.modifiers?.get("TICKCOUNTS") !=null){
                    currentTickCount = row.modifiers!!["TICKCOUNTS"]!![1]
                }
                if (row.notes!= null){
                    for (j in 0 until row.notes!!.size){
                        if (row.notes!![j].type ==NOTE_LONG_START){
                            var beatLong = row.currentBeat

                            val newNote = Note()//Se crea una nueva nota que tiene los mismos parametros que la de entrada
                            newNote.fake=row.notes!![j].fake;
                            newNote.vanish=row.notes!![j].vanish;
                            newNote.hidden=row.notes!![j].hidden;
                            newNote.skin= row.notes!![j].skin;
                            newNote.player =row.notes!![j].player;
                            newNote.sudden =row.notes!![j].sudden;
                            newNote.type= NOTE_LONG_BODY
                            while (currentTickCount!=0.0){//prevent infinite loop
                                beatLong+= 1.0/currentTickCount
                                val newRowAux = steps.firstOrNull{ findRow-> almostEqual(beatLong,findRow.currentBeat)}
                                if ( newRowAux==null){
                                    val newRow= GameRow()
                                    newRow.currentBeat=beatLong
                                    newRow.notes= arrayListOf(Note(),Note(),Note(),Note(),Note(),Note(),Note(),Note(),Note(),Note())
                                    newRow.notes!![j]= newNote
                                    steps.add(newRow)
                                    //println("beat: $beatLong" )
                                }
                                else {
                                    if (newRowAux.modifiers?.get("TICKCOUNTS") !=null){
                                        currentTickCount = newRowAux.modifiers!!["TICKCOUNTS"]!![1]
                                    }
                                    if ( newRowAux.notes!=null && newRowAux.notes?.get(j)!!.type==NOTE_LONG_END){
                                        break
                                    }
                                    if (newRowAux.notes ==null ){
                                        newRowAux.notes= arrayListOf(Note(),Note(),Note(),Note(),Note(),Note(),Note(),Note(),Note(),Note())
                                    }
                                    newRowAux.notes!![j].type = NOTE_LONG_BODY
                                }
                            }
                            orderByBeat(steps)
                        }
                    }
                }
                i++
            }
        }

    }

    class NX20 {
        companion object {//Constants
            const val NOTE_NULL = 0x00
            const val NOTE_EFFECT = 0x41   //  0b01000001
            const val NOTE_DIV_BRAIN = 0x42   //  0b01000010
            const val NOTE_FAKE = 0x23   //  0b00100011
            const val NOTE_TAP = 0x43   //  0b01000011
            const val NOTE_HOLD_HEAD_FAKE = 0x37   //  0b00110111
            const val NOTE_HOLD_HEAD = 0x57   //  0b01010111
            const val NOTE_HOLD_BODY_FAKE = 0x3b   //  0b00111011
            const val NOTE_HOLD_BODY = 0x5B   //  0b01011011
            const val NOTE_HOLD_TAIL_FAKE = 0x3f   //  0b00111111
            const val NOTE_HOLD_TAIL = 0x5F   //  0b01011111
            const val NOTE_ITEM_FAKE = 0x21   //  0b00100001
            const val NOTE_ITEM = 0x61   //  0b01100001
            const val NOTE_ROW = 0x80   //  0b10000000
            /*  Effect Stuff    */
            /*
                                        [Type, Attr, Seed, Attr2]
                Explosion at screen:    [65,      0,   22,   192]
                Random Items at screen: [65,      3,   11,   192]
            */

            /*  Metadata Stuff  */
            const val MetaUnknownM = 0

            const val MetaNonStep = 16
            const val MetaFreedom = 17
            const val MetaVanish = 22
            const val MetaAppear = 32
            const val MetaHighJudge = 64
            const val UnknownMeta0 = 80
            const val UnknownMeta1 = 81
            const val UnknownMeta2 = 82
            const val MetaStandBreak = 83

            const val MetaNoteSkinBank0 = 900
            const val MetaNoteSkinBank1 = 901
            const val MetaNoteSkinBank2 = 902
            const val MetaNoteSkinBank3 = 903
            const val MetaNoteSkinBank4 = 904
            const val MetaNoteSkinBank5 = 905
            const val MetaMissionLevel = 1000
            const val MetaChartLevel = 1001
            const val MetaNumberPlayers = 1002

            const val MetaFloor1Level = 1101
            const val MetaFloor2Level = 1201
            const val MetaFloor3Level = 1301
            const val MetaFloor4Level = 1401

            const val MetaFloor1UnkSpec0 = 1103
            const val MetaFloor2UnkSpec0 = 1203
            const val MetaFloor3UnkSpec0 = 1303
            const val MetaFloor4UnkSpec0 = 1403

            const val MetaFloor1UnkSpec1 = 1110
            const val MetaFloor2UnkSpec1 = 1210
            const val MetaFloor3UnkSpec1 = 1310
            const val MetaFloor4UnkSpec1 = 1410

            const val MetaFloor1UnkSpec2 = 1111
            const val MetaFloor2UnkSpec2 = 1211
            const val MetaFloor3UnkSpec2 = 1311
            const val MetaFloor4UnkSpec2 = 1411

            const val MetaFloor1Spec = 1150
            const val MetaFloor2Spec = 1250
            const val MetaFloor3Spec = 1350
            const val MetaFloor4Spec = 1450

            const val MetaFloor1MissionSpec0 = 66639
            const val MetaFloor2MissionSpec0 = 66739
            const val MetaFloor3MissionSpec0 = 66839
            const val MetaFloor4MissionSpec0 = 66939

            const val MetaFloor1MissionSpec1 = 132175
            const val MetaFloor2MissionSpec1 = 132275
            const val MetaFloor3MissionSpec1 = 132375
            const val MetaFloor4MissionSpec1 = 132475

            const val MetaFloor1MissionSpec2 = 197711
            const val MetaFloor2MissionSpec2 = 197811
            const val MetaFloor3MissionSpec2 = 197911
            const val MetaFloor4MissionSpec2 = 198011

            const val MetaFloor1MissionSpec3 = 263247
            const val MetaFloor2MissionSpec3 = 263347
            const val MetaFloor3MissionSpec3 = 263447
            const val MetaFloor4MissionSpec3 = 263547
        }
    }
}
