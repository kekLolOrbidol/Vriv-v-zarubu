package com.adt.game_of_life.model.file

import android.app.Application
import java.io.*

class FileManager(context: Application) : IFileManager {

    private val root = context.getExternalFilesDir(null)!!

    override fun getFilenames(): List<String> {
        return getFiles().map { it.nameWithoutExtension }
    }

    override fun getFiles(): List<File> {
        return root
            .listFiles()!!
            .filter { it.extension == GAME_OF_LIFE_EXTENSION }
            .sortedBy { it.name }
    }

    override fun addFile(filename: String, content: Array<Array<Int?>>) {
        val isUnique = !getFiles().any { it.name == filename }
        if (isUnique) {
            val file = getFile(filename)
            val fos = FileOutputStream(file)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(content)
        }
    }

    override fun getContent(filename: String): Array<Array<Int?>> {
        val file = getFile(filename)
        val fis = FileInputStream(file)
        val ois = ObjectInputStream(fis)
        return ois.readObject() as Array<Array<Int?>>
    }

    override fun delete(filename: String) {
        val file = getFile(filename)
        file.delete()
    }

    private fun getFile(filename: String): File {
        return File(root.path, filename.addExtension())
    }

    private fun String.addExtension(): String {
        return "$this.$GAME_OF_LIFE_EXTENSION"
    }

    private companion object {
        const val GAME_OF_LIFE_EXTENSION = "gole"
    }
}