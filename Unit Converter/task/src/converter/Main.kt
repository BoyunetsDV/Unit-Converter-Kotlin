package converter

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    do {
        print("Enter what you want to convert (or exit): ")
        val command = scanner.nextLine().toLowerCase()
        if (command == "exit") {
            continue
        }
        try {
            UnitConverter.convert(command)
        } catch (exp: Exception) {
            println(exp.message)
        }
        println()
    } while (command != "exit")
}

object UnitConverter {
    enum class Length(val shortcut: String, val singular: String, val plural: String) {
        METER("m", "meter", "meters") {
            override fun toMeters(value: Double): Double = value
            override fun fromMeters(value: Double): Double = value
        },
        KILOMETER("km", "kilometer", "kilometers") {
            override fun toMeters(value: Double): Double = value * 1000
            override fun fromMeters(value: Double): Double = value / 1000
        },
        CENTIMETER("cm", "centimeter", "centimeters") {
            override fun toMeters(value: Double): Double = value * 0.01
            override fun fromMeters(value: Double): Double = value / 0.01
        },
        MILLIMETER("mm", "millimeter", "millimeters") {
            override fun toMeters(value: Double): Double = value * 0.001
            override fun fromMeters(value: Double): Double = value / 0.001
        },
        MILE("mi", "mile", "miles") {
            override fun toMeters(value: Double): Double = value * 1609.35
            override fun fromMeters(value: Double): Double = value / 1609.35
        },
        YARD("yd", "yard", "yards") {
            override fun toMeters(value: Double): Double = value * 0.9144
            override fun fromMeters(value: Double): Double = value / 0.9144
        },
        FOOT("ft", "foot", "feet") {
            override fun toMeters(value: Double): Double = value * 0.3048
            override fun fromMeters(value: Double): Double = value / 0.3048
        },
        INCH("in", "inch", "inches") {
            override fun toMeters(value: Double): Double = value * 0.0254
            override fun fromMeters(value: Double): Double = value / 0.0254
        };

        abstract fun toMeters(value: Double): Double
        abstract fun fromMeters(value: Double): Double

        companion object {
            fun findByName(name: String): Length = values().first { it.shortcut == name || it.singular == name || it.plural == name }
            fun isLength(name: String): Boolean = values().any { it.shortcut == name || it.singular == name || it.plural == name }
        }
    }

    enum class Weight(val shortcut: String, val singular: String, val plural: String) {
        GRAM("g", "gram", "grams") {
            override fun toGrams(value: Double): Double = value
            override fun fromGrams(value: Double): Double = value
        },
        KILOGRAM("kg", "kilogram", "kilograms") {
            override fun toGrams(value: Double): Double = value * 1000
            override fun fromGrams(value: Double): Double = value / 1000
        },
        MILLIGRAM("mg", "milligram", "milligrams") {
            override fun toGrams(value: Double): Double = value * 0.001
            override fun fromGrams(value: Double): Double = value / 0.001
        },
        POUND("lb", "pound", "pounds") {
            override fun toGrams(value: Double): Double = value * 453.592
            override fun fromGrams(value: Double): Double = value / 453.592
        },
        OUNCE("oz", "ounce", "ounces") {
            override fun toGrams(value: Double): Double = value * 28.3495
            override fun fromGrams(value: Double): Double = value / 28.3495
        };

        abstract fun toGrams(value: Double): Double
        abstract fun fromGrams(value: Double): Double

        companion object {
            fun findByName(name: String): Weight = values().first { it.shortcut == name || it.singular == name || it.plural == name }
            fun isWeight(name: String): Boolean = values().any { it.shortcut == name || it.singular == name || it.plural == name }
        }
    }

    enum class Temperature(val shortcut: String, val altShortcut: String, val singular: String, val altSingular: String, val plural: String) {
        FAHRENHEIT("f", "df", "fahrenheit", "degree Fahrenheit", "degrees Fahrenheit") {
            override fun toFahrenheit(value: Double): Double = value
            override fun fromFahrenheit(value: Double): Double = value
        },
        CELSIUS("c", "dc", "celsius", "degree Celsius", "degrees Celsius") {
            override fun toFahrenheit(value: Double): Double = value * 1.8 + 32.0
            override fun fromFahrenheit(value: Double): Double = (value - 32.0) / 1.8
        },
        KELVINS("k", "k", "kelvin", "kelvin", "kelvins") {
            override fun toFahrenheit(value: Double): Double = (value - 273.15) * 1.8 + 32
            override fun fromFahrenheit(value: Double): Double = (value - 32.0) / 1.8 + 273.15
        };

        abstract fun toFahrenheit(value: Double): Double
        abstract fun fromFahrenheit(value: Double): Double

        companion object {
            fun findByName(name: String): Temperature = values().first {
                it.shortcut == name || it.altShortcut == name
                        || it.singular == name || it.altSingular.toLowerCase() == name || it.plural.toLowerCase() == name
            }

            fun isTemperature(name: String): Boolean = values().any {
                it.shortcut == name || it.altShortcut == name
                        || it.singular == name || it.altSingular.toLowerCase() == name || it.plural.toLowerCase() == name
            }
        }
    }

    fun convert(textCommand: String) {
        val regex = Regex("^(-?\\d*\\.?\\d*?) ([a-zA-Z]* ?[a-zA-Z]*?) (in|to) ([a-zA-Z]* ?[a-zA-Z]*?\$)")
        if (!textCommand.matches(regex)) {
            throw IllegalArgumentException("Parse error")
        }
        var commandsList = regex.matchEntire(textCommand)!!.groupValues.filter { it.isNotBlank() && it.isNotEmpty() }
        val value = commandsList[1].toDouble()
        val originalType = getType(commandsList[2])
        val targetType = getType(commandsList[4])

        if (value < 0 && originalType is Weight) {
            throw IllegalArgumentException("Weight shouldn't be negative")
        } else if (value < 0 && originalType is Length) {
            throw IllegalArgumentException("Length shouldn't be negative")
        } else if (originalType is Weight && targetType is Weight) {
            val result = targetType.fromGrams(originalType.toGrams(value))
            println("$value " +
                    "${if (value == 1.0) originalType.singular else originalType.plural} " +
                    "is $result ${if (result == 1.0) targetType.singular else targetType.plural}")
        } else if (originalType is Temperature && targetType is Temperature) {
            val result = targetType.fromFahrenheit(originalType.toFahrenheit(value))
            println("$value " +
                    "${if (value == 1.0) originalType.altSingular else originalType.plural} " +
                    "is $result ${if (result == 1.0) targetType.altSingular else targetType.plural}")
        } else if (originalType is Length && targetType is Length) {
            val result = targetType.fromMeters(originalType.toMeters(value))
            println("$value " +
                    "${if (value == 1.0) originalType.singular else originalType.plural} " +
                    "is $result ${if (result == 1.0) targetType.singular else targetType.plural}")
        } else {
            val originalTypePluralName = getPluralValueFromType(originalType)
            val targetTypePluralName = getPluralValueFromType(targetType)
            throw java.lang.IllegalArgumentException("Conversion from $originalTypePluralName " +
                    "to $targetTypePluralName is impossible")
        }
    }

    private fun getType(text: String): Any? {
        return when {
            Length.isLength(text) -> Length.findByName(text)
            Weight.isWeight(text) -> Weight.findByName(text)
            Temperature.isTemperature(text) -> Temperature.findByName(text)
            else -> null
        }
    }

    private fun getPluralValueFromType(type: Any?): String {
        return when (type) {
            is Length -> type.plural
            is Weight -> type.plural
            is Temperature -> type.plural
            else -> "???"
        }
    }
}