const { createApp } = require('./src/DungeonApp');
const PORT = 9007;

createApp().
    then(app => app.listen(PORT));

console.log(`Hello started on ${PORT},\nOpen http://localhost:${PORT}/exception`);
