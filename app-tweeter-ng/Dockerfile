FROM node:18 as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm install -g @angular/cli
RUN ng build --configuration production

FROM nginx:alpine
COPY --from=build /app/dist/app-tweeter-ng /usr/share/nginx/html
COPY nginx-custom.conf /etc/nginx/conf.d/nginx-custom.conf

RUN rm /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]