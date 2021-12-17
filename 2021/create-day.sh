#! /bin/sh

DAY="$*"

mkdir -p "${DAY}/data"
touch "${DAY}/data/input.txt"
touch "${DAY}/data/example.txt"
echo "package ${DAY}

import (
	\"fmt\"
	\"github.com/jvgrootveld/advent-of-code/avent2021/shared\"
)

func Part01(input []string) int {
	defer shared.Elapsed(\"${DAY} Part02\")()

	fmt.Println(\"HO HO HO!\")

	return 0
}

func Part02(input []string) int {
	defer shared.Elapsed(\"${DAY} Part02\")()

	fmt.Println(\"HO HO HO!\")

	return 0
}
" > "${DAY}/${DAY}.go"


echo "package ${DAY}

import (
	\"github.com/jvgrootveld/advent-of-code/avent2021/shared\"
	\"testing\"
)

type testResult struct {
	name     string
	expected int
}

func TestPart01(t *testing.T) {
	var tests = []testResult{
		{\"example\", -1},
		//{\"input\", -1},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings(\"data/\" + test.name + \".txt\")
			result := Part01(content)

			t.Log(\"Result: \", result)
			if result != test.expected {
				t.Fatalf(\"Incorrect answer. Got '%v' expected '%v'\", result, test.expected)
			}
		})
	}
}

func TestPart02(t *testing.T) {
	var tests = []testResult{
		{\"example\", -1},
		//{\"input\", -1},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings(\"data/\" + test.name + \".txt\")
			result := Part02(content)

			t.Log(\"Result: \", result)
			if result != test.expected {
				t.Fatalf(\"Incorrect answer. Got '%v' expected '%v'\", result, test.expected)
			}
		})
	}
}
" > "${DAY}/${DAY}_test.go"