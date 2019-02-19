# define function for encoding our objects
def myEncode (myStr):
    return myStr.encode('UTF-8', "strict")

# define function for decoding our objects
def myDecode (myStr):
    return myStr.decode('UTF-8', "strict")
