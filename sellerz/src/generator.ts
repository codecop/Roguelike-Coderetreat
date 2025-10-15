// Map generation logic
// 1. There should be walls around the map. Represented by a #
// 2. There should be a maximum of 15x15 cells.
// 3. Could be a square, but doesn't have to be.
// 4. It should have at least one door or stair to leave the room. Represented by a |

export function generateRandomMap(): string[] {
  const minimumWidthAndHeight = 3;
  const width = Math.floor(Math.random() * 12) + minimumWidthAndHeight;
  const height = Math.floor(Math.random() * 12) + minimumWidthAndHeight;

  const map: string[] = [];

  for (let y = 0; y < height; y++) {
    let row = '';
    for (let x = 0; x < width; x++) {
      if (y === 0 || y === height - 1 || x === 0 || x === width - 1) {
        row += '#'; // Wall
      } else if (Math.random() < 0.1) {
        row += '|'; // Door
      } else {
        row += ' '; // Empty space
      }
    }
    map.push(row);
  }

  return map;
}
