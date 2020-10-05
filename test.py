# -*- coding: utf-8 -*-
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import tensorflow as tf
import json
# import requests
from gensim.models.word2vec import Word2Vec
import warnings
warnings.filterwarnings("ignore")
from sklearn.cluster import KMeans
from konlpy.tag import Okt
from gensim.models import FastText
from tqdm import tqdm
from gensim import models

# print('1')
# ko_model = models.fasttext.load_facebook_model('cc.ko.300.bin.gz')
# print('2')
# for w, sim in ko_model.similar_by_word('파이썬', 10):
#     print(f'{w}: {sim}')


# #embedding_model = Word2Vec(kovec, size=100, window = 5, min_count=2, workers=3, iter=1000, sg=1, sample=1e-3)
embedding_model = Word2Vec.load('ko.bin')

# 어휘의 feature vector
word_vectors = embedding_model.wv.syn0

num_clusters = 30
kmeans_clustering = KMeans(n_clusters=num_clusters)

# list of clustered vector
# str(list(word_centroid_map.values())[i]) : 그룹 넘버 , str(list(word_centroid_map.keys())[i]) : 단어
idx = kmeans_clustering.fit_predict(word_vectors)
idx = list(idx)
names = embedding_model.wv.index2word
word_centroid_map = {names[i]: idx[i] for i in range(len(names))}


# 불용어

#stop_word="은,는,이,가,을,를,었,았,에게,한테,고,과,등,께,라고,의"
#stop_list=stop_word.split(',')

for c in range(num_clusters):
    #print("\ncluster {}".format(c))
    words = []
    cluster_values = list(word_centroid_map.values())
    for i in range(len(cluster_values)):
        if (cluster_values[i] == c):
            # if cluster_values[i] not in stop_list:
            words.append(list(word_centroid_map.keys())[i])
    #print(words)

# 단어 위치 확인용
# for i in range(len(names)):
#     if "고장" == names[i]:
#         print("find in : " + str(i) +" " + str(names[i]) + " " + str(list(word_centroid_map.values())[i]) + " " + str(list(word_centroid_map.keys())[i]))

# # input_text = "A동 압축기가 멈췄다고 이광용에게 말해줘"
input_text = "이광용한테 압축기가 고장났다고 알려줘"

tagger = Okt()
msg_nouns = tagger.nouns(input_text)
print(tagger.pos(input_text))
print(tagger.nouns(input_text))
# create json object
result = dict()
result["COMMAND"] = "SEND"
result["RECEIVER"] = ""
result["MSG"] = ""

# for w, sim in ko_model.similar_by_word('파이썬', 10):
#     print(f'{w}: {sim}')

# fill in the json object
for i in range(len(msg_nouns)):
    try:
        temp = embedding_model.most_similar(msg_nouns[i],topn=1)
        #print(embedding_model.similarity(msg_nouns[i],temp))
        #temp = ko_model.similar_by_word(msg_nouns[i],1)
        #print('temp:' + temp)
        print(temp, temp[0][0], temp[0][1])
        if (temp[0][1] > 0.6) and (temp[0][1] < 0.8):
            result["MSG"] += temp[0][0] + " "
        else :
            result["MSG"] += msg_nouns[i] + " "
    except:
        result["RECEIVER"] += msg_nouns[i]

# print result
print(json.dumps(result, ensure_ascii = False, indent="\t"))

# ############save
# # KMeans kmeans = ...
# #
# # using (FileStream fs = new FileStream(path, FileMode.Create))
# # {
# #     new BinaryFormatter().Serialize(fs, kmeans);
# # }
#
# ##########load
# # KMeans kmeans = null;
# #
# # using (FileStream fs = new FileStream(path, FileMode.Open))
# # {
# #     kmeans = new BinaryFormatter().Deserialize(fs) as KMeans;
# # }