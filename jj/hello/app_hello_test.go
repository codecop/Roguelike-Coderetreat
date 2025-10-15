package hello_test

import (
	"encoding/json"
	"io"
	"net/http"
	"net/http/httptest"
	"roguelike-go/hello"
	"testing"

	"github.com/stretchr/testify/assert"
)

func readResponseName(response *httptest.ResponseRecorder) (string, error) {
	res := response.Result()
	defer res.Body.Close()
	body, err := io.ReadAll(res.Body)
	if err != nil {
		return "", err
	}
	var nameResponse hello.NameCall
	if err := json.Unmarshal(body, &nameResponse); err != nil {
		return "", err
	}
	return nameResponse.Name, nil
}

func TestGetNameResponse(t *testing.T) {
	t.Run("should get name", func(t *testing.T) {
		expected_name := "World!"
		request := httptest.NewRequest(http.MethodGet, "/get_name", nil)
		request.Header.Add("Accept", "application/json")
		response := httptest.NewRecorder()

		hello.GetName(response, request)

		assert.Equal(t, response.Code, http.StatusOK)
		name, _ := readResponseName(response)
		assert.Equal(t, expected_name, name)
	})
}

func TestSetNameResponse(t *testing.T) {
	t.Run("should assign new name", func(t *testing.T) {
		expected_name := "Paul"
		setNameRequest := httptest.NewRequest(http.MethodPost, "/set_name?name=Paul", nil)

		hello.SetName(httptest.NewRecorder(), setNameRequest)

		getNameRequest := httptest.NewRequest(http.MethodGet, "/get_name", nil)
		getNameRequest.Header.Add("Accept", "application/json")
		response := httptest.NewRecorder()
		hello.GetName(response, getNameRequest)
		assert.Equal(t, response.Code, http.StatusOK)
		name, _ := readResponseName(response)
		assert.Equal(t, expected_name, name)
	})
}
