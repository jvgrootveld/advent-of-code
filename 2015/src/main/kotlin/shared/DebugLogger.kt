package shared

class DebugLogger(private var debugEnabled: Boolean = true) {

    fun debugln(message: String) {
        if (debugEnabled) {
            println(message)
        }
    }
}