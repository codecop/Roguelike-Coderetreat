package room

import "fmt"

type Coordinate struct {
	X int64
	Y int64
}

func NewCoordinate(x int64, y int64) Coordinate {
	return Coordinate{X: x, Y: y}

}

type Player struct {
	Position Coordinate
	Symbol   rune
}

func NewPlayer(position Coordinate, symbol rune) Player {
	return Player{Position: position, Symbol: symbol}

}

type Room struct {
	sizeX             int64
	sizeY             int64
	doors_coordinates []Coordinate
	roomMap           [][]rune
	player            *Player
}

func NewRoom(sizeX int64, sizeY int64, doors_coordinates []Coordinate) Room {
	// build room map
	// fill with walls
	roomMap := make([][]rune, sizeY)
	for i := range roomMap {

		roomMap[i] = make([]rune, sizeX)
		for j := range roomMap[i] {
			roomMap[i][j] = '#'
		}
	}
	// fill with empty space
	for i := int64(1); i < sizeY-1; i++ {
		for j := int64(1); j < sizeX-1; j++ {
			roomMap[i][j] = ' '
		}
	}
	// add doors
	for _, door := range doors_coordinates {
		if door.X == 0 || door.X == sizeX-1 || door.Y == 0 || door.Y == sizeY-1 {
			roomMap[door.Y][door.X] = '|'
		}
	}

	return Room{sizeX: sizeX, sizeY: sizeY, doors_coordinates: doors_coordinates, roomMap: roomMap}

}

func (r *Room) SetNewPosition(row int64, column int64) {
	c := NewCoordinate(row, column)
	r.player.Position = c
}

func (r *Room) AddPlayer(player *Player) {
	r.player = player
	r.roomMap[player.Position.X][r.player.Position.Y] = player.Symbol
}

func Print(r Room) {
	for _, rm := range r.roomMap {
		for _, val := range rm {
			fmt.Printf("%c", val)
		}
		fmt.Printf("\n")
	}

}

// [["########",]
// ["#.......#",]
// ["#.......#",]
// ["#.......#",]
// ["#.......#",]
// ["#####|###",]]

// func (h *Hello) GetName() string {
// 	return h.name
// }

// func (h *Hello) SetName(name string) {
// 	h.name = name
// }
