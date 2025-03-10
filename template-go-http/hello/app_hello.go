package hello

import (
	"encoding/json"
	"net/http"
	"sync"
)

var (
	mu    sync.Mutex
	hello Hello = NewHello("World!")
)

type NameCall struct {
	Name string `json:"name"`
}

func GetName(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	defer mu.Unlock()
	response := NameCall{Name: hello.GetName()}
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(response)
}

func SetName(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	defer mu.Unlock()
	newName := r.URL.Query().Get("name")
	if newName != "" {
		// Update the name
		hello.SetName(newName)
		w.WriteHeader(http.StatusCreated)
	} else {
		http.Error(w, "Invalid request data", http.StatusBadRequest)
	}
}
