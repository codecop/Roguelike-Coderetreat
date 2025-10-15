import { generateRandomMap } from '../src/generator';
import { validateMap } from '../src/validations';

describe('Generate map', () => {
  it('should generate a random valid map', () => {
    const map = generateRandomMap();
    const mapAsString = map.join('\n');

    const validMap = validateMap(mapAsString);

    expect(validMap).toBe(true);
  });
});
