const { createApp } = require('./src/app');
const PORT = 5010;

createApp().
    then(app => app.listen(PORT));

console.log(`Hello started on ${PORT},\nOpen http://localhost:${PORT}/hello`);
