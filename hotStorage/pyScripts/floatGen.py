from array import array
import random

# Ideally it should take in no of floats to generate and return an array or list of floats
# In future we can also set different parameters
def getFloatList(totalNum):
	# output_file = open('binaryFile', 'wb')
	list_of_floats = []
	# totalNum = 10
	for i in range(0,totalNum):
		list_of_floats.append(random.random())
	return list_of_floats
	# float_array = array('f', list_of_floats)
	# float_array.tofile(output_file)
	# output_file.close()
	
