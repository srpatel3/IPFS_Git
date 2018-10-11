import os
import subprocess
from subprocess import check_call
from subprocess import call

def getBlocks(ipfsHashList):
    for ipfsHash in ipfsHashList:
        print ipfsHash

def gethashList():
    hashList = ["QmbwnQ8PMPy53M6F6eDEg8qVAcEbbHEZVk19Ppn4kiQ1B5"]
    return hashList

def main():
    ipfsHashList = gethashList()
    getBlocks(ipfsHashList)

if __name__ == "__main__":
    main()
