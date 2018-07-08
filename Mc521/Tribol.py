t = int(input())

while t:
    t-=1
    P = int(input())

    pR = pB = pG = 0
    for i in range(P):
        F, T = input().split()

        if F == 'R':
            if T == 'G':
                pR += 2
            else:
                pR += 1

        elif F == 'G':
            if T == 'R':
                pG += 1
            else:
                pG += 2
        else:
            if T == 'R':
                pB += 2
            else:
                pB += 1

    if pR == pG and pR == pB:
        print("trempate")

    elif ((pR == pG) and (pR > pB)) or ((pR == pB) and (pR > pG)) or ((pG == pB) and (pG > pR)):
        print("empate")

    else:
        if pR > pG and pR > pB:
            print("red")
        elif pG > pR and pG > pB:
            print("green")
        elif pB > pR and pB > pG:
            print("blue")
