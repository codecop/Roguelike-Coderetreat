// Map generation logic
// 1. There should be walls around the map. Represented by a #
// 2. There should be a maximum of 15x15 cells.
// 3. Could be a square, but doesn't have to be.
// 4. It should have at least one door or stair to leave the room. Represented by a |

const isCornerOfTheMap = (x: number, y: number, width: number, height: number): boolean => {
  return (
    (x === 0 && y === 0) ||
    (x === width - 1 && y === 0) ||
    (x === 0 && y === height - 1) ||
    (x === width - 1 && y === height - 1)
  );
};

const isBorderOfTheMap = (x: number, y: number, width: number, height: number): boolean => {
  return x === 0 || y === 0 || x === width - 1 || y === height - 1;
};

const isPositionCorrect = (x: number, y: number, width: number, height: number): boolean => {
  return !isCornerOfTheMap(x, y, width, height) && isBorderOfTheMap(x, y, width, height);
};

export function generateRandomMap(amountOfMonsters: number = 0): string[] {
  const minimumWidthAndHeight = 7;
  const width = Math.floor(Math.random() * 8) + minimumWidthAndHeight;
  const height = Math.floor(Math.random() * 8) + minimumWidthAndHeight;

  const map: string[] = [];
  let currentAmountOfDoors = 0;
  let currentAmountOfButtons = 0;
  let currentAmountOfPortals = 0;

  for (let y = 0; y < height; y++) {
    let row = '';
    for (let x = 0; x < width; x++) {
      if (
        Math.random() < 0.05 &&
        !isCornerOfTheMap(x, y, width, height) &&
        x !== 1 &&
        y !== 1 &&
        currentAmountOfDoors++ < 3
      ) {
        row += '|'; // Door
      } else if (
        Math.random() < 0.1 &&
        isPositionCorrect(x, y, width, height) &&
        currentAmountOfButtons++ < 1
      ) {
        row += 'B'; // Button
      } else if (y === 0 || y === height - 1 || x === 0 || x === width - 1) {
        row += '#'; // Wall
      } else {
        if (Math.random() < 0.03 && x !== 1 && y !== 1 && currentAmountOfPortals++ < 3) {
          row += 'P'; // Portal
        } else if (Math.random() < 0.05 && x !== 1 && y !== 1 && amountOfMonsters-- > 0) {
          row += 'M'; // Monster
        } else {
          row += ' '; // Empty space
        }
      }
    }
    map.push(row);
  }
  console.log(map);

  return map;
}
