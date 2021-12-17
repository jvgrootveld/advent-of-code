package day15

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"testing"
)

type testResult struct {
	name     string
	expected int
}

func TestPart01(t *testing.T) {
	var tests = []testResult{
		{"example", 40},
		{"input", 714},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsIntGrid("data/" + test.name + ".txt")
			result := Part01(content)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}

func TestPart02(t *testing.T) {
	var tests = []testResult{
		{"example", -1},
		//{"input", -1},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsIntGrid("data/" + test.name + ".txt")
			result := Part02(content)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}

