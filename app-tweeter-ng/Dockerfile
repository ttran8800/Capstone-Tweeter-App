FROM node:18 as build

WORKDIR /app

COPY package*.json ./

RUN npm install
COPY . .

RUN npm install -g @angular/cli

RUN ng build --configuration production

FROM caddy:2-alpine

COPY --from=build /app/dist/app-tweeter-ng /usr/share/caddy

COPY Caddyfile /etc/caddy/Caddyfile

EXPOSE 80 443

# start the nginx web server
CMD ["caddy", "run", "--config", "/etc/caddy/Caddyfile", "--adapter", "caddyfile"]
