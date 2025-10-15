package utils

import (
	"roguelike-go/room"
	"strings"
)

func RoomToString(r *room.Room) string {
	lines := make([]string, len(r.RoomMap))
	for i, row := range r.RoomMap {
		lines[i] = string(row)
	}
	return strings.Join(lines, "\n")
}
