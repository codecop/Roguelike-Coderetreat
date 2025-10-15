package dungeon_api

import (
	"roguelike-go/room"
	"roguelike-go/utils"
)

type RoomMapResponse struct {
	Layout string `json:"layout"`
}

func GetRoomMap(newRoom *room.Room) RoomMapResponse {

	return RoomMapResponse{Layout: utils.RoomToString(newRoom)}

}
