import hashlib
#import jwt
#import json
import base64
from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP
from Crypto.Signature import PKCS1_v1_5
from Crypto.Signature.pkcs1_15 import PKCS115_SigScheme
from Crypto.Hash import SHA512, SHA384, SHA256, SHA, MD5
from Crypto import Random
from base64 import b64encode, b64decode
#import rsa
import binascii

#
#
#TEST EXCERCISE:
#	 input: Sample output (message + hex-encoded signature + PEM-encoded RSA public key):
#  SHA3_512
#  https://cryptobook.nakov.com/digital-signatures/exercises-rsa-sign-and-verify
#  RSA: Sign / Verify - Examples
#  
#
data           = open("test_p.txt").read()    #payload
public_key_str = open("test_k.txt").read() #pub_key
public_key     = RSA.import_key(open("inp/test_k.txt").read()) #pub_key
signature      = open("test_s.txt").read()    #signature

data
public_key_str
public_key
signature


#convert data to binary 
data = data.encode('utf-8')
data 

#add padding and decode signature
missing_padding = len(signature) % 4
if missing_padding:
	signature += '='* (4 - missing_padding)


signature = base64.urlsafe_b64decode(signature)
print("Signature:", binascii.hexlify(signature))	

#calcualte hash from payload
from Crypto.Hash import SHA3_512
hasher = SHA3_512.new(data)
hasher.hexdigest()

# why this method gives me  an error ? 
signer1 = PKCS115_SigScheme(public_key)
try:
    signer1.verify(hasher, signature)
    print("Signature is valid. OK")
except:
    print("Signature is invalid. ERROR")


# this method works 
signer2 = PKCS1_v1_5.new(public_key)
try:
    signer2.verify(hasher, signature)
    print("Signature is valid. OK")
except:
    print("Signature is invalid. ERROR")


