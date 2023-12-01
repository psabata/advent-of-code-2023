fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            ("" + it.first { ch -> ch.isDigit() } + it.last { ch -> ch.isDigit() }).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val mapping = listOf(
            listOf("0", "zero"),
            listOf("1", "one"),
            listOf("2", "two"),
            listOf("3", "three"),
            listOf("4", "four"),
            listOf("5", "five"),
            listOf("6", "six"),
            listOf("7", "seven"),
            listOf("8", "eight"),
            listOf("9", "nine"),
        )

        fun firstDigit(input: String): Int {
            var minIndex = Integer.MAX_VALUE
            var currentValue = 0

            mapping.forEachIndexed { value, items ->
                val index = input.indexOfAny(items)

                if (index >= 0 && index < minIndex) {
                    minIndex = index
                    currentValue = value
                }
            }

            return currentValue
        }

        fun lastDigit(input: String): Int {
            var maxIndex = Integer.MIN_VALUE
            var currentValue = 0

            mapping.forEachIndexed { value, items ->
                val index = input.lastIndexOfAny(items)

                if (index >= 0 && index > maxIndex) {
                    maxIndex = index
                    currentValue = value
                }
            }

            return currentValue
        }

        return input.sumOf {
            ("" + firstDigit(it) + lastDigit(it)).toInt()
        }
    }

    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
