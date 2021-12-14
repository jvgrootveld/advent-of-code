package day13

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"strings"
	"testing"
)

type testResult struct {
	name     string
	expected int
}

func TestPart01(t *testing.T) {
	var tests = []testResult{
		{"example", 17},
		{"input", 693},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings("data/" + test.name + ".txt")
			result := Part01(content)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}

type testResultPart2 struct {
	name     string
	expected string
}

func TestPart02(t *testing.T) {
	var tests = []testResultPart2{
		{"example", `
 # # # # #
 # . . . #
 # . . . #
 # . . . #
 # # # # #
 . . . . .
 . . . . .
`},
		{"input", `
 # . . # . . # # . . # . . . . # # # # . # # # . . . # # . . # # # # . # . . # .
 # . . # . # . . # . # . . . . . . . # . # . . # . # . . # . . . . # . # . . # .
 # . . # . # . . . . # . . . . . . # . . # . . # . # . . # . . . # . . # . . # .
 # . . # . # . . . . # . . . . . # . . . # # # . . # # # # . . # . . . # . . # .
 # . . # . # . . # . # . . . . # . . . . # . # . . # . . # . # . . . . # . . # .
 . # # . . . # # . . # # # # . # # # # . # . . # . # . . # . # # # # . . # # . .
`},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings("data/" + test.name + ".txt")
			result := Part02(content)

			t.Logf("Result:\n%v", result)
			expected := strings.TrimPrefix(test.expected, "\n")
			if result != expected {
				t.Fatalf("Incorrect answer. \nGot\n%v\nexpected\n%v\n", result, expected)
			}
		})
	}
}

