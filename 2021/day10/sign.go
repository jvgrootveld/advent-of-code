package day10

type Sign struct {
	Open  rune
	Close rune

	PointsPart1 int
	PointsPart2 int
}

func (s *Sign) isCharMatch(char rune) bool {
	return s.Open == char || s.Close == char
}

func (s *Sign) String() string {
	return string(s.Open) + string(s.Close)
}

var round = Sign{'(', ')', 3, 1}
var square = Sign{'[', ']', 57, 2}
var curly = Sign{'{', '}', 1197, 3}
var angle = Sign{'<', '>', 25137, 4}

var signMap = map[rune]Sign{
	round.Open:   round,
	round.Close:   round,
	square.Open: square,
	square.Close: square,
	curly.Open:   curly,
	curly.Close:  curly,
	angle.Open:   angle,
	angle.Close:  angle,
}