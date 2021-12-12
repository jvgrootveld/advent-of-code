package day12

import (
	"fmt"
	"strings"
)

type CaveType int

const (
	Small CaveType = iota
	Big
)

type Cave struct {
	Name           string
	Type           CaveType
	ConnectedCaves []*Cave
}

func NewCave(name string) *Cave {
	return &Cave{
		Name: name,
		Type: typeFromName(name),
	}
}

func (c *Cave) addConnection(cave *Cave) {
	c.ConnectedCaves = append(c.ConnectedCaves, cave)
	cave.ConnectedCaves = append(cave.ConnectedCaves, c) // And back reference
}

func (c *Cave) String() string {
	var connections []string
	for _, c := range c.ConnectedCaves {
		connections = append(connections, c.Name)
	}

	return fmt.Sprintf("[%s]: %s", c.Name, strings.Join(connections, ","))
}

func (c *Cave) IsStartOrEnd() bool {
	return c.Name == "start" || c.Name == "end"
}

func typeFromName(name string) CaveType {
	if name == strings.ToLower(name) {
		return Small
	}

	return Big
}
