import random

def getSlice(row, col, currentSlice):
    # string = ''
    pathToFile = "/home/sbot/dataDir/hotStorage/"
    fileName = "SLICE"+str(currentSlice)
    fileName = pathToFile + fileName
    try:
        fileToWrite = open(fileName, "w")
        for i in range(0, row):
            string = ''
            for j in range(0, col):
                if i == 0 and j == 0:
                    string += str(currentSlice)+str(i)+str(j) + " "
                elif i == 0 and j == col - 1:
                    string += str(currentSlice)+str(i)+str(1) + " "
                elif i == row - 1 and j == 0:
                    string += str(currentSlice)+str(1)+str(j) + " "
                elif i == row - 1 and j == col -1:
                    string += str(currentSlice)+str(1)+str(1) + " "
                else:
                    string += "001 "
            string += "\n"
            fileToWrite.write(string)

        print string
    except Exception as e:
        print e
        print "Error while opening a file"

    try:
        fileToWrite.close()
    except Exeption as e:
        print e
        print "Error while closing file"

def blockGen(row, col, numberOfBlocks):
    print "Now starting to generate blocks"
    for i in range (0, numberOfBlocks):
        print "Currently working on block: "+str(i)
        getSlice(row, col, i)

def main():
    # print "In main method"
    print "Enter Dimention of a Block"
    # depth = 3
    col = int(raw_input('columns:'))
    row = int(raw_input('rows:'))
    numberOfBlocks = int(raw_input('Total no of blocks:'))
    blockGen(row, col, numberOfBlocks)
# Check if there is main method or not
# If there is no main method it will release an error
if __name__ == "__main__":
    main()
