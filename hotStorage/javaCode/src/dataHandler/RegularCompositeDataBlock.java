



public class RegularCompositeDataBlock {

	ArrayList<[] float> blocks;
	
	int blockSpaceRows;
	int blockSpaceCols;

	int blockShapeRows;
	int blockShapeCols;
	
	
	
	
	RegularCompositeDataBlock(ISBound blockSpaceShape, ISBound blockShape, ArrayList<float[]> floatBlocks){
	
		blocks = floatBlocks;
		
		//FIXME: confusing to use start/end for specifying 2 dimensions
		this.blockSpaceRows = blockSpaceShape.get_sBound;
		this.blockSpaceCols = blockSpaceShape.get_eBound;
		
		this.blockShapeRows = blockShape.get_sBound;
		this.blockShapeCols = blockShape.get_eBound;
	
		
	}
	 




	private static int index( int row, int column, int rowSize){ // rowSize is numCols
	
		return row*rowSize + column; // row major order
	}
	
	
	float getFloat(int r, int c){
	
		// pick the block
		
		
		int blockIndex = index( r / blockShapeRows, c / blockShapeCols, blockSpaceCols);
		
		int floatIndex = index( r % blockShapeRows, c % blockShapeCols, blockShapeCols);
		
		return blocks.get(blockIndex)[floatIndex];
	}


}
