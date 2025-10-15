package main

import (
	"encoding/json"
	"net/http"
	"roguelike-go/dungeon_api"
	"roguelike-go/room"
	"strconv"
)

func main() {

	jjRoom := room.NewRoom("jj-room", "Our first room", 15, 15, room.Coordinate{X: 14, Y: 14})
	player := room.NewPlayer(room.Coordinate{X: 1, Y: 1}, '@')
	jjRoom.AddPlayer(&player)

	key := room.NewKey(room.Coordinate{X: 12, Y: 12}, 'c')
	jjRoom.AddKey(&key)

	monster := room.NewMonster(room.Coordinate{X: 10, Y: 10}, 'M')
	jjRoom.AddMonster(&monster)

	http.HandleFunc("/jj-room", func(w http.ResponseWriter, r *http.Request) {

		// jjRoom := room.RoomMap["jj-room"]
		res := dungeon_api.GetRoomMap(&jjRoom)

		w.Header().Set("Content-Type", "application/json")
		if err := json.NewEncoder(w).Encode(res); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	})

	http.HandleFunc("/jj-room/walk", func(w http.ResponseWriter, r *http.Request) {
		rowStr := r.URL.Query().Get("row")
		colStr := r.URL.Query().Get("column")

		row, _ := strconv.ParseInt(rowStr, 10, 64)
		col, _ := strconv.ParseInt(colStr, 10, 64)

		// jjRoom := room.RoomMap["jj-room"]
		res := dungeon_api.Walk(&jjRoom, row, col)

		w.Header().Set("Content-Type", "application/json")
		if err := json.NewEncoder(w).Encode(res); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}

	})
	http.ListenAndServe(":8080", nil)

}
