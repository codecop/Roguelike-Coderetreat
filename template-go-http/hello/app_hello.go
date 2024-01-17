package hello

import (
	"encoding/json"
	"io"
	"net/http"
	"sync"
)

var (
	mu   sync.Mutex
	name string = "John Doe"
)

type NameCall struct {
	Name string `json:"name"`
}

type ResponseMessage struct {
	Message string `json:"message"`
}

func GetName(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	defer mu.Unlock()
	response := NameCall{Name: name}
	jsonResponse(w, response)
}

func SetName(w http.ResponseWriter, r *http.Request) {
	mu.Lock()
	defer mu.Unlock()
	body, err := io.ReadAll(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	var request NameCall
	if err := json.Unmarshal(body, &request); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	newName := request.Name
	if newName != "" {
		// Update the name
		name = newName
		response := ResponseMessage{Message: "Name updated successfully"}
		jsonResponse(w, response)
	} else {
		http.Error(w, "Invalid request data", http.StatusBadRequest)
	}
}

func jsonResponse(w http.ResponseWriter, data interface{}) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	json.NewEncoder(w).Encode(data)
}
