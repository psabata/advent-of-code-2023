fun main() {

    data class Ranges(
        val sourceRange: LongRange,
        val destination: Long,
    )

    data class Mapper(
        val ranges: List<Ranges>
    ) {
        fun map(value: Long): Long {
            ranges.forEach { range ->
                if (range.sourceRange.contains(value)) {
                    return range.destination + value - range.sourceRange.first
                }
            }

            return value
        }
    }

    data class Almanac(
        val seeds: List<Long>,
        val mappers: List<Mapper>,
    ) {
        fun closestLocation(): Long {
            return seeds.minOf { seed ->
                evaluate(seed)
            }
        }

        fun closestSeedInputLocation(): Long {
            return seeds.chunked(2).minOf { seedRange ->
                (seedRange[0]..seedRange[0] + seedRange[1]).minOf { seed ->
                    evaluate(seed)
                }
            }
        }

        private fun evaluate(seed: Long): Long {
            return mappers.fold(seed) { value, mapper -> mapper.map(value) }
        }
    }

    fun parse(input: List<String>): Almanac {
        val seeds = input[0].substringAfter("seeds: ").split(' ').map { it.toLong() }
        val mappers = mutableListOf<Mapper>()
        var ranges: MutableList<Ranges> = mutableListOf()

        input.subList(1, input.size).forEach { line ->
            when {
                line.isBlank() -> {}

                line[0].isLetter() -> { // seed-to-soil map:
                    ranges = mutableListOf()
                    mappers.add(Mapper(ranges))
                }

                line[0].isDigit() -> { // 50 98 2
                    val numbers = line.split(' ').map { it.toLong() }
                    ranges.add(
                        Ranges(
                            sourceRange = LongRange(numbers[1], numbers[1] + numbers[2]),
                            destination = numbers[0]
                        )
                    )
                }
            }
        }

        return Almanac(seeds, mappers)
    }


    fun part1(input: List<String>): Long {
        val almanac = parse(input)

        return almanac.closestLocation()
    }

    fun part2(input: List<String>): Long {
        val almanac = parse(input)

        return almanac.closestSeedInputLocation()
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}