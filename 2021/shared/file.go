package shared

import (
	"bufio"
	"io"
	"log"
	"os"
	"strconv"
)

// ReadFileAsInts reads and return content as []int. Fail on error
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
