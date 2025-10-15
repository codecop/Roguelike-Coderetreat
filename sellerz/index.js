const { createApp } = require('./src/app');
const PORT = 5013;

createApp().then((app) => app.listen(PORT));

console.log(`Sellerz started on ${PORT},\nOpen http://localhost:${PORT}/sellerz`);
