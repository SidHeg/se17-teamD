import json, requests

url = "https://project1-007.herokuapp.com/attendance/"
urll = "http://127.0.0.1:8000/attendance/"
login = "{ \"status\" : 'login' , 'user_id' : 'timm' , 'course_id' : 'csc510' , 'password' : 'password' }"
nfc={'status':'nfc', 'user_id':'timm', 'course_id':'csc510', 'password':'pasword', 'text':'xyz'}
resp = requests.post(url, data = login)
print(resp._content)
print(resp)