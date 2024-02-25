package hello_test

import (
	"roguelike-go/hello"
	"testing"

	"github.com/stretchr/testify/assert"
)

func TestGetName(t *testing.T) {
	name := "World!"
	hello := hello.NewHello(name)

	assert.Equal(t, hello.GetName(), name)

}

func TestSetName(t *testing.T) {
	name := "World!"
	newName := "Paul"
	hello := hello.NewHello(name)

	hello.SetName(newName)

	assert.Equal(t, hello.GetName(), newName)

}
