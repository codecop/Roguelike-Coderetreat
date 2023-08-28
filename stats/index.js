const { createApp } = require('./src/app');
const PORT = 8002;

createApp().
    then(app => app.listen(PORT));

console.log(`Stats started on ${PORT},\nOpen http://localhost:${PORT}/stats`);
