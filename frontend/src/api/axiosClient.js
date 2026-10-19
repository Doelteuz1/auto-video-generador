import axios from 'axios';

export const axiosClient = axios.create({
  baseURL: 'http://localhost:8081/api', // Apunta a tu backend de Spring Boot
  headers: {
    'Content-Type': 'application/json',
  },
});