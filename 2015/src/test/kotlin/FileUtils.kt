
class Resource {
    companion object {

        /**
         * Reads txt file in resource folder.
         */
        fun readFile(fileName: String): String {
            return Resource::class.java.getResource("$fileName.txt").readText(Charsets.UTF_8)
        }

        /**
         * Reads txt file in resource folder and result a list for each line.
         */
        fun readFileLines(fileName: String): List<String> {
            return Resource::class.java.getResource("$fileName.txt").readText(Charsets.UTF_8).split("\n")
        }

        fun readFileAsBytes(fileName: String): ByteArray {
            return Resource::class.java.getResource("$fileName.txt").readBytes()
        }
    }
}

