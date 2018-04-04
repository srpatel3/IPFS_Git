import Image
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mpimg


IMG = Image.open("Test.jpg")
row,col = IMG.size
print row, "And ",col
# pixels = IMG.load()
# print "First Pixel"
# print pixels[1,0]
# print "How many blocks do you want??"
# No_of_Blocks =
total_No_of_Blocks = 40
RowDisc = row/total_No_of_Blocks
ColDisc = col/total_No_of_Blocks

print RowDisc, ColDisc

# flag = "true"
# while flag == "true":
#     croppedIM = IMG.crop((0,0,960,540))
#     croppedIM.size
#     croppedIM.save("Cropped.jpg")
#     IMG = Image.open("Cropped.jpg")
#     IMG.show()
#     flag = raw_input("Enter value for flag :")
# img=mpimg.imread("Cropped.jpg")
# imgplot = plt.imshow(img)
# plt.show()
