package day14

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"testing"
)

type testResult struct {
	name     string
	steps    int
	expected int
}

func TestPart01(t *testing.T) {
	var tests = []testResult{
		{"example", 10, 1588},
		{"input", 10, 3009},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings("data/" + test.name + ".txt")
			result := Part01(content, test.steps)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}

func TestPart02(t *testing.T) {
	var tests = []testResult{
		{"example", 10, 1588},
		{"input", 10, 3009},
		{"example", 40, 2_188_189_693_529},
		{"input", 40, 3_459_822_539_451},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings("data/" + test.name + ".txt")
			result := Part02(content, test.steps)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}
