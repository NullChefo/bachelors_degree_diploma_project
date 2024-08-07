FROM node:16-alpine

WORKDIR /usr/src/app

COPY . .

ARG PORT
EXPOSE ${PORT}

ENTRYPOINT [ "node", "index.js" ]
