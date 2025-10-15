package room

import (
	"strings"
	"testing"
)

// helper to convert [][]rune to []string for easy comparison
func roomMapToStrings(r [][]rune) []string {
	lines := make([]string, len(r))
	for i, row := range r {
		lines[i] = string(row)
	}
	return lines
}

func TestNewRoom(t *testing.T) {
	tests := []struct {
		name         string
		sizeX, sizeY int64
		doors        []Coordinate
		want         []string
	}{
		{
			name:  "3x3 room no doors",
			sizeX: 3, sizeY: 3,
			want: []string{
				"###",
				"# #",
				"###",
			},
		},
		{
			name:  "5x4 room with one door on right wall",
			sizeX: 5, sizeY: 4,
			doors: []Coordinate{{X: 4, Y: 2}},
			want: []string{
				"#####",
				"#   #",
				"#   |",
				"#####",
			},
		},
		{
			name:  "6x6 room with multiple doors",
			sizeX: 6, sizeY: 6,
			doors: []Coordinate{
				{X: 0, Y: 2}, // left wall
				{X: 5, Y: 3}, // right wall
				{X: 3, Y: 0}, // top wall
				{X: 2, Y: 5}, // bottom wall
			},
			want: []string{
				"###|##",
				"#    #",
				"|    #",
				"#    |",
				"#    #",
				"##|###",
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			got := NewRoom(tt.sizeX, tt.sizeY, tt.doors)
			gotStrings := roomMapToStrings(got.roomMap)

			if len(gotStrings) != len(tt.want) {
				t.Fatalf("wrong number of rows: got %d, want %d", len(gotStrings), len(tt.want))
			}

			for i := range gotStrings {
				if gotStrings[i] != tt.want[i] {
					t.Fatalf(
						"row %d mismatch\nGot:  %q\nWant: %q\nFull got:\n%s\nFull want:\n%s",
						i, gotStrings[i], tt.want[i],
						strings.Join(gotStrings, "\n"),
						strings.Join(tt.want, "\n"),
					)
				}
			}
		})
	}
}
