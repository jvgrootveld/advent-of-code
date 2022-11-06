# Advent of Code 2022
My solutions to the [Advent of Code 2022](https://adventofcode.com/2022) puzzles.

## Test

Run test in IDE or invoke command:

```sh
gradle test --tests Day<padded day number>Test --info

# Example
gradle test --tests Day01Test --info
```

## Create new day boilerplate

```sh
gradle addDay -Pday=<day number>

# Example
gradle addDay -Pday=1
```