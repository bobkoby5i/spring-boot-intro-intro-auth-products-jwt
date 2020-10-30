import bcrypt
salt = bcrypt.gensalt()
#salt = b'$2b$12$BXtgVZjNVyut8haD2/ID8O'
mypwd = "password_123"

hashed = bcrypt.hashpw(mypwd.encode(), salt)
print("    salt  : ", salt)
print("find salt : ", hashed.find(salt))
print("password  : ", mypwd)
print("    hash  : ", hashed)
print(hashed == bcrypt.hashpw(mypwd.encode(), hashed))

import random

s = "abcdefghijklmnopqrstuvwxyz01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()?"
s = "abcdefghijklmnopqrstuvwxyz01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$^&"
passlen = 10
print("passlen  : ", passlen)
p =  "".join(random.sample(s,passlen ))
print(p)

for i in range(5):
    pwd  =  "".join(random.sample(s,passlen ))
    salt = bcrypt.gensalt()
    hash = bcrypt.hashpw(pwd.encode(), salt)
    print(i,pwd,hash)


# TEST2
#hash2 = "$2a$10$3TNNidZb.g8J0.4uIssUhuhSjZo1vHcvQlU/Waw9Y5Yo6mg8BDPwy".encode()
#print( hash2 == bcrypt.hashpw("AAA".encode(), hash2))

