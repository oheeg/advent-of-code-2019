package com.heeg.`fun`.day02

import kotlin.system.exitProcess

fun main() {
    val runner = IntCode()
    val file: String = runner.readFileAsLinesUsingGetResourceAsStream("day02_input.txt")[0]
    val numbersAsString: MutableList<String> = splitNumbersInStringByCommas(file).toMutableList()
    val numbersAsInts = convertStringToListOfInts(numbersAsString)
    val initialIndex = 0
    runner.restoreGravityAssistProgram(initialIndex, numbersAsInts)
}

class IntCode {

    fun readFileAsLinesUsingGetResourceAsStream(fileName: String): List<String> =
        this::class.java.classLoader.getResourceAsStream(fileName)!!.bufferedReader().readLines()

    fun restoreGravityAssistProgram(givenIndex: Int, numbers: MutableList<Int>): MutableList<Int> {

        val modifiedNumbers: MutableList<Int> = when (numbers[givenIndex]) {
            99 -> printFinalResult(numbers)
            1 -> handleAddition(givenIndex, numbers)
            2 -> handleMultiplication(givenIndex, numbers)
            // Non-relevant oppCode: next index
            else -> restoreGravityAssistProgram(givenIndex + 1, numbers)
        }
        // Step-up 4 indices after OC-processing
        restoreGravityAssistProgram(givenIndex + 4, modifiedNumbers)
        return modifiedNumbers
    }

    private fun printFinalResult(numbers: MutableList<Int>): MutableList<Int> {
        for ((index, element) in numbers.withIndex()) {
            println("Index $index has element $element")
        }
        exitProcess(-1)
    }

    private fun handleMultiplication(index: Int, numbers: MutableList<Int>): MutableList<Int> {
        val firstReadPos: Int = numbers[index + 1]
        val secondReadPos: Int = numbers[index + 2]
        val storePos: Int = numbers[index + 3]

        val result = numbers[firstReadPos] * numbers[secondReadPos]

        numbers[storePos] = result
        return numbers
    }

    private fun handleAddition(index: Int, numbers: MutableList<Int>): MutableList<Int> {
        val firstReadPos: Int = numbers[index + 1]
        val secondReadPos: Int = numbers[index + 2]
        val storePos: Int = numbers[index + 3]

        val result = numbers[firstReadPos] + numbers[secondReadPos]

        numbers[storePos] = result
        return numbers
    }
}

private fun splitNumbersInStringByCommas(file: String): List<String> = file.split(",").map { it.trim() }

private fun convertStringToListOfInts(numbers: MutableList<String>): MutableList<Int> {
    val intNumbers: MutableList<Int> = mutableListOf()
    for ((index, element) in numbers.withIndex()) {
        intNumbers.add(index, element.toInt())
    }
    return intNumbers
}