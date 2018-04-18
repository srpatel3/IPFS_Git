import os
import subprocess
from subprocess import call
from subprocess import check_call
import pprint
def generateData(col,row,Dimention):

	# print row, col
	pathToFile = "/home/shirish/Data_Files/IPFS_Data/DB"
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
				try:
					fileToWrite.close()
				except Exception as e:
					print e
	        	except Exception as e:
	            		print e


def addData(col,row,Dimention):
	try:
		path = "/home/shirish/Data_Files/IPFS_Data/DB/"
		fileList = subprocess.check_output(['ls','/home/shirish/Data_Files/IPFS_Data/DB/'])
		pp = pprint.PrettyPrinter(indent=4)
		print "Files are:	\n",fileList
		fileList = fileList.split("\n")
		fileList = fileList[0:len(fileList)-1]
		jsonFile = open("/home/shirish/Data_Files/IPFS_Data/DB.json","w")
		dimn = "{\n"
		dimn = dimn + '\t"'+"Dimention"+'"'+": {\n"
		dimn = dimn +'\t\t"'+"No_Of_Columns"+'" :'+str(col)+",\n"
		dimn = dimn + '\t\t"'+"No_Of_Rows"+'" :'+str(row)+",\n"
		dimn = dimn + '\t\t"'+"Total_Blocks"+'" :'+str(Dimention)+"\n"
		dimn = dimn + "\t},\n"
		print dimn
		jsonFile.write(dimn)
		#Now goes the Actual list of IPFS
		block_List = '\t"'+"Blocks"+'"'+": {\n"
		jsonFile.write(block_List)
		print block_List 
		for i in range(0, len(fileList)):
			fileName = fileList[i]
			fileToAdd = path + fileName			
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
	Dimention = int(raw_input("Enter Dimention for each block to generate:  "))
	generateData(col,row,Dimention)
	addData(col,row,Dimention)


if __name__ == "__main__":
    main()
