import math

t = int(input())

while t:
        t-=1

        l = input()
        l = [int(d) for d in str(l)]
        state = -1
        cont = 0
        ans = []
        for x in l:
            if state == x:
                cont += 1
            else:
                if state != -1:
                    ans.append(cont)
                    ans.append(state)
                cont = 1
                state = x
            
        ans.append(cont)
        ans.append(state)
        print(''.join(map(str, ans))) 
