const statsClient = require('./src/statsClient');
const { createApp } = require('./src/app');
const PORT = 5011;

createApp(statsClient).
    then(app => app.listen(PORT));

console.log(`Hello started on ${PORT},\nOpen http://localhost:${PORT}/hello`);
console.log(`Room started on ${PORT},\nOpen http://localhost:${PORT}/room`);
