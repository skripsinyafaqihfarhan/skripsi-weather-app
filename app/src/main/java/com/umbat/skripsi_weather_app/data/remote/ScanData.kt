package com.kinnoe.testroomdatabase.remote

import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class scanData(filepath: InputStreamReader) : ArrayList<String>(){
    var records: MutableList<List<String>> = java.util.ArrayList()
    var scanner = Scanner(filepath)
    private fun getRecordFromLine(line: String): List<String> {
        val values: MutableList<String> = java.util.ArrayList()
        Scanner(line).use { rowScanner ->
            rowScanner.useDelimiter("[;\n]")
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next())
            }
        }
        return values
    }

    init {
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()))
        }
    }
}