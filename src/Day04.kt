import kotlin.math.pow

fun main() {
    data class ScratchCard(
        val winning: Int,
        var copies: Int = 1
    )

    fun parse(input: List<String>): List<ScratchCard> {
        return input.map { line ->
            val numbers = line.split(": ")[1].split(" | ").map { it.trim().split("\\s+".toRegex()) }

            val winning = numbers[0].map { it.toInt() }.toSet()
            val scratched = numbers[1].map { it.toInt() }.toSet()

            ScratchCard(winning.intersect(scratched).size)
        }
    }

    fun part1(input: List<String>): Int {
        return parse(input).sumOf { scratchCard ->
            when (val amount = scratchCard.winning) {
                0 -> 0
                1 -> 1
                else -> 2.0.pow(amount - 1).toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val scratchCards = parse(input)

        scratchCards.forEachIndexed { index, scratchCard ->
            for (i in 1..scratchCard.winning) {
                scratchCards[index + i].copies += scratchCard.copies
            }
        }

        return scratchCards.sumOf { it.copies }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
