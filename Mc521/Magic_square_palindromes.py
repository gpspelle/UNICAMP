def check(n):

    for i in range(int(n ** (0.5)) + 1):
        if i * i == n:
            return True

    return False
t = int(input())

for wfi in range(t):

    l = list(input())

    l = [i for i in l if i.isalpha()]
    r = l[::-1]

    palin = 1
    for x in range(len(l)):
        if l[x] != r[x]:
            palin = 0
            break   

    print("Case #%d:" % (wfi+1))

    
    if check(len(l)) == False:
        palin = 0

    if palin:
        print(str(int(len(l) ** (0.5))))
    else:
        print("No magic :(")
