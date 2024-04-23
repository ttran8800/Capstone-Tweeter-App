# use the offcial nodejs image as base image
FROM node:18 as build

# set the working directory in the container
WORKDIR /app

# copy package.json and package-lock.json to the container
COPY package*.json ./

# install project dependencies
COPY . .

RUN npm install -g @angular/cli

# build the angular app for production
RUN ng build --configuration production

# use a smaller, production ready image as the final image
FROM nginx:alpine

# copy the production ready angular app to the nginx web server root directory
COPY --from=build /app/dist/app-tweeter-ng /usr/share/nginx/html

# EXPOSE port 80
EXPOSE 80

# start the nginx web server
CMD ["nginx", "-g", "daemon off;"]