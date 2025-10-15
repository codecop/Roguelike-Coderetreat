package room

import (
	"fmt"
	"os"
)

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
	Hp       int64
	hasKey   bool
}

func (p *Player) AbsorbDamage(damage int64) {
	if damage > p.Hp {
		fmt.Println("💀 You have died. Game over.")
		os.Exit(1)
	}
	p.Hp = p.Hp - damage
}

type Monster struct {
	Position Coordinate
	Symbol   rune
}

func NewMonster(position Coordinate, symbol rune) Monster {
	return Monster{Position: position, Symbol: symbol}
}

type Key struct {
	Position Coordinate
	Symbol   rune
}

func NewKey(position Coordinate, symbol rune) Key {
	return Key{Position: position, Symbol: symbol}
}

func NewPlayer(position Coordinate, symbol rune) Player {
	return Player{Position: position, Symbol: symbol, Hp: 100, hasKey: false}
}

type Room struct {
	sizeX       int64
	sizeY       int64
	Description string
	Name        string

	doors_coordinates Coordinate

	player  *Player
	monster *Monster
	key     *Key

	Messages []string

	RoomMap [][]rune
}

var RoomMap = make(map[string]*Room)

func NewRoom(name string, description string, sizeX int64, sizeY int64, doors_coordinates Coordinate) Room {
	room := Room{Name: name, Description: description, sizeX: sizeX, sizeY: sizeY, doors_coordinates: doors_coordinates}
	room.builMap()
	RoomMap[name] = &room
	return room
}
func (r *Room) GetMessage() string {
	return r.Messages[len(r.Messages)-1]
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

	roomMap[r.doors_coordinates.X][r.doors_coordinates.Y] = '|'

	if r.player != nil {
		roomMap[r.player.Position.X][r.player.Position.Y] = r.player.Symbol
	}
	if r.monster != nil {
		roomMap[r.monster.Position.X][r.monster.Position.Y] = r.monster.Symbol
	}

	r.RoomMap = roomMap
}

func (r *Room) SetNewPosition(row int64, column int64) {
	c := NewCoordinate(row, column)
	r.player.Position = c
	r.builMap()
	if r.monster != nil && r.player != nil {
		dx := r.player.Position.X - r.monster.Position.X
		if dx < 0 {
			dx = -dx
		}
		dy := r.player.Position.Y - r.monster.Position.Y
		if dy < 0 {
			dy = -dy
		}
		distance := dx + dy

		if distance <= 1 {
			r.Messages = append(r.Messages, fmt.Sprintf("You were attacked... losing 20 HP, current HP is %d", r.player.Hp))
			r.player.Hp = r.player.Hp - 20
		} else if distance <= 3 {
			r.Messages = append(r.Messages, "You are close to the monster, be careful")
			r.player.Hp = r.player.Hp - 20
		}
	}

	if r.key != nil && r.player != nil {
		if r.player.Position.X == r.key.Position.X && r.player.Position.Y == r.key.Position.Y {
			r.Messages = append(r.Messages, "You found a key")
			r.key = nil
			r.player.hasKey = true
		}
	}

	if r.key != nil && r.player != nil {
		if r.player.Position.X == r.doors_coordinates.X && r.player.Position.Y == r.doors_coordinates.Y {
			if r.player.hasKey {
				r.Messages = append(r.Messages, "You Won!!!")
			} else {
				r.Messages = append(r.Messages, "You need a key")
			}
		}
	}
}

func (r *Room) AddPlayer(player *Player) {
	r.player = player
	r.builMap()
}

func (r *Room) AddKey(key *Key) {
	r.key = key
	r.builMap()
}

func (r *Room) AddMonster(monster *Monster) {
	r.monster = monster
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
