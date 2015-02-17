import sys

#file to be converted
filename = str(sys.argv[1])

#read file into array
with open(filename, "r") as read:
    lines = []
    for line in read:
        lines.append(line)
read.close()

#get size of board
#size = int(lines[0][0])
size = int(lines[0].split(" ",1)[0])

#get old coord bounds
bounds = lines[size+2].split(" ")
print(bounds)
y1 = int(bounds[0])
x1 = int(bounds[1])
y2 = int(bounds[2])
x2 = int(bounds[3])

#update bounds
x = 1
y = 0
while x < size+1:
    newline=""
    y=0
    while y<size:
        # if is in bounds and "."
        if lines[x][y]=="." and x-1>=x1 and x-1<=x2 and y>=y1 and y<=y2:
            #add to - to newline
            newline+="-"
        else:
            #else add it to newline
            newline+=lines[x][y]
        y+=1
    lines[x]=newline+"\n"
    x+=1

#save updated file
newfile = open(filename, "w")
count = 0
for each in lines:
    if count < len(lines)-2:
        newfile.write(each)
    #for last line, remove newline chars
    if count == len(lines)-2:
        newfile.write(each[:len(each)-1])
    count+=1
    
newfile.close()
