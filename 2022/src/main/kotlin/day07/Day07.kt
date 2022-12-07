package day07

import java.lang.RuntimeException

typealias FolderSizePredicate = (folder: Day07.Companion.Folder) -> Boolean

class Day07 {

    companion object {

        fun part1(input: List<String>): Long {
            val fileSystem = FileSystem.of(input.drop(1)) // Drop first '$ cd /'

            return fileSystem.root
                .findFoldersWithSize { it.size <= 100_000 }
                .sumOf(Folder::size)
        }

        fun part2(input: List<String>): Long {
            val fileSystem = FileSystem.of(input.drop(1)) // Drop first '$ cd /'

            val totalDiskSpace = 70_000_000L
            val requiredSpace = 30_000_000L
            val freeAtLeast = requiredSpace - (totalDiskSpace - fileSystem.root.size)

            return fileSystem.root
                .findFoldersWithSize { it.size >= freeAtLeast }
                .minByOrNull(Folder::size)
                ?.size ?: -1L
        }

        data class File(val name: String, val size: Long)

        class Folder(val name: String, val parentFolder: Folder?) {
            var size = 0L
                private set
            private val folders = mutableMapOf<String, Folder>()
            private val files = mutableListOf<File>()

            fun getOrCreateFolder(folderName: String) = folders.getOrPut(folderName) {
                Folder(folderName, this)
            }

            fun addFile(filename: String, fileSize: Long) {
                files.add(File(filename, fileSize))
                updateSize(fileSize)
            }

            private fun updateSize(sizeToAdd: Long) {
                size += sizeToAdd
                parentFolder?.updateSize(sizeToAdd)
            }

            fun getAbsolutePath(): String {
                val result = mutableListOf<String>()

                var dir: Folder? = this
                while (dir != null) {
                    result.add(dir.name)
                    dir = dir.parentFolder
                }

                return result.reversed().joinToString("")
            }

            fun findFoldersWithSize(predicate: FolderSizePredicate): List<Folder> {
                val result = mutableListOf<Folder>()

                if (predicate(this)) {
                    result.add(this)
                }

                for (folder in folders.values) {
                    result.addAll(folder.findFoldersWithSize(predicate))
                }

                return result
            }
        }

        class FileSystem {

            companion object {
                fun of(input: List<String>) = FileSystem().apply {
                    input.forEach(this::invokeCommand)
                }
            }

            private var currentPath = Folder("/", null)
            private var currentProcessedFolder = currentPath

            val root = currentPath

            fun invokeCommand(input: String) {
                val parts = input.replace("$ ", "").split(" ")

                when {
                    parts[0] == "dir" -> currentPath.getOrCreateFolder(parts[1])
                    parts[0] == "cd" -> {
                        currentPath = if (parts[1] == "..") {
                            currentPath.parentFolder ?: root
                        } else {
                            currentPath.getOrCreateFolder(parts[1])
                        }
                        currentProcessedFolder = currentPath
                    }

                    parts[0] == "ls" -> currentProcessedFolder = currentPath
                    parts[0].toLongOrNull() != null -> currentProcessedFolder.addFile(parts[1], parts[0].toLong())
                    else -> throw RuntimeException("Unsupported Command '$input'")
                }
            }
        }
    }
}