import axios from "axios";

const DATA_SET_API_URL = "http://localhost:8080/api/v2/dataset";

class DataSetService {
    retrieveAllDataSets() {
        return axios.get(`${DATA_SET_API_URL}/all`);
    }
    
    retrieveDataSet(id) {
        return axios.get(`${DATA_SET_API_URL}/id/${id}`);
    }

    retrieveDataSetByUserId(userId) {
        return axios.get(`${DATA_SET_API_URL}/user/${userId}`);
    }
    
    deleteDataSet(id) {
        return axios.delete(`${DATA_SET_API_URL}/${id}`);
    }

    deleteDataSetByUserId(userId) {
        return axios.delete(`${DATA_SET_API_URL}/user/${userId}`);
    }
    
    updateDataSet(id, dataset) {
        return axios.put(`${DATA_SET_API_URL}/id/${id}`, dataset);
    }
    createDataSet(dataset) {
        return axios.post(`${DATA_SET_API_URL}/`, dataset);
    }
}
export default new DataSetService()