import random
import ipfsapi
import json
import pprint

def generateData(fileToWrite):
	fileName = 1
	noOfRows = 2
	noOfCols = 2
	count = 0
	for i in range(0,noOfRows):
		for j in range(0,noOfCols):
			temp = random.randint(1,10)
			fileToWrite.write(str(temp)+ " ")
			count+= temp
		fileToWrite.write("\n")
	print count
	return count

def getJSON(dataDir, totalFiles):
	print "Here"


def generateFiles(totalFiles):
	finalDict = {}
	blockList = []
	finalDict['dbName'] = "TestDatabase"
	api = ipfsapi.connect("127.0.0.1", "5001")
	for i in range(0, totalFiles):
		fileName = i
		dataDir = "/home/sbot/DataFiles/IPFSDATA/DB/Test1/"
		fileToWrite = open(dataDir+str(fileName), "w")
		count = generateData(fileToWrite)
		fileToWrite.close()
		blockInfo = {}
		block = {}
		res = api.block_put(dataDir+str(fileName))
		# print res
		blockInfo['hash'] = res['Key']
		blockInfo['count'] = count
		block[i] = blockInfo
		blockList.append(block)
	finalDict["blockList"] = blockList
	
	json_data = json.dumps(finalDict)
	pp = pprint.PrettyPrinter(indent = 4)
	pp.pprint(json_data)
	res = api.add_json(json_data)	
	print res


def main():
# display some lines
	print "Hello World"
	totalFiles = 2
	generateFiles(totalFiles)


if __name__ == "__main__":
	main()
