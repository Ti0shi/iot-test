FROM node:14

ARG NODE_ENV=production

WORKDIR /app

RUN npm install -g expo-cli

COPY package*.json ./
RUN npm install
COPY . .

EXPOSE 19000
EXPOSE 19006

CMD [ "npm", "run", "web" ]
