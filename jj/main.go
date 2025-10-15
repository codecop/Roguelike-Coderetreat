package main

import (
	"roguelike-go/room"
)

func main() {
	// http.HandleFunc("/get_name", hello.GetName)
	// http.HandleFunc("/set_name", hello.SetName)

	// fmt.Println("Server is listening on :8080")
	// http.ListenAndServe(":8080", nil)

	r := room.NewRoom(8, 6, []room.Coordinate{{X: 5, Y: 5}})
	player := room.NewPlayer(room.Coordinate{X: 2, Y: 3}, '@')
	r.AddPlayer(&player)
	room.Print(r)

	r.SetNewPosition(3, 4)
	room.Print(r)

	// fmt.Println(hello.Hello{name: "JJ"}.GetName())
	// fmt.Println(hello.Hello{name: "JJ"}.SetName("Juanjo"))
	// fmt.Println(hello.Hello{name: "JJ"}.GetName())
}
