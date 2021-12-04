#! /bin/sh

DAY="$*"

mkdir "${DAY}"
touch "${DAY}/input.txt"
touch "${DAY}/input_example.txt"
echo "package ${DAY}

import (
	\"fmt\"
)

func Part01(input []int) int {
	fmt.Println(\"HO HO HO!\")

	return 0
}

func Part02(input []int) int {
	fmt.Println(\"HO HO HO!\")

	return 0
}
" > "${DAY}/${DAY}.go"


echo "package ${DAY}

import (
	\"fmt\"
	\"testing\"
	\"github.com/jvgrootveld/advent-of-code/avent2021/shared\"
)

func TestPart01(t *testing.T) {
	content := shared.ReadFileAsInts(\"input_example.txt\")

	result := Part01(content)
	answer := -1

	fmt.Println(\"Result: \", result)
	if result != answer {
		t.Fatalf(\"Incorrect answer. Got '%v' expected '%v'\", result, answer)
	}
}

func TestPart02(t *testing.T) {
	content := shared.ReadFileAsInts(\"input_example.txt\")

	result := Part02(content)
	answer := -1

	fmt.Println(\"Result: \", result)
	if result != answer {
		t.Fatalf(\"Incorrect answer. Got '%v' expected '%v'\", result, answer)
	}
}
" > "${DAY}/${DAY}_test.go"