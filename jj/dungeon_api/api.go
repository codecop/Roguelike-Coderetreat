package dungeon_api

import (
	"roguelike-go/room"
	"roguelike-go/utils"
)

type RoomMapResponse struct {
	Layout      string `json:"layout"`
	Description string `json:"description"`
}

func GetRoomMap(newRoom *room.Room) RoomMapResponse {

	return RoomMapResponse{Layout: utils.RoomToString(newRoom), Description: newRoom.Description}

}

type WalkResponse struct {
	Message string `json:"message"`
}

func Walk(newRoom *room.Room, row int64, col int64) WalkResponse {
	newRoom.SetNewPosition(row, col)

	return WalkResponse{Message: newRoom.GetMessage()}
}
