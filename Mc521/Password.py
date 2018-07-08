count = 1
def solve(line, k):
    count = 1
    for a in range(len(line[0])):
        for b in range(len(line[1])):
            for c in range(len(line[2])):
                for d in range(len(line[3])):
                    for e in range(len(line[4])):
                        if count < k:
                            count +=1
                            continue
                        else:
                            print(line[0][a] + line[1][b] + line[2][c] + line[3][d] + line[4][e])
                            return 1

    return 0

t = int(input())


while t:
    t-=1

    m1 = []
    m2 = []

    k = int(input())
    for i in range(6):
        m1.append(input())

    for i in range(6):
        m2.append(input())


    resp = []

    line =  [ [], [], [], [], []]

    for x in range(5):
        for i in range(6):
            for j in range(6):
                if m1[i][x] == m2[j][x] and m1[i][x] not in line[x]: 
                    line[x].append(m1[i][x])

    for x in range(5):
        line[x].sort()
    
    entrei = solve(line, k)

    if entrei == 0:
        print('NO')

