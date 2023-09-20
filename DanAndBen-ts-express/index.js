const { createApp } = require('./src/app');
const PORT = 9002;

createApp().
    then(app => app.listen(PORT));

