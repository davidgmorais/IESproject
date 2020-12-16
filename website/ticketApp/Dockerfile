FROM node:14.15.1-alpine

WORKDIR /app

COPY package.json /app/package.json
RUN npm install -s
RUN npm install -g @angular/cli -s

COPY . /app
EXPOSE 4200

CMD ng serve --host 0.0.0.0