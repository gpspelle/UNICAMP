t = int(input())
while t:
    t-=1

    a, b = map(int, input().split())
    d = dict()

    for i in range(a):
        d[i] = []

    x = input().split()
    for i in range(b):
        x[i] = int(x[i])
        d[(x[i])%a].append(x[i])

    for i in range(a):
        print("%d" % i, end='')
        x = d[i]
        for a in range(len(x)):
            print(" -> %d" % x[a], end = '')
        print(" -> \\") 

    if t != 0:
        print("")

    
