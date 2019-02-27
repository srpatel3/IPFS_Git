
#include <stdio.h>
#include <stdlib.h>


#define ROWS 1024
#define COLS 1024

int main( int argc, char * argv[]){

	int i;
	int numElements = ROWS * COLS;
	
	float *f = malloc(numElements * sizeof(float));
	
	for (i = 0; i<numElements; i++){
	
		f[i] = (float) rand()/RAND_MAX;
	
	}
	
	
	FILE * file = fopen("binaryData", "wb");
	
	if(file == NULL)
	
		printf("Couldn't open file.\n");
	
	
	int numWritten = fwrite(f, sizeof(float), numElements,  file);
	
	printf("Tried to write %d elements. Wrote %d elements\n", numElements, numWritten);
	
	
	fclose(file);
	


}