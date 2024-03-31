import psycopg2
from configparser import ConfigParser
import numpy as np
import torch
from torch import nn
import pickle


# rf
def run_rf(rows):
    db_config = read_db_config()
    conn = psycopg2.connect(**db_config)
    for row in rows:
        params = extract_data(row)
        prediction = get_prediction_rf(params)
        update_db(prediction, conn)
    print('random forest done')


def get_model_rf():
    filename = 'F:\\Study\\NKD\\2023FALL\\NP\\Valid_Data\\model_rf.sav'
    with open(filename, 'rb') as f:
        model = pickle.load(f)
    return model


def get_prediction_rf(params):
    model = get_model_rf()
    X = np.array(params[1:]).reshape(1, -1)
    prediction = np.ravel(model.predict(X))
    print(prediction)
    prediction = np.concatenate((prediction, [params[0]]))
    return prediction


# knn
def run_knn(rows):
    db_config = read_db_config()
    conn = psycopg2.connect(**db_config)
    for row in rows:
        params = extract_data(row)
        prediction = get_prediction_knn(params)
        update_db(prediction, conn)
    print('knn done')


def get_model_knn():
    filename = 'F:\\Study\\NKD\\2023FALL\\NP\\Valid_Data\\model_knn.sav'
    loaded_model = pickle.load(open(filename, 'rb'))
    return loaded_model


def get_prediction_knn(params):
    model = get_model_knn()
    X = np.array(params[1:]).reshape(1, -1)
    prediction = np.ravel(model.predict(X))
    print(prediction)
    prediction = np.concatenate((prediction, [params[0]]))
    return prediction


# dnn
def run_dnn(rows):
    db_config = read_db_config()
    conn = psycopg2.connect(**db_config)
    for row in rows:
        params = extract_data(row)
        model = get_model_dnn()
        prediction = get_prediction_dnn(model, params)
        update_db(prediction, conn)
    print('dnn done')


class NN_MLP(nn.Module):
    def __init__(self, input_size, layers, output_size):
        super(NN_MLP, self).__init__()
        self.input_size = input_size
        self.layers = layers
        self.output_size = output_size

        for layer in range(len(layers)):
            if layer == 0:
                setattr(self, f'fc{layer}', nn.Linear(input_size, layers[layer]))
            else:
                setattr(self, f'fc{layer}', nn.Linear(layers[layer - 1], layers[layer]))
            setattr(self, f'act{layer}', nn.ReLU())
        self.out = nn.Linear(layers[-1], output_size)

    def forward(self, x):
        for layer in range(len(self.layers)):
            x = getattr(self, f'fc{layer}')(x)
            x = getattr(self, f'act{layer}')(x)
        x = self.out(x)
        return x


def get_model_dnn():
    model = torch.load('F:\\Study\\NKD\\2023FALL\\NP\\Valid_Data\\model.ckpt')
    return model


def get_prediction_dnn(model, params):
    model.eval()
    x = torch.tensor(params[1:], dtype=torch.float32)
    with torch.no_grad():
        prediction = model(x)
    mm_value = [[1716.0, 1553.0], [0.128, 0.02], [0.0185, 0.0001]]
    prediction[0] = prediction[0] * (mm_value[0][0] - mm_value[0][1]) + mm_value[0][1]
    prediction[1] = prediction[1] * (mm_value[1][0] - mm_value[1][1]) + mm_value[1][1]
    prediction[2] = prediction[2] * (mm_value[2][0] - mm_value[2][1]) + mm_value[2][1]
    prediction = np.concatenate((prediction, [params[0]]))
    return prediction


def read_db_config(filename='dbconfig.ini', section='postgresql'):
    """ Read database configuration file and return a dictionary object
    :param filename: name of the configuration file
    :param section: section of database configuration
    :return: a dictionary of database parameters
    """
    # create parser and read ini configuration file
    parser = ConfigParser()
    parser.read(filename)

    # get section, default to postgresql
    db = {}
    if parser.has_section(section):
        parameters = parser.items(section)
        for parameter in parameters:
            db[parameter[0]] = parameter[1]
    else:
        raise Exception('Section {0} not found in the {1} file'.format(section, filename))

    return db


def extract_data(row):
    params = [row[0], row[5], row[11], row[12], row[13], row[14], row[15], row[16], row[17], row[18],
              row[6], row[7], row[8], row[9], row[10]]
    print(params)
    return params


def update_db(prediction, conn):
    cur = conn.cursor()
    for _ in prediction:
        sql = "UPDATE data_table SET output1 = %s, output2 = %s, output3 = %s, is_processed = true WHERE id = %s"
        cur.execute(sql, (float(prediction[0]), float(prediction[1]), float(prediction[2]), prediction[3]))
    conn.commit()
    cur.close()


def main():
    db_config = read_db_config()
    conn = psycopg2.connect(**db_config)
    cur = conn.cursor()
    while True:
        sql_dnn = "SELECT * FROM data_table where is_processed = false and selected_model = 'DNN'"
        sql_knn = "SELECT * FROM data_table where is_processed = false and selected_model = 'KNN'"
        sql_rf = "SELECT * FROM data_table where is_processed = false and selected_model = 'Random Forest'"
        cur.execute(sql_dnn)
        rows_dnn = cur.fetchall()
        cur.execute(sql_knn)
        rows_knn = cur.fetchall()
        cur.execute(sql_rf)
        rows_rf = cur.fetchall()
        if rows_dnn:
            run_dnn(rows_dnn)
        if rows_knn:
            run_knn(rows_knn)
        if rows_rf:
            run_rf(rows_rf)


if __name__ == '__main__':
    main()
