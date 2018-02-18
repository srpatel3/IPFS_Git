#!/usr/bin/perl
use strict;
#my $i;

    
	#print "$i\n";
	my $fileToread = "11.adj";
	my $bytesPerBlock = 41;
	my $blockNumber = 0;
	#my $status = system("cat 1024.adj | more");
  	my $fileSize = -s $fileToread;
	print "Size of a file is ${fileSize} \n";
	#my $bytesWritten = 0;
    my $totalBlocks = $fileSize / $bytesPerBlock;
    
    print "totalblocks = ${totalBlocks}.\n";
    
	while ($blockNumber < $totalBlocks){
		
		#print "dd if=${fileToread} skip=${blockNumber} count=1 ibs=${bytesPerBlock} obs=${bytesPerBlock} of=./output/${blockNumber}.blk\n";
	
        #my $status = system("dd if=${fileToread} skip=${blockNumber} count=1 ibs=${bytesPerBlock} obs=${bytesPerBlock} of=./output/${blockNumber}.blk\n");
        #print "dd returned status=${status}\n";
    
        my $hash = `dd if=${fileToread} skip=${blockNumber} count=1 ibs=${bytesPerBlock} | ipfs add`;
        print "${hash}";
        
        $blockNumber = $blockNumber +1;
        #$bytesWritten = $bytesWritten + $bytesPerBlock;
        #print "${status}";
}
