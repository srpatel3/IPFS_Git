import random
import os
import json

def addBlock(row, col, fileName):
    try:
        total = 0
        if not os.path.isdir(fileName):
            print "Creating new directory : "+fileName
            os.mkdir(fileName)
        print "Adding data for : "+fileName
        for k in range(0,4):
            fileToWrite = open(fileName+"/data_"+str(k), "w")
            for i in range(0, row):
                string = ''
                for j in range(0, col):
                    number = random.randint(0,1)
                    # total += number
                    string += str(number) + " "
                string += "\n"
                fileToWrite.write(string)

            print "Total is : "+ str(total)

            try:
                fileToWrite.close()
            except Exception as e:
                print e
                print "Error while closing file"
    except Exception as e:
        print e
        print "Error while opening a file"

# def blockGen(row, col, numberOfBlocks):
#     print "Now starting to generate blocks"
#     for i in range (0, numberOfBlocks):
#         print "Currently working on block: "+str(i)
#         getSlice(row, col, i)

# this one is to create new directory for each block item
def blockGenInNewDir(row,col, numberOfSlices):
    print "Starting...."
    dirPath = "/home/sbot/dataDir/hotStorage/"
    for i in range(0, numberOfSlices):
        slicePath = dirPath+"SLICE_"+str(i);
        try:
            if not os.path.isdir(slicePath):
                print "Creating new directory : " + slicePath
                os.mkdir(slicePath)
            print "Adding data for : " + slicePath
            for j in range(0,4):
                addBlock(row,col, slicePath+"/BLOCK_"+str(j))
        except Exception as e:
            print e
            print "Error while creating directory"
def main():
    # print "In main method"
    print "Enter Dimention of a Block"
    # depth = 3
    col = int(raw_input('columns:'))
    row = int(raw_input('rows:'))
    numberOfSlices = int(raw_input('Total no of blocks:'))
    blockGenInNewDir(row, col, numberOfSlices)
# Check if there is main method or not
# If there is no main method it will release an error
if __name__ == "__main__":
    main()
