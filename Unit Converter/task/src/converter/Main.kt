package converter

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    do {
        print("Enter what you want to convert (or exit): ")
        val command = scanner.nextLine().toLowerCase().split(" ")
        try {
            val convertedResult = when {
                command.size <= 1 -> continue
                UnitConverter.isLengthType(command[1]) -> UnitConverter.toAnotherLength(command[1], command[3], command[0].toDouble())
                UnitConverter.isWeightType(command[1]) -> UnitConverter.toAnotherWeight(command[1], command[3], command[0].toDouble())
                else -> throw  IllegalArgumentException()
            }
            displayResult(command[1], command[3], command[0].toDouble(), convertedResult)
        } catch (exp: Exception) {
            displayFailedResult(command[1], command[3])
        }
    } while (command[0] != "exit")
}

fun displayResult(mainType: String, destinationType: String, value: Double, result: Double) {
    val typeName1 = UnitConverter.getFullTypeNameBasedOnValue(mainType, value)
    val typeName2 = UnitConverter.getFullTypeNameBasedOnValue(destinationType, result)
    println("$value $typeName1 is $result $typeName2")
}

fun displayFailedResult(mainType: String, destinationType: String) {
    val typeName1 = UnitConverter.getFullTypeNameBasedOnValue(mainType, -1.0)
    val typeName2 = UnitConverter.getFullTypeNameBasedOnValue(destinationType, -1.0)
    println("Conversion from $typeName1 to $typeName2 is impossible")
}

object UnitConverter {
    private val lengthTypes = listOf(
            listOf("m", "meter", "meters"),
            listOf("km", "kilometer", "kilometers"),
            listOf("cm", "centimeter", "centimeters"),
            listOf("mm", "millimeter", "millimeters"),
            listOf("mi", "mile", "miles"),
            listOf("yd", "yard", "yards"),
            listOf("ft", "foot", "feet"),
            listOf("in", "inch", "inches")
    )

    private val weightTypes = listOf(
            listOf("g", "gram", "grams"),
            listOf("kg", "kilogram", "kilograms"),
            listOf("mg", "milligram", "milligrams"),
            listOf("lb", "pound", "pounds"),
            listOf("oz", "ounce", "ounces")
    )

    fun isLengthType(type: String): Boolean {
        return lengthTypes.any { it.contains(type) }
    }

    fun isWeightType(type: String): Boolean {
        return weightTypes.any { it.contains(type) }
    }

    fun getFullTypeNameBasedOnValue(type: String, value: Double): String {
        var row = lengthTypes.find { it.contains(type) }
        if (row == null) {
            row = weightTypes.find { it.contains(type) }
        }

        return when {
            row == null -> {
                "???"
            }
            value == 1.0 -> {
                row[1]
            }
            else -> {
                row[2]
            }
        }
    }

    fun toAnotherLength(currentType: String, newType: String, value: Double): Double {
        val result = when (currentType) {
            in lengthTypes[0] -> value
            in lengthTypes[1] -> kilometersToMeters(value)
            in lengthTypes[2] -> centimetersToMeters(value)
            in lengthTypes[3] -> millimetersToMeters(value)
            in lengthTypes[4] -> milesToMetres(value)
            in lengthTypes[5] -> yardsToMeters(value)
            in lengthTypes[6] -> feetToMeters(value)
            in lengthTypes[7] -> inchesToMeters(value)
            else -> throw  IllegalArgumentException()
        }

        return when (newType) {
            in lengthTypes[0] -> result
            in lengthTypes[1] -> metersToKilometers(result)
            in lengthTypes[2] -> metersToCentimeters(result)
            in lengthTypes[3] -> metersToMillimeters(result)
            in lengthTypes[4] -> metersToMiles(result)
            in lengthTypes[5] -> metersToYards(result)
            in lengthTypes[6] -> metersToFeet(result)
            in lengthTypes[7] -> metersToInches(result)
            else -> throw  IllegalArgumentException()
        }
    }

    fun toAnotherWeight(currentType: String, newType: String, value: Double): Double {
        val result = when (currentType) {
            in weightTypes[0] -> value
            in weightTypes[1] -> kilogramsToGrams(value)
            in weightTypes[2] -> milligramsToGrams(value)
            in weightTypes[3] -> poundsToGrams(value)
            in weightTypes[4] -> ouncesToGrams(value)
            else -> throw  IllegalArgumentException()
        }

        return when (newType) {
            in weightTypes[0] -> result
            in weightTypes[1] -> gramsToKilograms(result)
            in weightTypes[2] -> gramsToMilligrams(result)
            in weightTypes[3] -> gramsToPounds(result)
            in weightTypes[4] -> gramsToOunces(result)
            else -> throw  IllegalArgumentException()
        }
    }

    fun kilometersToMeters(kilometers: Double) = kilometers * 1000
    fun metersToKilometers(meters: Double) = meters / 1000
    fun centimetersToMeters(centimeters: Double) = centimeters * 0.01
    fun metersToCentimeters(meters: Double) = meters / 0.01
    fun millimetersToMeters(millimeters: Double) = millimeters * 0.001
    fun metersToMillimeters(meters: Double) = meters / 0.001
    fun milesToMetres(miles: Double) = miles * 1609.35
    fun metersToMiles(meters: Double) = meters / 1609.35
    fun yardsToMeters(yards: Double) = yards * 0.9144
    fun metersToYards(meters: Double) = meters / 0.9144
    fun feetToMeters(foot: Double) = foot * 0.3048
    fun metersToFeet(meters: Double) = meters / 0.3048
    fun inchesToMeters(inches: Double) = inches * 0.0254
    fun metersToInches(meters: Double) = meters / 0.0254

    fun kilogramsToGrams(kilograms: Double) = kilograms * 1000
    fun gramsToKilograms(grams: Double) = grams / 1000
    fun milligramsToGrams(milligrams: Double) = milligrams * 0.001
    fun gramsToMilligrams(grams: Double) = grams / 0.001
    fun poundsToGrams(pounds: Double) = pounds * 453.592
    fun gramsToPounds(grams: Double) = grams / 453.592
    fun ouncesToGrams(ounces: Double) = ounces * 28.3495
    fun gramsToOunces(grams: Double) = grams / 28.3495
}