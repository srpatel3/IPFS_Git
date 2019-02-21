import json
import random
import ipfsapi

def getDataHashes(blockPath, api):
    listOfHashes = []
    # total = 0
    min = 1000
    max = 0
    for i in range(0,4):
        dataInfo = dict()
        filePath = blockPath+"/"+"data_"+str(i)
        try:
            # print "Accessing file : "+filePath
            metaNumber = random.randint(0,100)
            if metaNumber < min :
                min = metaNumber
            if metaNumber > max :
                max = metaNumber
            # total += metaNumber
            # fileToRead = open(filePath, "r")
            res = api.add(filePath)
            # print "Hash : " + res['Hash']
            dataInfo['Hash'] = res['Hash']
            dataInfo['Count'] = str(metaNumber)
            listOfHashes.append(dataInfo)
            # fileToRead.close()
        except:
            print "Error while accessing file"
    return listOfHashes, min, max

def getBlockList(slicePath,api):
    blockList = []
    totalBlockCount = 0
    min = 1000
    max = 0
    for i in range(0,4):
        blockInfo = dict()
        blockPath = slicePath+"/"+"BLOCK_"+str(0)
        # print "Entering Folder : "+blockPath
        blockHashes, minTemp, maxTemp = getDataHashes(blockPath, api)
        # totalBlockCount += totalNumber

        blockInfo["blockNumber"] = str(i)
        blockInfo["dataBlocks"] = blockHashes
        blockInfo["min"] = str(minTemp)
        blockInfo['max'] = str(maxTemp)
        if minTemp < min:
            min = minTemp
        if maxTemp > max:
            max = maxTemp
        blockList.append(blockInfo)
    return blockList, min, max
    # print json.dumps(blockList, indent = 4)
def main():
    # print "Hello Now I am adding data to IPFS"
    # For keeping track of all Slices
    sliceList = []
    finalJson = dict();
    finalJson["nodeInfo"] = "SBOT"
    finalJson["ipAddress"] = "130.74.96.25"
    tmin = 1000
    tmax = 0
    api = ipfsapi.connect('127.0.0.1', 5001)
    # print json.dumps(finalJson, indent=4)
    slicePath = "/home/sbot/dataDir/hotStorage"
    noOfSlices = int(raw_input("Enter no of slices you want to add to IPFS : "))
    for i in range(0, noOfSlices):
        sliceInfo = dict()
        print "Entering Folder : " + slicePath+"/"+"SLICE_"+str(i)
        blockList, min, max = getBlockList(slicePath+"/"+"SLICE_"+str(i), api)
        sliceInfo["blockList"] = blockList
        sliceInfo['min'] = str(min)
        sliceInfo['max'] = str(max)
        sliceInfo['sliceNumber'] = str(i)
        sliceList.append(sliceInfo)
	if min < tmin:
		tmin = min
	if max > tmax:
		tmax = max


    # print json.dumps(sliceList, indent = 2)
    finalJson["sliceList"] = sliceList
    finalJson["min"] = tmin
    finalJson["max"] = tmax
    # print json.dumps(finalJson, indent = 2)
    with open("dag.json","w") as fileToWrite:
        json.dump(finalJson, fileToWrite)
if __name__ == "__main__":
    main()
