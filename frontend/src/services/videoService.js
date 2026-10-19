import { axiosClient } from '../api/axiosClient';

export const videoService = {
  generateVideo: async (videoData) => {
    // videoData debe coincidir con los campos que espera tu VideoRequest.java
    const response = await axiosClient.post('/videos/generate', videoData);
    return response.data;
  },
};