t = int(input())

a = [0]*46
b = [0]*46

a[0] = 1
a[1] = 0
b[0] = 0
b[1] = 1
for i in range(2, 46):
    a[i] = b[i-1]
    b[i] = a[i-1] + b[i-1]

print("%d %d" % (a[t], b[t]))
