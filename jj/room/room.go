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
	player            *Player

	RoomMap [][]rune
}

func NewRoom(sizeX int64, sizeY int64, doors_coordinates []Coordinate) Room {
	room := Room{sizeX: sizeX, sizeY: sizeY, doors_coordinates: doors_coordinates}
	room.builMap()
	return room

}

func (r *Room) builMap() {
	roomMap := make([][]rune, r.sizeY)

	for i := range roomMap {

		roomMap[i] = make([]rune, r.sizeX)
		for j := range roomMap[i] {
			roomMap[i][j] = '#'
		}
	}
	// fill with empty space
	for i := int64(1); i < r.sizeY-1; i++ {
		for j := int64(1); j < r.sizeX-1; j++ {
			roomMap[i][j] = ' '
		}
	}
	// add doors
	for _, door := range r.doors_coordinates {
		if door.X == 0 || door.X == r.sizeX-1 || door.Y == 0 || door.Y == r.sizeY-1 {
			roomMap[door.Y][door.X] = '|'
		}
	}
	if r.player != nil {
		roomMap[r.player.Position.X][r.player.Position.Y] = r.player.Symbol
	}
	r.RoomMap = roomMap
}

func (r *Room) SetNewPosition(row int64, column int64) {
	c := NewCoordinate(row, column)
	r.player.Position = c
	r.builMap()

}

func (r *Room) AddPlayer(player *Player) {
	r.player = player
	r.builMap()
}

func Print(r Room) {
	for _, rm := range r.RoomMap {
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
