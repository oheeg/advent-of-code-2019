package com.heeg.`fun`.day02

import kotlin.system.exitProcess

fun main() {
    val runner = IntCode()
    val file: String = runner.readFileAsLinesUsingGetResourceAsStream("1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,10,19,23,1,23,9,27,1,5,27,31,2,31,13,35,1,35,5,39,1,39,5,43,2,13,43,47,2,47,10,51,1,51,6,55,2,55,9,59,1,59,5,63,1,63,13,67,2,67,6,71,1,71,5,75,1,75,5,79,1,79,9,83,1,10,83,87,1,87,10,91,1,91,9,95,1,10,95,99,1,10,99,103,2,103,10,107,1,107,9,111,2,6,111,115,1,5,115,119,2,119,13,123,1,6,123,127,2,9,127,131,1,131,5,135,1,135,13,139,1,139,10,143,1,2,143,147,1,147,10,0,99,2,0,14,0\n")[0]
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