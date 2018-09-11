import os
import random
import subprocess
from subprocess import call
from subprocess import check_call
import pprint
def generateData(col,row):

	# print row, col
	pathToFile = "/home/sbot/DataFiles/IPFSDATA/DB"
	fileName = pathToFile + "/"+ "master.txt"
	print fileName
	count = 0
	fileToWrite = open(fileName,"w")
	for r in range(0,row):
		temp = ""
		for c in range(0,col):
			# temp+=str(random.randint(0,1))
			a = count
			temp+=str(a)
		count += 1
		# print tem
		fileToWrite.write(temp+"\n")
	try:
		fileToWrite.close()
	except Exception as e:
		print e
def addDataV1(noOfBlocks):
	fileName ="/home/sbot/DataFiles/IPFSDATA/DB/master.txt"
	count = 0
	fileToRead = open(fileName,"r")
	data = fileToRead.readlines()
	noOfLines = len(data)
	print "Total No Of lines are "+str(noOfLines)

	fileToRead.close()

def addData(col,row,noOfBlocks):
	try:
		path = "/home/sbot/DataFiles/IPFSDATA/DB"
		print "Path to get Files is: " + path
		fileList = subprocess.check_output(['ls',path])

		pp = pprint.PrettyPrinter(indent=4)
		print "Files are:	\n",fileList
		fileList = fileList.split("\n")
		fileList = fileList[0:len(fileList)-1]
		print fileList
		jsonFile = open(path+"/DB.json","w")
		dimn = "{\n"
		dimn = dimn + '\t"'+"Dimention"+'"'+": {\n"
		dimn = dimn + '\t\t"'+"Total_Blocks"+'" :'+str(col*row)+"\n"
		dimn = dimn + "\t},\n"
		print dimn
		jsonFile.write(dimn)
		#Now goes the Actual list of IPFS
		block_List = '\t"'+"Blocks"+'"'+": {\n"
		jsonFile.write(block_List)
		print block_List
		for i in range(0, len(fileList)):
			fileName = fileList[i]
			fileToAdd = path +"/"+ fileName
			IPFS_Hash = subprocess.check_output(['ipfs','add',fileToAdd])
			IPFS_Hash = IPFS_Hash.split()
			#print IPFS_Hash[1]
			if not i == len(fileList) -1 :
				block_List = '\t\t"'+str(i)+'":'+'"'+str(IPFS_Hash[1])+'"'+",\n"
			else:
				block_List = '\t\t"'+str(i)+'":'+'"'+str(IPFS_Hash[1])+'"'+"\n"
			jsonFile.write(block_List)
			print block_List
		block_List = "\t}\n"
		block_List += "}"
		jsonFile.write(block_List)
		print block_List
		try:
			jsonFile.close()
		except Exception as e:
			print e
	except Exception as e:
		print e

def main():
	col = int(raw_input("enter No Of Columns:   "))
	row = int(raw_input("ENter No of Rows:  "))
	# noOfBlocks = int(raw_input("Enter noOfBlocks for each block to generate:  "))
	generateData(col,row)
	# addDataV1(5)


if __name__ == "__main__":
    main()
