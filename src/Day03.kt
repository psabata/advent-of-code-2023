import java.lang.StringBuilder

fun main() {
    data class Point(
        val x: Int,
        val y: Int,
    ) {
        fun neighbours(): Set<Point> {
            return mutableSetOf<Point>().apply {
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        add(Point(x + dx, y + dy))
                    }
                }
            }
        }
    }

    data class Number(
        val value: Int,
        val points: List<Point>,
    ) {
        fun adjacentToAny(symbolsPositions: Set<Point>): Boolean {
            val neighbours = mutableSetOf<Point>().apply { points.forEach { addAll(it.neighbours()) } }
            return (neighbours intersect symbolsPositions).isNotEmpty()
        }
    }

    data class Symbol(
        val value: Char,
        val point: Point,
    ) {
        fun gearRatio(numbers: List<Number>): Int {
            val neighbours = point.neighbours()
            val nextTo = numbers.filter { (it.points intersect neighbours).isNotEmpty() }

            if (nextTo.size == 2) {
                nextTo.toList().let {
                    return it[0].value * it[1].value
                }
            }

            return 0
        }
    }

    data class Engine(
        val numbers: List<Number>,
        val symbols: List<Symbol>,
        val symbolsPositions: Set<Point> = symbols.map { it.point }.toSet(),
    )

    class Parser {
        private val numbers = mutableListOf<Number>()
        private val symbols = mutableListOf<Symbol>()
        private val numberBuilder = StringBuilder()
        private val numberPoints = mutableListOf<Point>()

        fun parse(input: List<String>): Engine {
            input.forEachIndexed { y, line ->
                line.forEachIndexed line@{ x, char ->
                    when {
                        char == '.' -> {
                            endNumber()
                            return@line
                        }

                        char.isDigit() -> {
                            numberBuilder.append(char)
                            numberPoints.add(Point(x, y))
                        }

                        else -> {
                            endNumber()
                            symbols.add(Symbol(char, Point(x, y)))
                        }
                    }
                }
                endNumber()
            }

            return Engine(numbers, symbols)
        }

        private fun endNumber() {
            if (numberBuilder.isNotEmpty()) {
                numbers.add(
                    Number(numberBuilder.toString().toInt(), numberPoints.toList())
                )
                numberBuilder.clear()
                numberPoints.clear()
            }
        }
    }

    fun part1(input: List<String>): Int {
        Parser().parse(input).apply {
            return numbers.sumOf {
                if (it.adjacentToAny(symbolsPositions)) it.value else 0
            }
        }
    }

    fun part2(input: List<String>): Int {
        Parser().parse(input).apply {
            return symbols.filter { it.value == '*' }
                .sumOf { it.gearRatio(numbers) }
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println("Part 1: ${part1(input)}") // 553079
    println("Part 2: ${part2(input)}") // 84363105
}
