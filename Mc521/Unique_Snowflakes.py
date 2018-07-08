import sys
t = int(input())
d = dict()
while t:

    n = int(sys.stdin.readline())

    max_len = 0
    momento = 0
    start = 0
    while n: 
        n-=1
        l = int(sys.stdin.readline())
        if l in d and d[l] >= start:
            start = d[l] + 1
        
        d[l] = momento

        max_len = max(momento-start+1, max_len)
        momento+=1
                
    d.clear()
    print(max_len)
    t-=1
