package shared

import (
	"bufio"
	"io"
	"log"
	"os"
	"strconv"
	"strings"
)

// ReadFileAsInts reads and return content as []int. Fails on error.
func ReadFileAsInts(file string) []int {
	f, err := os.Open(file)
	if err != nil {
		log.Fatalln(err)
	}
	defer f.Close()

	result, err := readInts(f)
	if err != nil {
		log.Fatalln(err)
	}

	return result
}

func readInts(r io.Reader) ([]int, error) {
	var result []int

	scanner := bufio.NewScanner(r)
	for scanner.Scan() {
		n, err := strconv.Atoi(scanner.Text())
		if err != nil {
			return nil, err
		}
		result = append(result, n)
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return result, nil
}

// ReadFileAsCommaInts reads, splits on comma and return content as []int. Fails on error.
func ReadFileAsCommaInts(file string) []int {
	f, err := os.Open(file)
	if err != nil {
		log.Fatalln(err)
	}
	defer f.Close()

	rows, err := readStrings(f)
	if err != nil {
		log.Fatalln(err)
	} else if len(rows) > 1 {
		log.Fatalln("Expect only one row. Got: ", len(rows))
	}

	var result []int
	for _, item := range strings.Split(rows[0], ",") {
		number, _ := strconv.Atoi(item)
		result = append(result, number)
	}

	return result
}

// ReadFileAsIntGrid reads and return content as a grid of [][]int. Fails on error.
func ReadFileAsIntGrid(file string) [][]int {
	f, err := os.Open(file)
	if err != nil {
		log.Fatalln(err)
	}
	defer f.Close()

	rows, err := readStrings(f)
	if err != nil {
		log.Fatalln(err)
	}

	var result [][]int

	for _, row := range rows {
		rowInts := make([]int, len(row))

		for i, point := range row {
			rowInts[i] = int(point - '0') // Convert rune/char to real int value
		}

		result = append(result, rowInts)
	}

	return result
}

// ReadFileAsStrings reads and return content as []string. Fails on error.
func ReadFileAsStrings(file string) []string {
	f, err := os.Open(file)
	if err != nil {
		log.Fatalln(err)
	}
	defer f.Close()

	result, err := readStrings(f)
	if err != nil {
		log.Fatalln(err)
	}

	return result
}

func readStrings(r io.Reader) ([]string, error) {
	var result []string

	scanner := bufio.NewScanner(r)
	for scanner.Scan() {
		result = append(result, scanner.Text())
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return result, nil
}
