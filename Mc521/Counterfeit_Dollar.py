n = int(input())

# coins from A to L
# up: right side goes up (direito leve)
# down: right side goes down (direito pesado)
# even: right side doesn't move
for i in range(0, n):
    result = [None] *3 
    left = [None] *3
    right = [None] *3 
    for j in range(0, 3):
        left[j], right[j], result[j] = map(str, input().split())

    la = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L']
    diff = []
    balance = []


    for j in range(0, 3):
        if result[j] == 'even':
            for k in range(0, len(left[j])):
                if left[j][k] in la:
                    la.remove(left[j][k])  
            for k in range(0, len(right[j])):
                if right[j][k] in la:
                    la.remove(right[j][k]) 
        else:
            diff.append(j)
            balance.append(result[j])

    for j in range(0, len(diff)):
        for k in left[diff[j]]:
            for l in range(0, len(diff)):
                if l == j:
                    continue
                if k not in left[diff[l]] and k not in right[diff[l]]:
                    if k in la:
                        la.remove(k)
        for k in right[diff[j]]:
            for l in range(0, len(diff)):
                if l == j:
                    continue
                if k not in left[diff[l]] and k not in right[diff[l]]:
                    if k in la:
                        la.remove(k)
    
    ans = -1     
    resp = 0 
    # para a quantidade de desbalancos
    #print(diff)
    #print(balance)
    for j in range(0, len(diff)):    
        if balance[j] == 'up':
            for k in left[diff[j]]:
                for l in range(0, len(diff)):   
                    if j == l:
                        continue
                    if balance[l] == 'down':
                        if k in left[diff[l]]:
                            if k in la:
                                la.remove(k)
                    elif balance[l] == 'up':
                        if k in right[diff[l]]:
                            if k in la:
                                la.remove(k)
            for k in right[diff[j]]:
                for l in range(0, len(diff)):
                    if j == l:
                        continue
                    if balance[l] == 'down':
                        if k in right[diff[l]]:
                            if k in la:
                                la.remove(k)  
                    elif balance[l] == 'up':
                        if k in left[diff[l]]:
                            if k in la:
                                la.remove(k)
        elif balance[j] == 'down':
            for k in left[diff[j]]:
                for l in range(0, len(diff)):   
                    if j == l:
                        continue
                    if balance[l] == 'up':
                        if k in left[diff[l]]:
                            if k in la:
                                la.remove(k)
                    if balance[l] == 'down':
                        if k in right[diff[l]]:
                            if k in la:
                                la.remove(k)
            for k in right[diff[j]]:
                for l in range(0, len(diff)):
                    if j == l:
                        continue
                    if balance[l] == 'up':
                        if k in right[diff[l]]:
                            if k in la:
                                la.remove(k) 
                    if balance[l] == 'down':
                        if k in left[diff[l]]:
                            if k in la:
                                la.remove(k)
    for j in list(la):
        if j not in left[0] and j not in right[0] and j not in left[1] and j not in left[2] and j not in right[1] and j not in right[2]:
            la.remove(j)

    resp = ' ' 
    for j in range(0, len(diff)):
        if balance[j] == 'up':
            if la[0] in right[diff[j]]:
                resp = 'light'
            else:
                resp = 'heavy'
        else:
            if la[0] in right[diff[j]]:
                resp = 'heavy'
            else:
                resp = 'light'
    
    print(str(la[0]) + " is the counterfeit coin and it is " + resp + ".")
