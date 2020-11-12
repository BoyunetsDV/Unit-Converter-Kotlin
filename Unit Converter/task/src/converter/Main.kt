package converter

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    print("Enter a number and a measure of length: ")
    val number = scanner.nextDouble()
    when (scanner.next().toLowerCase()) {
        in "m", "meter", "meters" -> UnitConverter.meterToMeter(number)
        in "km", "kilometer", "kilometers" -> UnitConverter.kilometersToMeters(number)
        in "cm", "centimeter", "centimeters" -> UnitConverter.centimetersToMeters(number)
        in "mm", "millimeter", "millimeters" -> UnitConverter.millimetersToMeters(number)
        in "mi", "mile", "miles" -> UnitConverter.milesToMetres(number)
        in "yd", "yard", "yards" -> UnitConverter.yardsToMeters(number)
        in "ft", "foot", "feet" -> UnitConverter.feetToMeters(number)
        in "in", "inch", "inches" -> UnitConverter.inchesToMeters(number)
    }
}

object UnitConverter {
    fun meterToMeter(meter: Double) {
        println("$meter ${if (meter == 1.0) "meter" else "meters"} " +
                "is $meter ${if (meter == 1.0) "meter" else "meters"}")
    }

    fun kilometersToMeters(kilometers: Double) {
        val result = kilometers * 1000
        println("$kilometers ${if (kilometers == 1.0) "kilometer" else "kilometers"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }

    fun centimetersToMeters(centimeters: Double) {
        val result = centimeters * 0.01
        println("$centimeters ${if (centimeters == 1.0) "centimeter" else "centimeters"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }

    fun millimetersToMeters(millimeters: Double) {
        val result = millimeters * 0.001
        println("$millimeters ${if (millimeters == 1.0) "millimeter" else "millimeters"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }

    fun milesToMetres(miles: Double) {
        val result = miles * 1609.35
        println("$miles ${if (miles == 1.0) "mile" else "miles"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }

    fun yardsToMeters(yards: Double) {
        val result = yards * 0.9144
        println("$yards ${if (yards == 1.0) "yard" else "yards"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }

    fun feetToMeters(foot: Double) {
        val result = foot * 0.3048
        println("$foot ${if (foot == 1.0) "foot" else "feet"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }

    fun inchesToMeters(inches: Double) {
        val result = inches * 0.0254
        println("$inches ${if (inches == 1.0) "inch" else "inches"} " +
                "is $result ${if (result == 1.0) "meter" else "meters"}")
    }
}