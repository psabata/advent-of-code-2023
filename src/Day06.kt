fun main() {

    fun options(time: Int, distance: Int): Int {
        return (1..<time).count { button ->
            (button * (time - button)) > distance
        }
    }

    fun options(time: Long, distance: Long): Int {
        val first = (1..time).first { button ->
            (button * (time - button)) > distance
        }
        val last = (time downTo 1).first { button ->
            (button * (time - button)) > distance
        }

        return (last - first + 1).toInt()
    }


    fun part1(input: List<String>): Int {
        val times = input[0].substringAfter("Time:").trim().split("\\s+".toRegex()).map { it.toInt() }
        val distances = input[1].substringAfter("Distance:").trim().split("\\s+".toRegex()).map { it.toInt() }

        return times.foldIndexed(1) { index, result, time ->
            result * options(time, distances[index])
        }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].substringAfter("Time:").replace(" ", "").toLong()
        val distance = input[1].substringAfter("Distance:").replace(" ", "").toLong()

        return options(time, distance)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}