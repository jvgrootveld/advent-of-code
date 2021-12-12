package day12

type CavePath struct {
	HasTimeToVisitSmallCave bool
	Path                    []*Cave
}

func NewCavePath(start *Cave, hasTimeToVisitSmallCave bool) *CavePath {
	return &CavePath {
		Path: []*Cave{start},
		HasTimeToVisitSmallCave: hasTimeToVisitSmallCave,
	}
}

func (c *CavePath) ContainsCave(caveToFind *Cave) bool {
	for _, cave := range c.Path {
		if cave == caveToFind {
			return true
		}
	}

	return false
}

func (c *CavePath) AddCave(cave *Cave) {
	c.Path = append(c.Path, cave)
}
