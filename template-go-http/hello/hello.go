package hello

type Hello struct {
	name string
}

func NewHello(name string) Hello {
	return Hello{name: name}
}

func (h *Hello) GetName() string {
	return h.name
}

func (h *Hello) SetName(name string) {
	h.name = name
}
