package sorting

import java.util.Scanner
import java.io.File

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    // var dataType = try {
    //     when(args[1]) {
    //         "long" -> "long"
    //         "word" -> "word"
    //         "line" -> "line"
    //         "sortInteger" -> "sortInteger"
    //         else -> "word"
    //     }
    // } catch(e: Exception) {
    //     var dataType = "sortInteger"
    // }
    // val list = mutableListOf<Int>()

    var dataType = ""
    var sortingType = ""
    var outputFile = " "

    if (args.contains("-sortingType")) {
        try {

            var a = args.indexOf("-sortingType")
            sortingType = args[a + 1]
            val cdl = arrayOf("byCount", "natural")

            for (arg in args) {
                if (arg == "-dataType") {
                    break
                } else if (arg in cdl) {
                    continue
                } else if (arg == "-sortingType") {
                    continue
                } else {
                    println("$arg is not a valid parameter. It will be skipped.")
                }

            }
            /*args.forEach {
                if (it == "-dataType") break
                if (it in cdl) continue
                println("$it is not a valid parameter. It will be skipped.")
            } */

        } catch (e: IndexOutOfBoundsException) { println("No sorting type defined!") }

    } else sortingType = "natural"

    if (args.contains("-dataType")) {

        try {
            var a = args.indexOf("-dataType")
            dataType = args[a + 1]

        } catch (e: IndexOutOfBoundsException) { println("No data type defined!") }

    } else dataType = "word"

    val inputFile = if (args.contains("-inputFile")) {
        val a = args.indexOf("-inputFile")
        args[a + 1]
    } else " "

    outputFile = if (args.contains("-outputFile")) {
        val a = args.indexOf("-outputFile")
        args[a + 1]
    } else " "

    // val cdl = arrayOf("-sortingType", "-dataType", "byCount", "natural", "long", "line", "word")

    val entry = mutableListOf<String>()

    if (dataType != "" && inputFile == " ") {
        while(sc.hasNext()) { entry.add(sc.nextLine()) }
    } else {
        val file = File(inputFile)
        val ent = file.readText()
        entry += ent
    }

    // when(dataType) {
    //     "long" -> longType(entry)
    //     "word" -> wordType(entry)
    //     "line" -> lineType(entry)
    //     "sortInteger" -> sortInt(entry)
    // }

    if (sortingType == "natural") {
        when(dataType) {
            "long" -> sortInt(entry)
            "word" -> wordType(entry)
            "line" -> lineType(entry)
        }
    } else if(sortingType == "byCount") {
        when(dataType) {
            "long" -> longTypeByCount(entry, outputFile)
            "word" -> wordTypeByCount(entry, outputFile)
            "line" -> lineTypeByCount(entry, outputFile)
        }
    }
}

fun longTypeByCount(a: MutableList<String>, outputFile: String) {

    // This sort long by count

    val list = mutableListOf<String>()
    val listed = mutableListOf<Long>()
    val values = mutableListOf<Int>()
    val keys = mutableListOf<Long>()
    val list2 = mutableMapOf<Long, Int>()

    for (yo in a) {
        list += yo.split(Regex("\\s+"))
    }

    for(w in list) {
        try {
            listed.add(w.toLong())
        } catch (e: NumberFormatException) {
            println("$w is not a long. It will be skipped.")
        }
    }

    /*for (w in a) {

        val one = w.split("//s+")
        for (h in w) {
            if (w.matches(Regex("[1-9]+"))) {
                list.add(w.toLong())
            } else if (w.matches(Regex("[a-zA-Z]"))) {
                println("$h is not a long. It will be skipped.")
            } else {
                continue
            }
        }
    }*/

    listed.sort()

    println("Total numbers: ${list.size}.")
    for (x in listed) {
        var count = 0
        if (!(keys.contains(x))) keys.add(x)
        for (y in listed) {
            if (x == y) ++count
        }
        values.add(count)
        list2.put(keys[keys.size - 1], count)

    }

    val finList = list2.toList().sortedBy {(_, value) -> value}.toMap()

    if (outputFile != " ") {
        val file = File(outputFile)
        file.writeText("")
        finList.forEach { file.appendText("/n$it") }
    } else {
        for (fin in finList) {
            println("${fin.key}: ${fin.value} time(s), ${((fin.value.toDouble() / listed.size.toDouble()) * 100).toInt()}%")
        }
    }


}

fun lineTypeByCount(a: MutableList<String>, outputFile: String) {
    // val list = mutableListOf<String>()
    val values = mutableListOf<Int>()
    val keys = mutableListOf<String>()
    val list2 = mutableMapOf<String, Int>()

    a.sort()

    println("Total numbers: ${a.size}.")
    for (x in a) {
        if (!(keys.contains(x))) keys.add(x)
        var count = 0
        for (y in a) {
            if (x == y) ++count
        }
        values.add(count)
        list2.put(keys[keys.size - 1], values[values.size - 1])
    }

    val finList = list2.toList().sortedBy {(_, value) -> value}.toMap()

    if (outputFile != " ") {
        val file = File(outputFile)
        file.writeText("")
        finList.forEach { file.appendText("/n$it") }
    } else {
        for (fin in finList) {
            println("${fin.key}: ${fin.value} time(s), ${((fin.value.toDouble() / a.size.toDouble()) * 100).toInt()}%")
        }
    }


}

fun wordTypeByCount(a: MutableList<String>, outputFile: String) {
    val list = mutableListOf<String>()
    val values = mutableListOf<Int>()
    val keys = mutableListOf<String>()
    val list2 = mutableMapOf<String, Int>()

    for(w in a) {list += w.split(Regex("\\s+"))}
    list.sort()
    println("Total numbers: ${list.size}.")
    for (x in list) {
        if (!(keys.contains(x))) keys.add(x)
        var count = 0
        for (y in list) {
            if (x == y) ++count
        }
        values.add(count)
        list2.put(keys[keys.size - 1], values[values.size - 1])
    }

    val finList = list2.toList().sortedBy {(_, value) -> value}.toMap()


    if (outputFile != " ") {
        val file = File(outputFile)
        file.writeText("")
        finList.forEach { file.appendText("/n$it") }
    } else {
        for (fin in finList) {
            println("${fin.key}: ${fin.value} time(s), ${((fin.value.toDouble() / list.size.toDouble()) * 100).toInt()}%")
        }
    }
    // finList.toSortedMap()

}

fun lineType(a: MutableList<String>) {

    // This sorts line naturally


    val list = a.toMutableList()

    list.sort()
    println("Total lines: ${a.size}.")
    println("Sorted data:")
    for (x in list) {
        println(x)
    }

}

fun wordType(a: MutableList<String>) {
    val list = mutableListOf<String>()

    for(w in a) {list += w.split(Regex("\\s+"))}

    list.sort()
    println("Total words: ${list.size}.")
    println("Sorted words: ${list.joinToString(" ")}")

}

fun sortInt(a: MutableList<String>) {

    // this sort long by naturally
    //

    val list = mutableListOf<Long>()



    for(w in a) {list += w.split(Regex("\\s+")).map{it.toLong()}}
    list.sort()
    println("Total numbers: ${list.size}.")
    println("Sorted data: ${list.joinToString(" ")}")
}