package main

import (
	"fmt"
	"hello"
	"net/http"
)

func main() {
	http.HandleFunc("/get_name", hello.GetName)
	http.HandleFunc("/set_name", hello.SetName)

	fmt.Println("Server is listening on :8080")
	http.ListenAndServe(":8080", nil)
}
