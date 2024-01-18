# base image
FROM node:18.16.1-alpine

# set working directory
WORKDIR /app

# install app dependencies
COPY package*.json ./
RUN npm install 

# add app
COPY . .

# Expose the port
EXPOSE 3000

# start app
CMD ["npm", "start"]
