t = int(input())

fam = dict()
for i in range(t):
    
    l, a = map(str, input().split())
   
    # -2: nao sei nada sobre esse cara ainda
    # -1: nao possui
    #  0: recessivo
    #  1: dominante

    # a eh uma pessoa
    if a == 'dominant' or a == 'recessive' or a == 'non-existent':
        if a == 'dominant':
            if l in fam:
                change = fam[l]
                change[2] = 1 
                fam[l] = change
            else:
                info = [l, [None, None ], 1]
                fam[l] = info
                
        elif a == 'recessive':
            if l in fam:
                change = fam[l]
                change[2] = 0 
                fam[l] = change
            else:
                info = [l, [None, None ], 0]
                fam[l] = info

        else:
            if l in fam:
                change = fam[l]
                change[2] = -1 
                fam[l] = change
            else:
                info = [l, [None, None ], -1]
                fam[l] = info

    else:
        if a not in fam:
            info = [a, [l, None ], -2]
            fam[a] = info
        else:
            info = fam[a]
            info[1][1] = l 
            fam[a] = info

wait_list = []

for i in fam:
    if fam[i][2] == -2:
        info = fam[i]

        pai = info[1][0]
        mae = info[1][1]

        pai = fam[pai]
        mae = fam[mae]


        if pai[2] == -2 or mae[2] == -2:
            wait_list.append(i)
        elif (pai[2] >= 0 and mae[2] >= 0) or (pai[2] == 1 or mae[2] == 1):
            # o filho tem o gene
            # o filho tem o gene
            if (pai[2] == 1 and mae[2] == 1) or (pai[2] == 1 and mae[2] == 0) or (mae[2] == 1 and pai[2] == 0):
                info[2] = 1
            else:
                info[2] = 0
        else:
            info[2] = -1;

        fam[i] = info

while True:

    new_list = []
    if len(wait_list) == 0:
        break

    for i in sorted(wait_list):
        if fam[i][2] == -2:
            info = fam[i]

            pai = info[1][0]
            mae = info[1][1]

            pai = fam[pai]
            mae = fam[mae]

            if pai[2] == -2 or mae[2] == -2:
                new_list.append(i)
            elif (pai[2] >= 0 and mae[2] >= 0) or (pai[2] == 1 or mae[2] == 1):
                # o filho tem o gene
                if (pai[2] == 1 and mae[2] == 1) or (pai[2] == 1 and mae[2] == 0) or (mae[2] == 1 and pai[2] == 0):
                    info[2] = 1
                else:
                    info[2] = 0
            else:
                info[2] = -1

            fam[i] = info

    wait_list = new_list

for i in sorted(fam):
    print(i, end='')
    if fam[i][2] == -1:
        print(" non-existent")
    elif fam[i][2] == 0:
        print(" recessive")

    elif fam[i][2] == 1:
        print(" dominant")
