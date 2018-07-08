import sys

def lis(arr, tam):
    lis = [1]*tam
    p = [-1]*tam

    for i in range(1, tam):
        for j in range(0, i):
            if arr[i] > arr[j] and lis[i] < lis[j] + 1:
                lis[i] = lis[j] + 1
                p[i] = j
    
    maxx = 0
    ind = -1
    for i in range(tam):
        if lis[i] > maxx:
            maxx = lis[i]
            ind = i

    return maxx, lis, p, ind

data = sys.stdin.readlines()

t = int(data[0].strip())

pos = 1
while t:
    
    if t != 0:
        pos += 1

    t -= 1
    hei_s = []
    hei = []
 
    while len(data) > pos and data[pos].strip() !=  '':
        l = data[pos].strip() 
        hei_s.append(int(l)) 
        hei.append(int(l))
        pos += 1

    size = len(hei)
    tam, lis1, p, ind = lis(hei, size) 

    print("Max hits: %d" % tam)
    ll = []
    while True:
        ll.append(hei[ind])
        ind = p[ind]

        if ind == -1:
            break

    for i in reversed(ll):
        print(i)
    if t != 0:
        print("")
