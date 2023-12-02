import kotlin.math.max

fun main() {
    data class CubeSet(
        val red: Int,
        val green: Int,
        val blue: Int,
    )

    data class Game(
        val id: Int,
        val cubeSets: List<CubeSet>
    ) {
        fun isPossible(red: Int, green: Int, blue: Int): Boolean {
            return cubeSets.none {
                it.red > red || it.green > green || it.blue > blue
            }
        }

        fun minPower(): Int {
            var red = 0
            var green = 0
            var blue = 0

            cubeSets.forEach {
                red = max(red, it.red)
                green = max(green, it.green)
                blue = max(blue, it.blue)
            }

            return red * green * blue
        }
    }

    fun parseGames(input: List<String>) = input.map { line ->
        line.split(": ").let { game ->
            val gameId = game[0].split(' ')[1].toInt()
            val cubeSets = game[1].split("; ").map { cubeSet ->
                var red = 0
                var green = 0
                var blue = 0

                cubeSet.split(", ").forEach { group ->
                    group.split(" ").apply {
                        val number = this[0].toInt()
                        when (this[1]) {
                            "red" -> red += number
                            "green" -> green += number
                            "blue" -> blue += number
                        }
                    }
                }

                CubeSet(red, green, blue)
            }

            Game(gameId, cubeSets)
        }
    }

    fun part1(input: List<String>): Int {
        return parseGames(input).sumOf { game ->
            if (game.isPossible(12, 13, 14)) game.id else 0
        }
    }

    fun part2(input: List<String>): Int {
        return parseGames(input).sumOf { it.minPower() }
    }

    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1) == 8)

    val testInput2 = readInput("Day02_test2")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
