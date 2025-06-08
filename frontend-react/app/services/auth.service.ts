import axiosInstance from './axios.config';

const API_URL = 'http://localhost:8080/users';

export interface RegisterData {
  username: string;
  email: string;
  password: string;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  userRole?: string;
}

export interface LoginData {
  username: string;
  password: string;
}

class AuthService {
  async register(data: RegisterData) {
    try {
      const response = await axiosInstance.post(`${API_URL}/signup`, data);
      if (response.data) {
        localStorage.setItem('token', response.data);
      }
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Registration failed');
    }
  }

  async login(data: LoginData) {
    try {
      const response = await axiosInstance.post(`${API_URL}/signin`, null, {
        params: {
          username: data.username,
          password: data.password
        }
      });
      if (response.data) {
        localStorage.setItem('token', response.data);
      }
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Login failed');
    }
  }

  logout() {
    localStorage.removeItem('token');
  }

  getCurrentToken() {
    return localStorage.getItem('token');
  }

  isAuthenticated() {
    return !!this.getCurrentToken();
  }
}

export default new AuthService(); 