import json
import random
import ipfsapi

def getDataBlockList(dataSectionPath, api):
    dataBlockList = []
    gMin = 1000
    gMax = 0
    for i in range(0,4):
        dataBlockInfo = dict()
        dataBlockPath = dataSectionPath+"/"+"data_"+str(i)
        try:
            metaNumber = random.randint(0,100)
            metaNumber1 = random.randint(0,100)
            if metaNumber < metaNumber1 :
                dataBlockInfo["min"] = str(metaNumber)
                dataBlockInfo["max"] = str(metaNumber1)
            else:
                dataBlockInfo["min"] = str(metaNumber1)
                dataBlockInfo["max"] = str(metaNumber)
            gMin = min(metaNumber, metaNumber1, gMin)
            gMax = max(metaNumber, metaNumber1, gMax)
            res = api.add(dataBlockPath)
            dataBlockInfo['number'] = str(i)
            dataBlockInfo['hash'] = res['Hash']
            dataBlockList.append(dataBlockInfo)
            # fileToRead.close()
        except:
            print "Error while accessing file"
    return dataBlockList, gMin, gMax

def getDataSectionList(slicePath,api):
    dataSectionList = []
    gMin = 1000
    gMax = 0
    for i in range(0,4):
        dataSectionInfo = dict()
        # Will have to change it to i
        dataSectionPath = slicePath+"/"+"BLOCK_"+str(i)
        dataBlockList, min, max = getDataBlockList(dataSectionPath, api)
        dataSectionInfo["number"] = str(i)
        dataSectionInfo["dataBlockList"] = dataBlockList
        dataSectionInfo["min"] = str(min)
        dataSectionInfo['max'] = str(max)
        if min < gMin:
            gMin = min
        if max > gMax:
            gMax = max
        dataSectionList.append(dataSectionInfo)
    return dataSectionList, min, max

def addDataSlice(slicePath, sliceNumber):
    sliceInfo = dict()
    api = ipfsapi.connect('127.0.0.1', 5001)
    dataSectionList, min, max = getDataSectionList(slicePath, api)
    sliceInfo["dataSectionList"] = dataSectionList
    sliceInfo['number'] = str(sliceNumber)
    sliceInfo["min"] = str(min)
    sliceInfo["max"] = str(max)
    return sliceInfo

def main():
    slicePath = "/home/sbot/dataDir/hotStorage/SLICE_0"
    print "Entering Folder : " + slicePath+"/"+"SLICE_0"
    with open("dag1.json","w") as fileToWrite:
        # addDataSlice takes in two parameter path of SLICE dir and sliceNumber
        json.dump(addDataSlice(slicePath, 0), fileToWrite)
if __name__ == "__main__":
    main()
