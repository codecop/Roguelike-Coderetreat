package main

import (
	"encoding/json"
	"net/http"
	"roguelike-go/dungeon_api"
	"roguelike-go/room"
	"strconv"
)

func main() {
	newRoom := room.NewRoom(8, 6, []room.Coordinate{{X: 5, Y: 5}})
	player := room.NewPlayer(room.Coordinate{X: 2, Y: 3}, '@')
	newRoom.AddPlayer(&player)

	http.HandleFunc("/room", func(w http.ResponseWriter, r *http.Request) {
		res := dungeon_api.GetRoomMap(&newRoom)
		w.Header().Set("Content-Type", "application/json")
		if err := json.NewEncoder(w).Encode(res); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		}
	})

	http.HandleFunc("/room/walk", func(w http.ResponseWriter, r *http.Request) {
		rowStr := r.URL.Query().Get("row")
		colStr := r.URL.Query().Get("column")

		row, _ := strconv.ParseInt(rowStr, 10, 64)
		col, _ := strconv.ParseInt(colStr, 10, 64)

		newRoom.SetNewPosition(row, col)
	})
	http.ListenAndServe(":8080", nil)

}
