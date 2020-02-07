python -m pip install pyjwt
python -m pip install cryptography --upgrade

import jwt
import json
import base64
#from jwt.contrib.algorithms.pycrypto import RSAAlgorithm
#jwt.register_algorithm('RS256', RSAAlgorithm(RSAAlgorithm.SHA256))


private_key   = open('key.prv').read()
public_key    = open('key.pub').read()

java_b64_header      = open('b64_header.txt').read()
java_b64_payload     = open('b64_payload.txt').read()
java_b64_signature   = open('b64_signature.txt').read()

java_b64_header
java_b64_payload
java_b64_signature


payload_json     = {'user_id': 123}
with open('payload.txt') as json_file:
    payload_json = json.load(json_file)
\


header_json     = {'user_id': 123}
with open('header.txt') as json_file:
    header_json = json.load(json_file)
\

header_json
payload_json


token_1 = jwt.encode(payload, private_key, algorithm='RS256', headers={"alg": "RS256", "typ": "JWT"}).decode('utf-8')
token_1

token_2 = jwt.encode(payload, private_key, algorithm='RS256', headers={"typ": "JWT", "alg": "RS256"}).decode('utf-8')
token_2

token = jwt.encode(payload, private_key, algorithm='RS256', headers={"typ": "JWT", "alg": "RS256"}).decode('utf-8')
token

header={'alg':'RS256','typ':'JWT'}
token = jwt.encode(payload, private_key, algorithm='RS256').decode('utf-8')

token = jwt.encode(header, private_key, algorithm='RS256').decode('utf-8')


jwt.decode(token,public_key,algorithm='RS256',options={'verify_exp': False})

tab=token.split('.')
jwt_header  = tab[0]
jwt_payload = tab[1]
jwt_sign    = tab[2]


java_b64_header
jwt_header

jwt_payload
java_b64_payload

jwt_sign
java_b64_signature

s = base64.b64decode(jwt_payload)

base64.b64decode(jwt_header)
base64.b64decode('eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9')
base64.b64decode('eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9')


base64.b64encode(json.dumps({"typ": "JWT", "alg": "RS256"}).encode('utf-8'))
base64.b64encode(json.dumps({"alg": "RS256", "typ": "JWT"}).encode('utf-8'))


paylod_str = json.dumps(payload_json)  # Turns your json dict into a str
print(paylod_str)
base64.b64encode(paylod_str.encode('utf-8'))

encoded_payload = base64.b64encode(payload)
    
    
import base64
encoded = base64.b64encode('data to be encoded')
encoded
'ZGF0YSB0byBiZSBlbmNvZGVk'
data = base64.b64decode(encoded)
data
'data to be encoded'


header_json = {"alg": "RS256", "typ": "JWT"}
header64 = base64.b64encode(json.dumps(header_json).encode('utf-8'))
with open('payload.txt') as json_file:
    payload_json = json.load(json_file)
\

payload64 = base64.b64encode(json.dumps(payload_json).encode('utf-8'))

data = '{"hello": "world"}'
enc = payload.encode()  # utf-8 by default
base64.encodestring(enc)

header_json_str = '{"alg": "RS256", "typ": "JWT"}'
enc = header_json_str.encode()  # utf-8 by default
base64.encodestring(enc)


header_json     = {'user_id': 123}
with open('header.txt') as json_file:
    header_json = json.load(json_file)
\

    
    
print(type(header_json))  #dict
datastr = json.dumps(header_json)
datastr=datastr.replace(" ", "")
print(type(datastr)) #str
print(datastr)
my_encoded_header = base64.b64encode(datastr.encode('utf-8'))  #1 way
print(my_encoded_header)
print(type(my_encoded_header)) #bin
my_encoded_header_str=my_encoded_header.decode("utf-8")
print(type(my_encoded_header_str)) #str
my_encoded_header_str
my_encoded_header_str=my_encoded_header_str + "=" * (-len(my_encoded_header_str)%4)
my_encoded_header_str
java_b64_header
jwt_header


print(type(payload_json))  #dict
datastr = json.dumps(payload_json)
datastr=datastr.replace(" ", "")
print(type(datastr)) #str
print(datastr)
my_encoded_payload = base64.b64encode(datastr.encode('utf-8'))  #1 way
print(my_encoded_payload)
print(type(my_encoded_payload)) #bin
my_encoded_payload_str=my_encoded_payload.decode("utf-8")
print(type(my_encoded_payload_str)) #str
my_encoded_payload_str
my_encoded_payload_str=my_encoded_payload_str + "=" * (-len(my_encoded_payload_str)%4)
my_encoded_payload_str
java_b64_payload
jwt_payload

jwt_header + "." + jwt_payload

print(base64.encodebytes(datastr.encode())) #2 method
