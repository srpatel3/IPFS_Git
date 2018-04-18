import Image
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mpimg


IMG = Image.open("Test.jpg")
width,height = IMG.size
print width, "And ",height
total_No_of_Blocks = 2
No_Of_Vertical_Strips = width/total_No_of_Blocks
No_of_Horizontal_Strips = height/total_No_of_Blocks
print No_Of_Vertical_Strips, "And", No_of_Horizontal_Strips
