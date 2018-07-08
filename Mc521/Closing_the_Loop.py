t = int(input())
case = 1
while t:
    
    corda = int(input())
    verm = []
    azul = []

    seg = input().split() 
    
    for s in seg:
        if s[-1] == 'R':
            verm.append(int(s[:-1]))
        else:
            azul.append(int(s[:-1]))

    num_v = len(verm)
    num_a = len(azul)

    if num_v == 0 or num_a == 0:
        print("Case #%d: 0" % case)

    else:
        while num_v != num_a:
            if num_v > num_a: 
                min_v = min(verm)
                verm.remove(min_v)
                num_v -= 1
            elif num_a > num_v:
                min_a = min(azul)
                azul.remove(min_a)
                num_a -= 1

        out = 0
        for i in range(num_a):  
            out += (azul[i] - 1) 
            out += (verm[i] - 1)
        print("Case #%d: %d" % (case, out))

    case += 1
    t-=1
