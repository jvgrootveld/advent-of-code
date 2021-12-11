package day10

import (
	"errors"
	"sync"
)

var ErrIsEmpty = errors.New("stack is empty")

type SignStack struct {
	stack []Sign
	lock  sync.RWMutex
}

func NewSignStack() *SignStack {
	return &SignStack{}
}

func (s *SignStack) Stack() []Sign {
	return s.stack
}

func (s *SignStack) Size() int {
	return len(s.stack)
}

func (s *SignStack) Empty() bool {
	return len(s.stack) == 0
}

func (s *SignStack) Last() (Sign, error) {
	if s.Empty() {
		return Sign{}, ErrIsEmpty
	}
	return s.stack[len(s.stack)-1], nil
}

func (s *SignStack) First() (Sign, error) {
	if s.Empty() {
		return Sign{}, ErrIsEmpty
	}
	return s.stack[0], nil
}

func (s *SignStack) Push(value Sign) {
	s.lock.Lock()
	defer s.lock.Unlock()
	s.stack = append(s.stack, value)
}

func (s *SignStack) Pop() (Sign, error) {
	s.lock.Lock()
	defer s.lock.Unlock()

	lastItem, err := s.Last()
	if err != nil {
		return Sign{}, err
	}

	s.stack = s.stack[:s.Size()-1]

	return lastItem, nil
}

func (s *SignStack) Clear() {
	s.stack = make([]Sign, 0)
}
