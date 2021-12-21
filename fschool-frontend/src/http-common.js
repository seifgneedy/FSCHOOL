import axios from "axios";
export const AXIOS = axios.create({
    baseURL: "http://localhost:9000",
    headers:{
        "Access-Control-Allow-Origin": "http://localhost:8080",
    },
});