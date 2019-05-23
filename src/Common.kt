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
        const val NOTE_PRESED: Short = 127


        /**PERFORMANCE*/

        const val PLAYER_0: Byte = 1
        const val PLAYER_1: Byte = 2
        const val PLAYER_2: Byte = 3
        const val PLAYER_3: Byte = 4

        fun almostEqual(a:Double,b:Double):Boolean{
            val tolerance = 0.00000001
            return Math.abs(a-b)<tolerance
        }
    }

}
