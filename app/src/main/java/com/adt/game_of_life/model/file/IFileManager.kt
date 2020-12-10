package com.adt.game_of_life.model.file

import java.io.File

interface IFileManager {
    fun getFilenames(): List<String>
    fun getFiles(): List<File>
    fun addFile(filename: String, content: Array<Array<Int?>>)
    fun getContent(filename: String): Array<Array<Int?>>
    fun delete(filename: String)
}