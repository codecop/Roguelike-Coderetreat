export function validateMap(map: string): boolean {
  // Check if the map has a maximum size of 15x15
  const rows = map.split('\n');
  if (rows.length > 15) return false;
  for (const row of rows) {
    if (row.length > 15) return false;
  }

  return true;
}
