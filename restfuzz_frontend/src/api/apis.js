import axios from "axios";

const localBaseUrl = 'http://localhost:8080';

const baseUrl = localBaseUrl;

export function generateSendAPI(data) {
  console.log(data);
  return axios.post(`${baseUrl}/restfuzz/receive`, data);
}
