package com.heeg.`fun`.day01

import kotlin.math.floor


fun main() {
    val runner = FuelRequirementCalculator()
    val file: List<String> = runner.readFileAsLinesUsingGetResourceAsStream("day01_input.txt")
    runner.calculateSumOfRequiredFuel(file)
}

class FuelRequirementCalculator {

    fun readFileAsLinesUsingGetResourceAsStream(fileName: String): List<String> =
        this::class.java.classLoader.getResourceAsStream(fileName)!!.bufferedReader().readLines()

    fun calculateSumOfRequiredFuel(listOfModuleMass: List<String>) {
        var sumOfRequiredFuel = 0
        for (singleMass in listOfModuleMass) {
            sumOfRequiredFuel = sumOfRequiredFuel.plus(calculateRequiredFuel(singleMass.toInt()))
        }
        print("Required fuel: $sumOfRequiredFuel")
    }

    private fun calculateRequiredFuel(mass: Int): Int {
        val divisionFactor = 3
        val subtractionFactor = 2
        return floor((mass / divisionFactor) - subtractionFactor.toDouble()).toInt()
    }
}