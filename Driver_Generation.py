col = int(raw_input("enter No Of Columns:   "))
row = int(raw_input("ENter No of Rows:  "))
# print row, col
Dimention = int(raw_input("Enter Dimention for each block to generate:  "))
pathToFile = "C:/Users/shiri/git/Data_Files/IPFS"
Count = 0

for r in range(0,row):
    for c in range(0,col):
        fileName = pathToFile + "/" + str(Count) + ".txt"
        print fileName
        try:
            fileToWrite = open(fileName,"w")
            for i in range (0,Dimention):
                for j in range(0,Dimention):
                    fileToWrite.write(str(Count))
                fileToWrite.write("\n")
            Count += 1
        except Exception as e:
            print e
