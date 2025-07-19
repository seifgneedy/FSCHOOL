FROM node:18-alpine AS build
WORKDIR /fschool-frontend

COPY package*.json ./
# Project is old, multiple dependencies deprecated
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build

# PROD
FROM nginx:stable-alpine AS production-stage
COPY --from=build /fschool-frontend/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
