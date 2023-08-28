FROM node:alpine
WORKDIR /usr/rogue-stats
COPY package.json .
RUN npm install
COPY . .
RUN npm run build
CMD ["node", "./dist/index.js"]
