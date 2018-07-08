import re
from sys import stdin

d = dict()
linha = stdin.readline()
l = []
num = 0
while(1):
    if len(linha) == 0:
            break

    if linha[0] == "#":
        if len(d) != 0:
            print("%4d %s" % (d[l[0]], l[0]))
        #print(d)
        d = dict()
        num = 0
        linha = stdin.readline()
        if len(linha) == 0:
            break

    for i in (re.split('[^a-zA-Z]', linha.lower())):
        if i == '':
            continue
        if i not in d: 
            d[i] = 1
            if d[i] > num:
                num = d[i]
                if len(l) == 0:
                    l.append(i)
                else:
                    del l[0]
                    l.append(i)
        elif i in d:
            d[i] += 1
            if d[i] > num:
                num = d[i]
                if len(l) == 0:
                    l.append(i)
                else:
                    del l[0]
                    l.append(i)

    linha = stdin.readline()
