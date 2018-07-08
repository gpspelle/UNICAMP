def gcd(a, b):
    while a != b:
        if a > b:
            a = a - b
        else:
            b = b - a

    return a

def lcm(a, b):
    return (a * b) / gcd(a, b)

t = int(input())

while t:
    t -= 1
    v = list(map(int, input().split()))

    n = v.pop(0)

    min_gcd = 10000
    for i in range(len(v) - 1):
        atual = gcd(v[i], v[i+1])
        if atual < min_gcd:
            min_gcd = atual
    for i in range(len(v)):
        if i == len(v):
            print("%d" % (v[i]//min_gcd), end='')
        else:
            print("%d " % (v[i]//min_gcd), end='')
    print("")

        
