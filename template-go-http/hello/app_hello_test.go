package hello_test

import (
	"bytes"
	"encoding/json"
	"hello"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/stretchr/testify/assert"
)

func readResponseName(response *httptest.ResponseRecorder) (string, error) {
	res := response.Result()
	defer res.Body.Close()
	body, err := ioutil.ReadAll(res.Body)
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
		expected_name := "John Doe"
		request := httptest.NewRequest(http.MethodGet, "/get_name", nil)
		response := httptest.NewRecorder()

		hello.GetName(response, request)

		assert.Equal(t, response.Code, http.StatusOK)
		name, _ := readResponseName(response)
		assert.Equal(t, expected_name, name)
	})
}

func TestSetNameResponse(t *testing.T) {
	t.Run("should assign new name", func(t *testing.T) {
		var (
			expected_name = "Paul"
			b             bytes.Buffer
			nameCall      = hello.NameCall{Name: expected_name}
		)
		json.NewEncoder(&b).Encode(nameCall)
		setNameRequest := httptest.NewRequest(http.MethodPost, "/set_name", &b)
		getNameRequest := httptest.NewRequest(http.MethodGet, "/get_name", nil)
		response := httptest.NewRecorder()

		hello.SetName(httptest.NewRecorder(), setNameRequest)

		hello.GetName(response, getNameRequest)
		assert.Equal(t, response.Code, http.StatusOK)
		name, _ := readResponseName(response)
		assert.Equal(t, expected_name, name)
	})
}
