const { createApp } = require('./src/app');
const PORT = 5012;

createApp().
    then(app => app.listen(PORT));

console.log(`Hello started on ${PORT},\nOpen http://localhost:${PORT}/room-ab to see the room layout.`);
